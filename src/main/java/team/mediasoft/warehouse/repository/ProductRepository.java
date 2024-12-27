package team.mediasoft.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.warehouse.model.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);
}