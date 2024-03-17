package com.vowing.purchase.controller;

import com.vowing.purchase.dto.AddSlotDTO;
import com.vowing.purchase.entity.AddSlotEntity;
import com.vowing.purchase.service.AddSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/add")
public class AddSlotController {

    private final AddSlotService addSlotService;

    /**
     * 모든 slot 목록 가져오기
     * @return slot 목록
     */
    @GetMapping("/every")
    public ResponseEntity<List<AddSlotDTO>> getAllAddSlots() {
        List<AddSlotDTO> addSlotDTOList = addSlotService.getAllAddSlots();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addSlotDTOList);
    }

    /**
     * 카테고리별 출력
     * @param category category
     * @return 카테고리 내용
     */
    @GetMapping("/")
    public ResponseEntity<List<AddSlotDTO>> getCategorySelect(String category) {
        List<AddSlotDTO> addSlotDTOList = addSlotService.getAddSlotsByCategory(category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addSlotDTOList);
    }

    /**
     * 슬롯 등록
     * @param dto AddSlotDTO
     * @return 슬롯 등록
     */
    @PostMapping("/save")
    public ResponseEntity<AddSlotDTO> slotSave(@RequestBody AddSlotDTO dto ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addSlotService.addSlotSave(dto));
    }

}
