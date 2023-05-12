package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblDnsFee;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblDnsFeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TblDnsFeeService {
    @Autowired
    private TblDnsFeeDAO tblDnsFeeDAO;
    public List<TblDnsFee> getAll(){
        return tblDnsFeeDAO.findAll();
    }
    public Optional<TblDnsFee> get(Long id){
        return tblDnsFeeDAO.findById(id);
    }
    public ResponseEntity<?> insert(TblDnsFee tblDnsFee){
        tblDnsFee.setCode(tblDnsFee.getCode().trim());
        tblDnsFee.setName(tblDnsFee.getName().trim());
        if (tblDnsFeeDAO.findTblDnsFeeByCode(tblDnsFee.getCode()).isEmpty()) {
            tblDnsFeeDAO.save(tblDnsFee);
            return ResponseEntity.ok(tblDnsFee);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Tên hoặc Mã cảnh báo đã tồn tại"));
        }
    }
    public ResponseEntity<?> update(Long id, TblDnsFee tblDnsFee){
        tblDnsFee.setCode(tblDnsFee.getCode().trim());
        tblDnsFee.setName(tblDnsFee.getName().trim());
        if(tblDnsFeeDAO.findTblDnsFeeByCode(tblDnsFee.getCode()).isEmpty()||
                tblDnsFeeDAO.findById(id).get().getCode().equals(tblDnsFee.getCode())){
            tblDnsFee.setId(id);
            tblDnsFeeDAO.save(tblDnsFee);
            return ResponseEntity.ok(tblDnsFee);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Tên hoặc Mã cảnh báo đã tồn tại"));
        }
    }
    public ResponseEntity<?> delete(Long id) {
        tblDnsFeeDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa cấu hình phí thành công"));
    }
}
