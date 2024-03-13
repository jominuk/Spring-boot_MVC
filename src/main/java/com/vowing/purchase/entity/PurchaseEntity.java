package com.vowing.purchase.entity;

import com.vowing.purchase.dto.PurchaseDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "Product")
public class PurchaseEntity {

    /**
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * URL 주소
     */
    @Column(name = "addressUrl", nullable = false)
    private String addressUrl;

    /**
     * 구매 수량
     */
    @Column(name = "counts", nullable = false)
    private Long counts;


    /**
     * URL 미리보기
     */
    @Column(name = "previewUrl")
    private String previewUrl;


    /**
     * 외래키 지정
     * 기본키 id를 MemberId와의 관계설정
     */
    @JoinColumn(name = "memberId", nullable = false)
    @ManyToOne(targetEntity = MemberEntity.class, fetch = FetchType.LAZY)
    private MemberEntity member;

    public PurchaseDTO toValueObject(){
        return new PurchaseDTO(
                this.getId(),
                this.getMember().getId(),
                this.getAddressUrl(),
                this.getCounts(),
                this.getPreviewUrl()
        );
    }

}
