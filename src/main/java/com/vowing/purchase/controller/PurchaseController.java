package com.vowing.purchase.controller;

import com.vowing.purchase.dto.PurchaseDTO;
import com.vowing.purchase.error.PurchaseNotFound;
import com.vowing.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * 구매 목록 리스트 가져오기
     * @return 구매 목록
     */
    @GetMapping("/")
    public ResponseEntity<List<PurchaseDTO>> getAllBoardList() {
        List<PurchaseDTO> boardList = purchaseService.boardList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardList);
    }

    /**
     * 구매 목록 엑셀 파일로 변환
     * @return  엑셀 파일
     * @throws Exception 예외처리
     */
    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportBoardListToExcel() throws Exception {
        Workbook workbook = purchaseService.exportBoardListToExcel();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "purchase_list.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 이건 title만 나오게 등록
     */
    /*
    @GetMapping(value = "/preview", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getPreview(@RequestParam(name = "url") String url) {
        System.out.println("Received URL: " + url);
        String result = purchaseService.fetchPreviewInformation(url);
        System.out.println("Server Response: " + result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
     */

    /**
     * URL 링크로 사진+제목+링크 출력
     * @param url URL
     * @return 사진 + 제목 + 링크
     */
    @GetMapping(value = "/preview", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, String>> getPreview(@RequestParam(name = "url") String url) {
        System.out.println("Received URL: " + url);
        Map<String, String> result = purchaseService.fetchPreviewInformation(url);
        System.out.println("Server Response: " + result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    /**
     * 구매 등록
     * @param dto PurchaseDTO
     * @return 구매 등록
     */
    @PostMapping("/save")
    public ResponseEntity<PurchaseDTO> purchaseSave(
            @RequestBody PurchaseDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(purchaseService.purchaseSave(dto));
    }

    /**
     * 구매 수정
     * @param id 구매 수정할 번호
     * @param dto PurchaseDTO dto
     * @return 구매 수정 완료
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseDTO> purchaseUpdate(
            @PathVariable(name = "id") Long id,
            @RequestBody PurchaseDTO dto) {
        if (!purchaseService.isValidMemberId(id)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(purchaseService.purchaseUpdate(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PurchaseDTO> purchaseDelete(
            @PathVariable(name = "id") Long id) {
        /*
        if(!purchaseService.isValidMemberId(id)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
         */
        purchaseService.purchaseRemove(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
