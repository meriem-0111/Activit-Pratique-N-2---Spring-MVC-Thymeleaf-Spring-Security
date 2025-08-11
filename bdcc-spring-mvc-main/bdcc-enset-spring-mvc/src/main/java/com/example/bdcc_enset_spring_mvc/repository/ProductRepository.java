package com.example.bdcc_enset_spring_mvc.repository;

import com.example.bdcc_enset_spring_mvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
