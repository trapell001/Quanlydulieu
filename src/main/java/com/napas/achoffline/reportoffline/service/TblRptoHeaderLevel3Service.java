package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel3;
import com.napas.achoffline.reportoffline.models.TblRptoHeaderLevel3DTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel3DAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblRptoHeaderLevel3Service {

    @Autowired
    private TblRptoHeaderLevel3DAO tblRptoHeaderLevel3DAO;

    @Autowired
    private ModelMapper mapper;

    private TblRptoHeaderLevel3DTO fromEntity(TblRptoHeaderLevel3 entity) {
        TblRptoHeaderLevel3DTO dto = mapper.map(entity, TblRptoHeaderLevel3DTO.class);
        return dto;
    }

    private TblRptoHeaderLevel3 fromDTO(TblRptoHeaderLevel3DTO dto) {
        TblRptoHeaderLevel3 entity = mapper.map(dto, TblRptoHeaderLevel3.class);
        return entity;
    }

    public ResponseEntity<?> delete(Integer id) {
        tblRptoHeaderLevel3DAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa công thức phí thành công"));
    }

    public ResponseEntity<?> post(TblRptoHeaderLevel3DTO input) {

        TblRptoHeaderLevel3 entity = fromDTO(input);

        TblRptoHeaderLevel3 t = tblRptoHeaderLevel3DAO.findByHeaderCode(input.getHeaderCode());
        if(t != null) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }

        if (tblRptoHeaderLevel3DAO.findByHeaderCodeAndLevel2Id(input.getHeaderCode().trim(), input.getLevel2Id()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }
        entity.setLevel2Id(input.getLevel2Id());
        entity.setHeaderCode(input.getHeaderCode().trim());
        entity.setHeaderName(input.getHeaderName());

        if (tblRptoHeaderLevel3DAO.findByHeaderOrderAndLevel2Id(input.getHeaderOrder(), input.getLevel2Id()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        }
        entity.setHeaderOrder(input.getHeaderOrder());
        tblRptoHeaderLevel3DAO.save(entity);

        return ResponseEntity.ok(fromEntity(entity));
    }
    public ResponseEntity<?> put(int id, TblRptoHeaderLevel3DTO input) {

        TblRptoHeaderLevel3 t = tblRptoHeaderLevel3DAO.findByHeaderCode(input.getHeaderCode());
        if(t != null && t.getId() != id) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }

        TblRptoHeaderLevel3 t1 = tblRptoHeaderLevel3DAO
                .findByHeaderOrderAndLevel2Id(input.getHeaderOrder(), input.getLevel2Id()).get();

        if (t1 != null && t1.getId() != id) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        }

        TblRptoHeaderLevel3 entity = tblRptoHeaderLevel3DAO.findById(id).orElse(null);
        entity.setLevel2Id(input.getLevel2Id());
        entity.setHeaderName(input.getHeaderName());
        entity.setHeaderOrder(input.getHeaderOrder());
        entity.setHeaderCode(input.getHeaderCode());
        tblRptoHeaderLevel3DAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }
    public TblRptoHeaderLevel3DTO get(int id) {
        return fromEntity(tblRptoHeaderLevel3DAO.findById(id).orElse(null));
    }

    public List<TblRptoHeaderLevel3DTO> find(int level2Id) {
        List<TblRptoHeaderLevel3> dbResult = tblRptoHeaderLevel3DAO.findByLevel2Id(level2Id)
                .stream().sorted(Comparator.comparingLong(TblRptoHeaderLevel3::getHeaderOrder))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
