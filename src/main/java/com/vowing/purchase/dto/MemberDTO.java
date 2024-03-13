package com.vowing.purchase.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String userId;
    private String password;
    private String company;

    public MemberDTO(Long id, String userId, String company) {
        this.id = id;
        this.userId = userId;
        this.company = company;
    }


}
