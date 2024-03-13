package com.vowing.purchase.entity;

import com.vowing.purchase.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "Member")
public class MemberEntity {

    /**
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * 회원 ID
     */
    @Column(name = "userId", nullable = false)
    private String userId;

    /**
     * 회원 비밀번호
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 회원 회사
     */
    @Column(name = "company", nullable = false)
    private String company;

    public MemberDTO toValueObject() {
        return new MemberDTO(
                this.getId(),
                this.getUserId(),
                this.getPassword(),
                this.getCompany()
        );
    }
}
