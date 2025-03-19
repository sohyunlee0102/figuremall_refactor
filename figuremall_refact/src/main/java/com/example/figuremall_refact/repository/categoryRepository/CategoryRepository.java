package com.example.figuremall_refact.repository.categoryRepository;

import com.example.figuremall_refact.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
