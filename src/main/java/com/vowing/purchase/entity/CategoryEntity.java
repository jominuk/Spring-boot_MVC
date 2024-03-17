package com.vowing.purchase.entity;

import com.vowing.purchase.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    public CategoryDTO toValueObject() {
        return new CategoryDTO(
                this.getId(),
                this.getCategory()
        );
    }
}
