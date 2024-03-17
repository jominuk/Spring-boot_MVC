package com.vowing.purchase.repository;

import com.vowing.purchase.entity.AddSlotEntity;
import com.vowing.purchase.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddSlotRepository extends JpaRepository<AddSlotEntity, Long> {
    List<AddSlotEntity> findByCategory(CategoryEntity category);
}
