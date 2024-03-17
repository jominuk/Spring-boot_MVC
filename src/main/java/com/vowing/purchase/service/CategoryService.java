package com.vowing.purchase.service;

import com.vowing.purchase.dto.CategoryDTO;
import com.vowing.purchase.entity.AddSlotEntity;
import com.vowing.purchase.entity.CategoryEntity;
import com.vowing.purchase.repository.AddSlotRepository;
import com.vowing.purchase.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AddSlotRepository addSlotRepository;

    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryEntityList.stream()
                .map(CategoryEntity::toValueObject)
                .collect(Collectors.toList());
    }

    /*
    public CategoryDTO getFinaleCategory() {
        return new CategoryDTO(1L, "피날레");
    }
     */


}
