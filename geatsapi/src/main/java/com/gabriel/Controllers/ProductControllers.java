package com.gabriel.Controllers;

import java.util.List;

import com.gabriel.Repo.ProductRepo;
import com.gabriel.Services.StripeService;
import com.gabriel.Utils.StripeResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.Models.Product;
@RestController
public class ProductControllers {


    @Value("${stripe.apikey}")
    String stripeKey;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private StripeService stripeService;

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

    @PostMapping("/api/create-checkout-session")
    public ResponseEntity<StripeResponse> createCheckout(@RequestBody List<Product> products) throws StripeException {
        Session session = stripeService.createSession(products);
        StripeResponse stripeResponse = new StripeResponse(session.getUrl());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}