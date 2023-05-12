package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoLadderValueLevel;
import com.napas.achoffline.reportoffline.models.TblRptoLadderValueLevelDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoLadderValueLevelDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblRptoLadderValueLevelService {

    @Autowired
    private TblRptoLadderValueLevelDAO tblRptoLadderValueLevelDAO;
    @Autowired
    private ModelMapper mapper;

    private boolean isNumTransRangeOverlap(TblRptoLadderValueLevelDTO entity,Long ladderId, boolean check) {
        List<TblRptoLadderValueLevel> list = tblRptoLadderValueLevelDAO.findAll().stream().filter(item ->
                item.getLadderId() == ladderId).collect(Collectors.toList());
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblRptoLadderValueLevel> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblRptoLadderValueLevel> newList = list.stream().filter(item -> (item.getId() != entity.getId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }
    private TblRptoLadderValueLevelDTO fromEntity(TblRptoLadderValueLevel entity) {
        TblRptoLadderValueLevelDTO dto = mapper.map(entity, TblRptoLadderValueLevelDTO.class);
        return dto;
    }

    private TblRptoLadderValueLevel fromDTO(TblRptoLadderValueLevelDTO dto) {
        TblRptoLadderValueLevel entity = mapper.map(dto, TblRptoLadderValueLevel.class);
        return entity;
    }

    public ResponseEntity<?> delete(Integer id) {
        tblRptoLadderValueLevelDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa cấu hình phí thành công"));
    }

    public ResponseEntity<?> post(TblRptoLadderValueLevelDTO input) {

        try {
            TblRptoLadderValueLevel entity = fromDTO(input);
            if (input.getValueRangeMin() > input.getValueRangeMax()) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                        .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
            }
            if (input.getValueRangeMax().toString().equals(input.getValueRangeMin().toString())) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                        .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
            }


            if (isNumTransRangeOverlap(input, input.getLadderId(), true)) {
                entity.setLadderId(input.getLadderId());
                entity.setValueRangeMax(input.getValueRangeMax());
                entity.setValueRangeMin(input.getValueRangeMin());
                tblRptoLadderValueLevelDAO.save(entity);
                return ResponseEntity.ok(fromEntity(entity));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng bậc thang bị trùng với một trong số các bậc thang đã cài đặt trước đó"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Bậc thang không tồn tại"));
        }
    }

    public ResponseEntity<?> put(int id, TblRptoLadderValueLevelDTO input) {
        try {
            TblRptoLadderValueLevel entity = fromDTO(input);
            entity.setId(id);

            if (input.getValueRangeMin() > input.getValueRangeMax()) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                        .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
            } else if (input.getValueRangeMax().toString().equals(input.getValueRangeMin().toString())) {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                        .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
            }

            if (isNumTransRangeOverlap(input, input.getLadderId(), false)) {
                entity.setLadderId(input.getLadderId());
                entity.setValueRangeMax(input.getValueRangeMax());
                entity.setValueRangeMin(input.getValueRangeMin());
                tblRptoLadderValueLevelDAO.save(entity);
                return ResponseEntity.ok(fromEntity(entity));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng bậc thang bị trùng với một trong số các bậc thang đã cài đặt trước đó"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Bậc thang không tồn tại"));
        }
    }

    public TblRptoLadderValueLevelDTO get(int id) {
        return fromEntity(tblRptoLadderValueLevelDAO.findById(id).orElse(null));
    }

    public List<TblRptoLadderValueLevelDTO> find(Long ladderId) {
        List<TblRptoLadderValueLevel> dbResult = tblRptoLadderValueLevelDAO.findByLadderId(ladderId)
                .stream().sorted(Comparator.comparingLong(TblRptoLadderValueLevel::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

}