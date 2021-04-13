package dev.sassine.perfomance.insert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.sassine.perfomance.insert.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
