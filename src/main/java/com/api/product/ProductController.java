package com.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.api.category.Category;
import com.api.category.CategoryRepo;

@Controller
public class ProductController {
  
	@Autowired
	private ProductRepo Prepo;
	
	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private productservicee service;
	
	@GetMapping("/Product/Create")
	public String showProduct(Model model) {
		List<Category> listcategory = repo.findAll();
		model.addAttribute("product",new Product());
		model.addAttribute("listcategory",listcategory);
		return "ProductForm";
	}
	
	@PostMapping("/product/save")
	public String saveProduct(Product product) {
		Prepo.save(product);
		return "redirect:/Product";
	}
	
	@GetMapping("/Product")
	public String listProduct(Model model) {
		return listByPage(model, 1);
	}
	
	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model,@PathVariable("pageNumber") int currentPage) {
	
	Page<Product> page=service.listAll(currentPage);
	long totalItems=page.getTotalElements();
	int totalPages=page.getTotalPages();
	
	List<Product>listproduct=page.getContent();
	
	model.addAttribute("currentPage", currentPage);
	model.addAttribute("totalItems", totalItems);
	model.addAttribute("totalPages", totalPages);
 	model.addAttribute("listproduct", listproduct);
	return "Product";
}
	
	@GetMapping("/product/edit/{id}")
	public String showProduct(@PathVariable("id") Integer id,Model model) {
		Product product = Prepo.findById(id).get();
		model.addAttribute("product", product);
		
		List<Category> listcategory = repo.findAll();
		model.addAttribute("listcategory",listcategory);
		
		return "ProductForm";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id,Model model) {
		Prepo.deleteById(id);
		return "redirect:/Product";
	}
	
//	@GetMapping("/page/{pageNo}")
//	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model) {
//		int pageSize = 5;
//		Page<Product> page = PService.findPaginated(pageNo, pageSize);
//		List<Product> listproduct = page.getContent();
//		model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("listProduct", listproduct);
//		return "Product";
//	}
	
}
