package com.vowing.purchase.entity;

import com.vowing.purchase.dto.AddSlotDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AddSlot")
public class AddSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column
    private String sellerUserId;

    @Column
    private Long addNumber;

    @Column
    private Long workDay;

    @Column
    private String startDate;

    @Column
    private String productName;

    @Column
    private String productMid;

    @Column
    private String mainKeyword;

    @Column
    private String serveKeyword;

    @Column
    private String goShopping;

    @Column
    private String storeName;

    @Column
    private String endDate;

    @Column
    private String productUrl;

    @Column
    private String link;

    public AddSlotDTO toValueObject() {
        return new AddSlotDTO(
                this.getId(),
                this.getCategory().getId(),
                this.getSellerUserId(),
                this.getAddNumber(),
                this.getWorkDay(),
                this.getStartDate(),
                this.getProductName(),
                this.getProductMid(),
                this.getMainKeyword(),
                this.getServeKeyword(),
                this.getGoShopping(),
                this.getStoreName(),
                this.getEndDate(),
                this.getProductUrl(),
                this.getLink()
        );
    }
}
