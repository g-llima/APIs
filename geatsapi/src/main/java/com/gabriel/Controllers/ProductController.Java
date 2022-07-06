package com.gabriel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.geatsapi.Models.Product;
import com.gabriel.geatsapi.Repo.ProductRepo;

@RestController
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @PostMapping("/")
    public String saveProduct(@RequestBody Product product) {
        productRepo.save(product);
        return "Saved...";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, @RequestBody Product product) {
        Product updatedProduct = productRepo.findById(id).get();
        updatedProduct.setCalories(product.getCalories());
        updatedProduct.setImg_url(product.getImg_url());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setProduct_name(product.getProduct_name());
        updatedProduct.setQuantity(product.getQuantity());
        updatedProduct.setVeg(product.isVeg());

        productRepo.save(updatedProduct);

        return "Updated...";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
        productRepo.deleteById(id);
        return "Deleted...";
    }
}