package com.vowing.purchase.service;

import com.vowing.purchase.dto.MemberDTO;
import com.vowing.purchase.entity.MemberEntity;
import com.vowing.purchase.error.UserNotFound;
import com.vowing.purchase.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 목록
     * @return 회원 목록
     */

    @Transactional
    public List<MemberDTO> memberList() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        return memberEntityList.stream()
                .map(MemberEntity::toValueObject)
                .collect(Collectors.toList());
    }



    /**
     * 회원 페이징 처리
     * @param pageable MemberDTO
     * @return 회원 각 페이지 인원
     */

    /*
    @Transactional
    public Page<MemberDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() ;
        int pageLimit = 3;

        Page<MemberEntity> memberEntities =
                memberRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<MemberDTO> memberDTO = memberEntities.map(
                member -> new MemberDTO(member.getId(), member.getUserId(), member.getCompany())
        );
        return memberDTO;

    }

     */




    /**
     * 회원 등록
     * @param dto MemberDTO dto
     * @return 회원 생성
     */
    @Transactional
    public MemberDTO memberSave(MemberDTO dto) {
        final var entity = new MemberEntity();
        entity.setUserId(dto.getUserId());
        entity.setPassword(hashPassword(dto.getPassword()));
        entity.setCompany(dto.getCompany());
        return memberRepository.save(entity).toValueObject();
    }

    /**
     * 회원 수정
     * @param id 수정할 회원 기본키
     * @param dto MemberDTO dto
     * @return 회원 수정
     */
    @Transactional
    public MemberDTO memberUpdate(Long id, MemberDTO dto) {
        final var entity = memberRepository
                .findById(id)
                .orElseThrow(UserNotFound::new);

        entity.setUserId(dto.getUserId());
        entity.setPassword(hashPassword(dto.getPassword()));
        entity.setCompany(dto.getCompany());

        return memberRepository.save(entity).toValueObject();
    }

    /**
     * 현재 2가지 로직으로 DB까지 삭제와 클라이언트한테만 삭제가 가능
     * 회원 삭제
     * @param id 삭제할 회원 기본키
     *
     */
    @Transactional
    public void memberRemove(Long id) {
        // memberRepository.findById(id).orElseThrow(UserNotFound::new);
        // 이 로직은 클라이언트 상에서 삭제를 구현
        // 데이터베이스에서는 데이터 존재


        final var member = memberRepository
                .findById(id)
                .orElseThrow(UserNotFound::new);
        memberRepository.delete(member);
        // 이 로직은 데이터베이스까지 삭제 구현

    }

    /**
     * 비밀번호 SHA-512
     *
     * @param password 비밀번호 평문
     * @return 해시된 비밀번호
     */
    @SneakyThrows(NoSuchAlgorithmException.class)
    private String hashPassword(String password) {
        final var instance = MessageDigest.getInstance("SHA-512");
        instance.update((password + "<JH: place your salt here>").getBytes(StandardCharsets.UTF_8));
        final var bytes = instance.digest();
        final var numericRepresentation = new BigInteger(1, bytes);

        return String.format("%0128x", numericRepresentation);
    }

    /**
     * 로그인
     * @param dto MemberDTO
     * @return 로그인
     */
    @Transactional
    public MemberDTO memberLogin(MemberDTO dto) {
        MemberEntity memberEntity = memberRepository.findByUserId(dto.getUserId())
                .orElseThrow(UserNotFound::new);
        return memberEntity.toValueObject();
    }

}
