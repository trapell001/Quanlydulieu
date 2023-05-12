package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel1;
import com.napas.achoffline.reportoffline.models.TblRptoHeaderLevel1DTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel1DAO;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel2DAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblRptoHeaderLevel1Service {

    @Autowired
    private TblRptoHeaderLevel1DAO tblRptoHeaderLevel1DAO;

    @Autowired
    private TblRptoHeaderLevel2DAO tblRptoHeaderLevel2DAO;

    @Autowired
    private ModelMapper mapper;

    private TblRptoHeaderLevel1DTO fromEntity(TblRptoHeaderLevel1 entity) {
        TblRptoHeaderLevel1DTO dto = mapper.map(entity, TblRptoHeaderLevel1DTO.class);
        return dto;
    }
    private TblRptoHeaderLevel1 fromDTO(TblRptoHeaderLevel1DTO dto) {
        TblRptoHeaderLevel1 entity = mapper.map(dto, TblRptoHeaderLevel1.class);
        return entity;
    }

    public ResponseEntity<?> delete(Integer id){
        if(tblRptoHeaderLevel2DAO.findByLevel1Id(id).size() > 0)
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có tiêu đề con"));
        tblRptoHeaderLevel1DAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa công thức phí thành công"));
    }
    public ResponseEntity<?> post(TblRptoHeaderLevel1DTO input) {

        TblRptoHeaderLevel1 entity = fromDTO(input);

        if (tblRptoHeaderLevel1DAO.findByHeaderCode(input.getHeaderCode().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }
        if (tblRptoHeaderLevel1DAO.findByHeaderName(input.getHeaderName().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Tên tiêu đề đã tồn tại"));
        }
        entity.setHeaderCode(input.getHeaderCode().trim());
        entity.setHeaderName(input.getHeaderName());

        if (tblRptoHeaderLevel1DAO.findByHeaderOrder(input.getHeaderOrder()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        }
        entity.setHeaderOrder(input.getHeaderOrder());
        tblRptoHeaderLevel1DAO.save(entity);

        return ResponseEntity.ok(fromEntity(entity));
    }
    public ResponseEntity<?> put(int id, TblRptoHeaderLevel1DTO input) {

        TblRptoHeaderLevel1 entity = tblRptoHeaderLevel1DAO.findById(id).orElse(null);
        if (entity.getHeaderCode().equals(input.getHeaderCode().trim())) {
            entity.setHeaderCode(input.getHeaderCode());
        } else if (tblRptoHeaderLevel1DAO.findByHeaderCode(input.getHeaderCode().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        } else {
            entity.setHeaderCode(input.getHeaderCode().trim());
        }

        TblRptoHeaderLevel1 t = tblRptoHeaderLevel1DAO.findByHeaderName(input.getHeaderName().trim()).get();

        if(t != null && t.getId() != id) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Tên tiêu đề đã tồn tại"));
        }

        entity.setHeaderName(input.getHeaderName());

        if (entity.getHeaderOrder() == input.getHeaderOrder()) {
            entity.setHeaderOrder(input.getHeaderOrder());
        } else if (tblRptoHeaderLevel1DAO.findByHeaderOrder(input.getHeaderOrder()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        } else {
            entity.setHeaderOrder(input.getHeaderOrder());
        }
        tblRptoHeaderLevel1DAO.save(entity);

        return ResponseEntity.ok(fromEntity(entity));
    }
    public TblRptoHeaderLevel1DTO get(int id) {
        return fromEntity(tblRptoHeaderLevel1DAO.findById(id).orElse(null));
    }

    public List<TblRptoHeaderLevel1DTO> list() {
        List<TblRptoHeaderLevel1> dbResult = tblRptoHeaderLevel1DAO.findAll().stream()
                .sorted(Comparator.comparing(TblRptoHeaderLevel1::getHeaderOrder)).collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
