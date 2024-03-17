package com.vowing.purchase.service;

import com.vowing.purchase.dto.AddSlotDTO;
import com.vowing.purchase.entity.AddSlotEntity;
import com.vowing.purchase.entity.CategoryEntity;
import com.vowing.purchase.repository.AddSlotRepository;
import com.vowing.purchase.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddSlotService {

    private final AddSlotRepository addSlotRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 모든 slot 목록 가져오기
     * @return slot 목록
     */
    @Transactional
    public List<AddSlotDTO> getAllAddSlots() {
        List<AddSlotEntity> addSlotEntityList = addSlotRepository.findAll();

        return addSlotEntityList.stream()
                .map(AddSlotEntity::toValueObject)
                .collect(Collectors.toList());
    };

    /**
     * 카테고리별 출력
     * @param category category
     * @return 카테고리 내용
     */
    @Transactional
    public List<AddSlotDTO> getAddSlotsByCategory(String category) {
        CategoryEntity categoryEntity = categoryRepository.findByCategory(category);

        List<AddSlotEntity> addSlotEntityList = addSlotRepository.findByCategory(categoryEntity);

        return addSlotEntityList.stream()
                .map(AddSlotEntity::toValueObject)
                .collect(Collectors.toList());
    }

    /**
     * 슬롯 등록
     * @param dto AddSlotDTO
     * @return 슬롯 등록
     */
    @Transactional
    public AddSlotDTO addSlotSave(AddSlotDTO dto) {
        try {
            CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

            AddSlotEntity addSlotEntity = new AddSlotEntity();
            addSlotEntity.setCategory(category);
            addSlotEntity.setSellerUserId(dto.getSellerUserId());
            addSlotEntity.setAddNumber(dto.getAddNumber());
            addSlotEntity.setWorkDay(dto.getWorkDay());
            addSlotEntity.setStartDate(dto.getStartDate());

            AddSlotEntity savedEntity = addSlotRepository.save(addSlotEntity);

            return savedEntity.toValueObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
