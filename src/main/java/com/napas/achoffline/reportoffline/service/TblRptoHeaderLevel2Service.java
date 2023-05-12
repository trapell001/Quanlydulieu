package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel2;
import com.napas.achoffline.reportoffline.models.TblRptoHeaderLevel2DTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoHeaderLevel2DAO;
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
public class TblRptoHeaderLevel2Service {

    @Autowired
    private TblRptoHeaderLevel2DAO tblRptoHeaderLevel2DAO;

    @Autowired
    private TblRptoHeaderLevel3DAO tblRptoHeaderLevel3DAO;

    @Autowired
    private ModelMapper mapper;

    private TblRptoHeaderLevel2DTO fromEntity(TblRptoHeaderLevel2 entity) {
        TblRptoHeaderLevel2DTO dto = mapper.map(entity, TblRptoHeaderLevel2DTO.class);
        return dto;
    }

    private TblRptoHeaderLevel2 fromDTO(TblRptoHeaderLevel2DTO dto) {
        TblRptoHeaderLevel2 entity = mapper.map(dto, TblRptoHeaderLevel2.class);
        return entity;
    }

    public ResponseEntity<?> delete(Integer id) {
        if(tblRptoHeaderLevel3DAO.findByLevel2Id(id).size() > 0)
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có tiêu đề con"));
        tblRptoHeaderLevel2DAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa công thức phí thành công"));
    }
    public ResponseEntity<?> post(TblRptoHeaderLevel2DTO input) {

        TblRptoHeaderLevel2 entity = fromDTO(input);
        TblRptoHeaderLevel2 t = tblRptoHeaderLevel2DAO.findByHeaderCode(input.getHeaderCode());
        if(t != null) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }

        if (tblRptoHeaderLevel2DAO.findByHeaderCodeAndLevel1Id(input.getHeaderCode().trim(),input.getLevel1Id()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }
        entity.setLevel1Id(input.getLevel1Id());
        entity.setHeaderCode(input.getHeaderCode().trim());
        entity.setHeaderName(input.getHeaderName());

        if (tblRptoHeaderLevel2DAO.findByHeaderOrderAndLevel1Id(input.getHeaderOrder(), input.getLevel1Id()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        }
        entity.setHeaderOrder(input.getHeaderOrder());
        tblRptoHeaderLevel2DAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> put(int id, TblRptoHeaderLevel2DTO input) {

        TblRptoHeaderLevel2 t = tblRptoHeaderLevel2DAO.findByHeaderCode(input.getHeaderCode());
        if(t != null && t.getId() != id) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Mã tiêu đề đã tồn tại"));
        }

        TblRptoHeaderLevel2 t1 = tblRptoHeaderLevel2DAO
                .findByHeaderOrderAndLevel1Id(input.getHeaderOrder(), input.getLevel1Id()).get();

        if (t1 != null && t1.getId() != id) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Thứ tự của tiêu đề đã tồn tại"));
        }

        TblRptoHeaderLevel2 entity = tblRptoHeaderLevel2DAO.findById(id).orElse(null);
        entity.setLevel1Id(input.getLevel1Id());
        entity.setHeaderName(input.getHeaderName());
        entity.setHeaderOrder(input.getHeaderOrder());
        entity.setHeaderCode(input.getHeaderCode());
        tblRptoHeaderLevel2DAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }
    public TblRptoHeaderLevel2DTO get(int id) {
        return fromEntity(tblRptoHeaderLevel2DAO.findById(id).orElse(null));
    }
    public List<TblRptoHeaderLevel2DTO> find(int level1Id) {
        List<TblRptoHeaderLevel2> dbResult = tblRptoHeaderLevel2DAO.findByLevel1Id(level1Id)
                .stream().sorted(Comparator.comparingLong(TblRptoHeaderLevel2::getHeaderOrder))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
