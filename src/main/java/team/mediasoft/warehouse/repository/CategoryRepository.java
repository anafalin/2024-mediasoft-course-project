package team.mediasoft.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.warehouse.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);

    Optional<Category> findByName(String category);
}