package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblBillingSpecialCampaign;
import com.napas.achoffline.reportoffline.models.*;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblBillingSpecialCampaignDAO;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblBillingSpecialCampaignService {

    @Autowired
    private TblBillingSpecialCampaignDAO tblBillingSpecialCampaignDAO;

    @Autowired
    private ModelMapper mapper;

    private TblBillingSpecialCampaignDTO fromEntity(TblBillingSpecialCampaign entity) {
        TblBillingSpecialCampaignDTO dto = mapper.map(entity, TblBillingSpecialCampaignDTO.class);
        return dto;
    }
    public TblBillingSpecialCampaignDTO get(Long id) {
        return fromEntity(tblBillingSpecialCampaignDAO.findById(id).orElse(null));
    }
    public List<TblBillingSpecialCampaignDTO> list() {
        List<TblBillingSpecialCampaign> dbResult = tblBillingSpecialCampaignDAO.searchAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public ResponseEntity<?> post(TblBillingSpecialCampaignDTO input) {

        try {
            TblBillingSpecialCampaign entity = new TblBillingSpecialCampaign();
            entity.setCampaignCode(input.getCampaignCode().trim());
            if (StringUtils.isBlank(input.getCampaignName())){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Tên chương trình không được để trống"));
            }
            entity.setCampaignName(input.getCampaignName());
            entity.setModifDate(new Date());
            tblBillingSpecialCampaignDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Mã chương trình đã tồn tại"));
        }
    }

    public ResponseEntity<?>put(Long id,TblBillingSpecialCampaignDTO input){

        try {
            TblBillingSpecialCampaign entity = tblBillingSpecialCampaignDAO.findById(id).orElse(null);
            entity.setCampaignCode(input.getCampaignCode().trim());
            if (StringUtils.isBlank(input.getCampaignName())){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Tên chương trình không được để trống"));
            }
            entity.setCampaignName(input.getCampaignName());
            entity.setModifDate(new Date());
            tblBillingSpecialCampaignDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Mã chương trình đã tồn tại"));
        }
    }
    public ResponseEntity<?> delete(Long id){
        tblBillingSpecialCampaignDAO.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }
}
