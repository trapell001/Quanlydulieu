package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblPaymentChannel;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblPaymentChannelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TblPaymentChannelService {
    @Autowired
    private TblPaymentChannelDAO tblPaymentChannelDAO;
    public List<TblPaymentChannel> getAll(){
        return tblPaymentChannelDAO.findAll();
    }
    public Optional<TblPaymentChannel> get(Long id){
        return tblPaymentChannelDAO.findById(id);
    }
    public ResponseEntity<?> insert(TblPaymentChannel tblPaymentChannel) {
        tblPaymentChannel.setChannelId(tblPaymentChannel.getChannelId().trim());
        tblPaymentChannel.setChannelName(tblPaymentChannel.getChannelName().trim());
        try {
            return ResponseEntity.ok(tblPaymentChannelDAO.save(tblPaymentChannel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Mã hoặc tên đã tồn tại"));
        }
    }
    public ResponseEntity<?> update(Long id, TblPaymentChannel tblPaymentChannel){
        tblPaymentChannel.setChannelId(tblPaymentChannel.getChannelId().trim());
        tblPaymentChannel.setChannelName(tblPaymentChannel.getChannelName().trim());
        try {
                tblPaymentChannel.setId(id);
                tblPaymentChannelDAO.save(tblPaymentChannel);
                return ResponseEntity.ok(tblPaymentChannel);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Mã đã tồn tại"));
        }
    }
    public ResponseEntity<?> delete(Long id) {
        tblPaymentChannelDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa cấu hình phí thành công"));
    }
}
