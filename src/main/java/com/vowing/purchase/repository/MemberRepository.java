package com.vowing.purchase.repository;

import com.vowing.purchase.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    /**
     * 로그인
     * @param userId 유저id
     */
    Optional<MemberEntity> findByUserId(String userId);
}
