package com.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		List<Product>listproduct=Prepo.findAll();
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
}
