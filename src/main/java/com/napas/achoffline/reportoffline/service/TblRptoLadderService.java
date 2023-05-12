package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel3;
import com.napas.achoffline.reportoffline.entity.TblRptoLadder;
import com.napas.achoffline.reportoffline.entity.TblRptoLadderValueLevel;
import com.napas.achoffline.reportoffline.models.TblRptoLadderDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRptoLadderDAO;
import com.napas.achoffline.reportoffline.repository.TblRptoLadderValueLevelDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblRptoLadderService {

    @Autowired
    private TblRptoLadderDAO tblRptoLadderDAO;

    @Autowired
    private TblRptoLadderValueLevelDAO tblRptoLadderValueLevelDAO;

    @Autowired
    private ModelMapper mapper;

    private TblRptoLadderDTO fromEntity(TblRptoLadder entity) {
        TblRptoLadderDTO dto = mapper.map(entity, TblRptoLadderDTO.class);
        return dto;
    }
    private TblRptoLadder fromDTO(TblRptoLadderDTO dto) {
        TblRptoLadder entity = mapper.map(dto, TblRptoLadder.class);
        return entity;
    }
    public ResponseEntity<?> delete(Integer id){
        try {
            tblRptoLadderDAO.deleteById(id)
            ;
            return ResponseEntity.ok(new MessageResponse("Xóa bậc thang thành công"));
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có cấu hình bậc thang con"));
        }
    }
    public ResponseEntity<?> post(TblRptoLadderDTO input) {

        TblRptoLadder entity = fromDTO(input);

        if (tblRptoLadderDAO.findByLadderName(input.getLadderName().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Tên bậc thang đã tồn tại"));
        }
        entity.setLadderName(input.getLadderName());
        tblRptoLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }
    public ResponseEntity<?> put(int id, TblRptoLadderDTO input) {
        TblRptoLadder entity = tblRptoLadderDAO.findById(id).get();
        if(entity == null) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không tìm thấy bậc thang"));
        }

        entity.setLadderName(input.getLadderName());
        List<TblRptoLadder> list = tblRptoLadderDAO.findAll().stream().filter(item ->
                item.getLadderName().equals(input.getLadderName().trim())
                        && item.getId() != id).collect(Collectors.toList());
        if(list.size() >= 1)
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Tên bậc thang đã tồn tại"));
        tblRptoLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }
    public TblRptoLadderDTO get(int id) {
        return fromEntity(tblRptoLadderDAO.findById(id).orElse(null));
    }

    public List<TblRptoLadderDTO> list() {
        List<TblRptoLadder> dbResult = tblRptoLadderDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
