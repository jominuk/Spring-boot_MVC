package com.vowing.purchase.service;

import com.vowing.purchase.dto.PurchaseDTO;
import com.vowing.purchase.entity.MemberEntity;
import com.vowing.purchase.entity.PurchaseEntity;
import com.vowing.purchase.error.PurchaseNotFound;
import com.vowing.purchase.error.UserNotFound;
import com.vowing.purchase.repository.MemberRepository;
import com.vowing.purchase.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    /**
     * 구매 목록 불러오기
     * @return 구매 목록
     */
    @Transactional
    public List<PurchaseDTO> boardList() {
        List<PurchaseEntity> purchaseEntityList = purchaseRepository.findAll();

        return purchaseEntityList.stream()
                .map(PurchaseEntity::toValueObject)
                .collect(Collectors.toList());
    }

    /**
     * 구매 목록 엑셀파일로 변환
     * @return 엑셀파일
     */
    @Transactional
    public Workbook exportBoardListToExcel() {
        List<PurchaseEntity> purchaseEntityList = purchaseRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Board List");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Address URL");
        headerRow.createCell(2).setCellValue("Counts");

        int rowNum = 1;
        for (PurchaseEntity purchaseEntity : purchaseEntityList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(purchaseEntity.getId());
            row.createCell(1).setCellValue(purchaseEntity.getAddressUrl());
            row.createCell(2).setCellValue(purchaseEntity.getCounts());
        }

        return workbook;
    }

    /**
     * 구매 등록
     * @param dto PurchaseDTO
     * @return 구매 등록
     */
    @Transactional
    public PurchaseDTO purchaseSave(PurchaseDTO dto) {
        final var memberEntity = memberRepository
                .findById(dto.getMemberId())
                .orElseThrow(UserNotFound::new);

        String previewUrl = fetchPreviewInformation(dto.getAddressUrl()).toString();
        dto.setPreviewUrl(previewUrl);

        final var purchaseEntity = new PurchaseEntity();
        purchaseEntity.setMember(memberEntity);
        purchaseEntity.setAddressUrl(dto.getAddressUrl());
        purchaseEntity.setCounts(dto.getCounts());
        purchaseEntity.setPreviewUrl(dto.getPreviewUrl());

        PurchaseEntity savedEntity = purchaseRepository.save(purchaseEntity);

        dto.setPreviewUrl(savedEntity.getPreviewUrl());

        return savedEntity.toValueObject();
    }



    /**
     * URL 크롤링 (title만
     */
    /*
    @Transactional
    public String fetchPreviewInformation(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Document document = Jsoup.connect(url).get();
            return document.title();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching preview information: " + e.getMessage();
        }
    }
     */

    /**
     * URL 크롤링 (사진 + 제목 + 링크)
     */
    @Transactional
    public Map<String, String> fetchPreviewInformation(String url) {
        Map<String, String> previewInfo = new HashMap<>();
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Document document = Jsoup.connect(url).get();

            String ogTitle = document.select("meta[property=og:title]").attr("content");
            String ogImage = document.select("meta[property=og:image]").attr("content");

            previewInfo.put("title", ogTitle.isEmpty() ? document.title() : ogTitle);
            previewInfo.put("image", ogImage);

            return previewInfo;
        } catch (Exception e) {
            e.printStackTrace();
            previewInfo.put("error", "미리보기 정보가 없습니다. " + e.getMessage());
            return previewInfo;
        }
    }

    /**
     * 구매 수정
     * @param id 수정 할 번호
     * @param dto PurchaseDTO dto
     * @return 구매 수정 완료
     */
    @Transactional
    public PurchaseDTO purchaseUpdate(Long id, PurchaseDTO dto) {
        final var entity = purchaseRepository
                .findById(id)
                .orElseThrow(PurchaseNotFound::new);

        entity.setAddressUrl(dto.getAddressUrl());
        entity.setCounts(dto.getCounts());

        return purchaseRepository.save(entity).toValueObject();
    }


    public void purchaseRemove(Long id) {
        //purchaseRepository.findById(id).orElseThrow(PurchaseNotFound::new);
        final var purchase = purchaseRepository
                .findById(id)
                .orElseThrow(PurchaseNotFound::new);
        purchaseRepository.delete(purchase);
    }

    /**
     * member id 유효성 확인
     * @param memberId memberId
     * @return memberId
     */
    public boolean isValidMemberId(Long memberId) {
        return memberRepository.findById(memberId).isPresent();
    }


}
