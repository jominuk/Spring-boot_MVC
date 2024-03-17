package com.vowing.purchase.dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private Long memberId;
    private String addressUrl;
    private Long counts;
    private String previewUrl;
}
