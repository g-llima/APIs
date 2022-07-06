package com.gabriel.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.Models.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
