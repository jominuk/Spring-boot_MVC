package com.vowing.purchase.controller;

import com.vowing.purchase.dto.MemberDTO;
import com.vowing.purchase.error.UserNotFound;
import com.vowing.purchase.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 목록 불러오기
     * @return 회원 목록
     */

    @GetMapping("/")
    public ResponseEntity<List<MemberDTO>> getAllMemberList(){
        List<MemberDTO> memberList = memberService.memberList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberList);
    }



    /**
     * 회원 목록 페이징 처리
     * @param pageable MemberDTO
     * @return 회원 각 페이지 인원
     */
/*
    @GetMapping("/paging")
    public ResponseEntity<Page<MemberDTO>> getAliiMamberPagin(
            @PageableDefault(page = 1) Pageable pageable) {
        Page<MemberDTO> memberDTOPage = memberService.paging(pageable);
        int blockLimit = 3;
        int currentPage = pageable.getPageNumber() + 1;
        int startPage = ((currentPage - 1) / blockLimit) * blockLimit + 1;
        int endPage = Math.min(startPage + blockLimit - 1, memberDTOPage.getTotalPages());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberDTOPage);
    }
 */


    /**
     * 회원 등록
     * @param dto MemberDTO dto
     * @return 회원 생성
     */
    @PostMapping("/save")
    public ResponseEntity<MemberDTO> memberSave(
            @RequestBody MemberDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.memberSave(dto));
    }

    /**
     * 회원 수정
     * @param id 수정할 회원 기본키
     * @param dto MemberDTO dto
     * @return 회원 수정
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<MemberDTO> memberUpdate(
            @PathVariable(name = "id") Long id,
            @RequestBody MemberDTO dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.memberUpdate(id, dto));
    }

    /**
     * 회원 삭제
     * @param id 기본키
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MemberDTO> memberDelete(
            @PathVariable(name = "id") Long id){
        memberService.memberRemove(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 로그인
     * @param dto MemberDTO
     * @param session 로그인 id 저장
     * @return 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDTO> memberLogin(
            @RequestBody MemberDTO dto,
            HttpSession session) {
        try {
            MemberDTO loggedInMember = memberService.memberLogin(dto);
            session.setAttribute("loginId", loggedInMember.getUserId());

            System.out.println("Session attribute 'loginId': " + session.getAttribute("loginId"));

            return ResponseEntity.status(HttpStatus.OK).body(loggedInMember);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 로그아웃
     * @param session 세션 id
     * @return 로그아웃
     */
    @GetMapping("/logout")
    public ResponseEntity<MemberDTO> memberLogout(HttpSession session){
        session.invalidate();
        System.out.println("로그아웃이 성공적으로 되었습니다");
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
