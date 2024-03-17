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
    private String memo;

    public MemberDTO(Long id, String userId, String memo) {
        this.id = id;
        this.userId = userId;
        this.memo = memo;
    }


}
