package com.vowing.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSlotDTO {
    private Long id;
    private Long categoryId;
    private String sellerUserId;
    private Long addNumber;
    private Long workDay;
    private String startDate;
    private String productName;
    private String productMid;
    private String mainKeyword;
    private String serveKeyword;
    private String goShopping;
    private String storeName;
    private String endDate;
    private String productUrl;
    private String link;

}
