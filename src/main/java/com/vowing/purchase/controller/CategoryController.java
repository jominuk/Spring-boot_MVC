package com.vowing.purchase.controller;

import com.vowing.purchase.dto.CategoryDTO;
import com.vowing.purchase.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategory();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryDTOList);
    }

    /*
    @GetMapping("/finale")
    public ResponseEntity<CategoryDTO> getCategoryFinale() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getFinaleCategory());
    }
     */


}
