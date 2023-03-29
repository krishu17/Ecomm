package com.happytech.Electrostore.repo;

import com.happytech.Electrostore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
