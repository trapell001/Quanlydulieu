package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblBocFee;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblBocFeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TblBocFeeService {
    @Autowired
    private TblBocFeeDAO tblBocFeeDAO;
    public List<TblBocFee> getAll(){
        return tblBocFeeDAO.findAll();
    }
    public Optional<TblBocFee> get(Long id){
        return tblBocFeeDAO.findById(id);
    }
    public ResponseEntity<?> insert(TblBocFee tblBocFee){
        tblBocFee.setCode(tblBocFee.getCode().trim());
        tblBocFee.setName(tblBocFee.getName().trim());
        if (tblBocFeeDAO.findTblBocFeeByCode(tblBocFee.getCode()).isEmpty()) {
            tblBocFeeDAO.save(tblBocFee);
            return ResponseEntity.ok(tblBocFee);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Tên hoặc Mã cảnh báo đã tồn tại"));
        }
    }
    public ResponseEntity<?> update(Long id, TblBocFee tblBocFee){
        tblBocFee.setCode(tblBocFee.getCode().trim());
        tblBocFee.setName(tblBocFee.getName().trim());
        if(tblBocFeeDAO.findTblBocFeeByCode(tblBocFee.getCode()).isEmpty()||
                tblBocFeeDAO.findById(id).get().getCode().equals(tblBocFee.getCode())){
            tblBocFee.setId(id);
            tblBocFeeDAO.save(tblBocFee);
            return ResponseEntity.ok(tblBocFee);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Tên hoặc Mã cảnh báo đã tồn tại"));
        }
    }
    public ResponseEntity<?> delete(Long id) {
        tblBocFeeDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa cấu hình phí thành công"));
    }
}
