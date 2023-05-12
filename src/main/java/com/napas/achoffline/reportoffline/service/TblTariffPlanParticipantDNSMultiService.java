package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipantDNS;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDNSDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDNSMultiRequestDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDNSMultiDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblAchBankDAO;
import com.napas.achoffline.reportoffline.repository.TblAchBankParticipantsDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanParticipantDNSMultiDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class TblTariffPlanParticipantDNSMultiService {
    @Autowired
    private TblTariffPlanParticipantDNSMultiDAO tariffPlanParticipantDNSMultiDAO;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private TblAchBankDAO tblAchBankDAO;
    @Autowired
    private TblAchBankParticipantsDAO tblAchBankParticipantsDAO;

    private TblTariffPlanParticipantDNSDTO fromEntity(TblTariffPlanParticipantDNS entity) {
        TblTariffPlanParticipantDNSDTO dto = mapper.map(entity, TblTariffPlanParticipantDNSDTO.class);
        return dto;
    }


    public List<TblTariffPlanParticipantDNSMultiDTO> list(Integer tariffPlanId, String channelId, ChannelType channelType, String bic) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "bic", "channelType", "channelId");
        Set<Integer> setTariffPlanId = new LinkedHashSet<>();
        Set<String> setChannelId =  new LinkedHashSet<>();
        Set<ChannelType> setChannelType =  new LinkedHashSet<>();
        // list DTO
        List<TblTariffPlanParticipantDNSDTO> list = tariffPlanParticipantDNSMultiDAO.getAllByBicAndTariffPlanIdAndAndChannelIdAndChannelType(tariffPlanId, channelId, channelType,bic).stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
        //list tariffpalndid
        for (TblTariffPlanParticipantDNSDTO t : list) {
            System.out.println(t.getTariffPlanId());
            setTariffPlanId.add(t.getTariffPlanId());
            setChannelType.add(t.getChannelType());
            setChannelId.add(t.getChannelId());
        }
        System.out.println(setTariffPlanId);
        List<TblTariffPlanParticipantDNSMultiDTO> tblDNSTctvDTOS = new ArrayList<>();
        for (Integer tariffplanid : setTariffPlanId) {
            for (String channelid : setChannelId
            ) {
                for (ChannelType channeltype : setChannelType
                ) {

                    List<String> bics = new ArrayList<>();
                    if((channeltype==ChannelType.SPECIAL&&channelid!=null)||channeltype==ChannelType.NORMAL){
                        for (TblTariffPlanParticipantDNS t : tariffPlanParticipantDNSMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tariffplanid, channelid, channeltype)
                        ) {
                            bics.add(t.getBic());
                        }
                        if (!bics.isEmpty()) {
                            TblTariffPlanParticipantDNSMultiDTO tblDNSTctvDTO = new TblTariffPlanParticipantDNSMultiDTO();
                            tblDNSTctvDTO.setTariffPlanId(tariffplanid);
                            tblDNSTctvDTO.setChannelId(channelid);
                            tblDNSTctvDTO.setChannelType(channeltype);
                            tblDNSTctvDTO.setBic(bics);
                            tblDNSTctvDTOS.add(tblDNSTctvDTO);
                        }
                    }

                }
            }
        }
        return tblDNSTctvDTOS;
    }

    public TblTariffPlanParticipantDNSMultiRequestDTO get(int tariffPlanId) {
        TblTariffPlanParticipantDNSMultiRequestDTO tblDNSTctvDTO = new TblTariffPlanParticipantDNSMultiRequestDTO();
        tblDNSTctvDTO.setTariffPlanIdOld(tariffPlanId);
        List<String> bic = new ArrayList<>();
        for (TblTariffPlanParticipantDNS t : tariffPlanParticipantDNSMultiDAO.findByTariffPlanId(tariffPlanId)
        ) {
            bic.add(t.getBic());
        }
        tblDNSTctvDTO.setBic(bic);
        return tblDNSTctvDTO;
    }

    public ResponseEntity<?> post(TblTariffPlanParticipantDNSMultiDTO tblDNSTctvDTO) throws ParseException {
        List<String> add = tblDNSTctvDTO.getBic();
        List<TblTariffPlanParticipantDNS> tblTariffPlanParticipantDNSList = new ArrayList<>();
        if (tariffPlanParticipantDNSMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblDNSTctvDTO.getTariffPlanId(), tblDNSTctvDTO.getChannelId(), tblDNSTctvDTO.getChannelType()).isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date createdDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
            Date modifiedDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
            for (String bic: add
            ) {
                List<TblTariffPlanParticipantDNS> tblAchBankParticipantsList = tariffPlanParticipantDNSMultiDAO.findByBic(bic);
                ResponseEntity<?> checkNewPlan = isNewPlanValid(tblDNSTctvDTO, tblAchBankParticipantsList);
                if (checkNewPlan.getStatusCode() == HttpStatus.ACCEPTED) {
                    TblTariffPlanParticipantDNS tblTariffPlanParticipantDNS = new TblTariffPlanParticipantDNS(
                            bic,
                            tblDNSTctvDTO.getTariffPlanId(),
                            tblDNSTctvDTO.getChannelId(),
                            tblDNSTctvDTO.getChannelType(),
                            createdDate,
                            modifiedDate
                    );
                    tblTariffPlanParticipantDNSList.add(tblTariffPlanParticipantDNS);
                }
            }
            if (tblDNSTctvDTO.getBic().size() == tblTariffPlanParticipantDNSList.size()) {
                try {
                    tariffPlanParticipantDNSMultiDAO.saveAll(tblTariffPlanParticipantDNSList);
                    return ResponseEntity.ok(tblTariffPlanParticipantDNSList);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh"));
                }
            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới tổ chức do đã tồn tại tổ chức ở kênh khác"));
            }

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh"));
        }

    }
    public ResponseEntity<?> put(TblTariffPlanParticipantDNSMultiRequestDTO tblDNSTctvDTO) throws ParseException {
        List<TblTariffPlanParticipantDNSDTO> list = tariffPlanParticipantDNSMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblDNSTctvDTO.getOldTariffPlanId(), tblDNSTctvDTO.getChannelId(), tblDNSTctvDTO.getChannelType()).stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
        List<TblTariffPlanParticipantDNSDTO> tblList = list.stream().filter(h -> h.getTariffPlanId()==tblDNSTctvDTO.getOldTariffPlanId()).collect(Collectors.toList());
        List<String> bicCheck = new ArrayList<>();
        for (TblTariffPlanParticipantDNSDTO t :tblList
        ) {
            bicCheck.add(t.getBic());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date createdDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
        Date modifiedDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));


        List<String> check = bicCheck.stream().filter(h->(tblDNSTctvDTO.getBic().stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        List<String> add = tblDNSTctvDTO.getBic().stream().filter(h->!(bicCheck.stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        List<String> delete = bicCheck.stream().filter(h->!(tblDNSTctvDTO.getBic().stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        if(!add.stream().anyMatch(
                h->(tariffPlanParticipantDNSMultiDAO.findByChannelIdAndChannelType(tblDNSTctvDTO.getChannelId(),tblDNSTctvDTO.getChannelType()).stream().map(
                        b->b.getBic()).collect(Collectors.toList())).stream().anyMatch(
                        c->c.equals(h))))
        {
            if(tblDNSTctvDTO.getOldTariffPlanId()==tblDNSTctvDTO.getNewTariffPlanId()){
                //xoa phan tu
                for (String s : delete
                ) {
                    tariffPlanParticipantDNSMultiDAO.deleteById(tariffPlanParticipantDNSMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(
                            s,
                            tblDNSTctvDTO.getOldTariffPlanId(),
                            tblDNSTctvDTO.getChannelId(),
                            tblDNSTctvDTO.getChannelType()
                    ).getTariffPlanParticipantId());
                }
                for (String bic: add
                     ) {
                    tariffPlanParticipantDNSMultiDAO.save(new TblTariffPlanParticipantDNS(
                            bic,
                            tblDNSTctvDTO.getNewTariffPlanId(),
                            tblDNSTctvDTO.getChannelId(),
                            tblDNSTctvDTO.getChannelType(),
                            createdDate,
                            modifiedDate
                    ));
                }

            }else {
                if(tariffPlanParticipantDNSMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblDNSTctvDTO.getNewTariffPlanId(), tblDNSTctvDTO.getChannelId(),tblDNSTctvDTO.getChannelType()).isEmpty()
                ){
                    for (String s : delete
                    ) {
                        tariffPlanParticipantDNSMultiDAO.deleteById(tariffPlanParticipantDNSMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(
                                s,
                                tblDNSTctvDTO.getOldTariffPlanId(),
                                tblDNSTctvDTO.getChannelId(),
                                tblDNSTctvDTO.getChannelType()
                        ).getTariffPlanParticipantId());
                    }
                    //add theem phaan tuwr
                    for (String bic: add
                    ) {
                        tariffPlanParticipantDNSMultiDAO.save(new TblTariffPlanParticipantDNS(
                                bic,
                                tblDNSTctvDTO.getNewTariffPlanId(),
                                tblDNSTctvDTO.getChannelId(),
                                tblDNSTctvDTO.getChannelType(),
                                createdDate,
                                modifiedDate
                        ));
                    }
                    for (String s: check
                    ) {
                        tariffPlanParticipantDNSMultiDAO.save(
                                new TblTariffPlanParticipantDNS(
                                        s,
                                        tblDNSTctvDTO.getNewTariffPlanId(),
                                        tblDNSTctvDTO.getChannelId(),
                                        tblDNSTctvDTO.getChannelType(),
                                        tariffPlanParticipantDNSMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(s,tblDNSTctvDTO.getOldTariffPlanId(),tblDNSTctvDTO.getChannelId(),tblDNSTctvDTO.getChannelType()).getTariffPlanParticipantId(),
                                        tariffPlanParticipantDNSMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(s,tblDNSTctvDTO.getOldTariffPlanId(),tblDNSTctvDTO.getChannelId(),tblDNSTctvDTO.getChannelType()).getDateCreated(),
                                        modifiedDate
                                )
                        );
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new MessageResponse("Đã tồn tại biểu phí"));
                }
            }
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Đã tồn tại biểu phí trong kênh khác"));
        }
        return ResponseEntity.ok(tblDNSTctvDTO);
    }
    public ResponseEntity<?> delete( Integer tariffPlanId, String channelId, ChannelType channelType ){
        for (TblTariffPlanParticipantDNS t : tariffPlanParticipantDNSMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tariffPlanId, channelId, channelType)
        ) {
            tariffPlanParticipantDNSMultiDAO.delete(t);
        }
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
    private ResponseEntity<?> isNewPlanValid(TblTariffPlanParticipantDNSMultiDTO input, List<TblTariffPlanParticipantDNS> listPlanByBic) {
        ChannelType newChannelType = input.getChannelType();

        //Không cho phép chọn type ALL
        if (newChannelType == ChannelType.ALL) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Không chập nhận type ALL"));
        }

        //Xem dã tồn tại type All chưa
        boolean typeAllExisted = listPlanByBic.stream().anyMatch(p -> p.getChannelType() == ChannelType.ALL);

        if (typeAllExisted) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh"));
        }

        //Kiểm tra channel type mới
        if (newChannelType == ChannelType.NORMAL) {
            //Kiểm tra xem đã tồn tại plan kênh thông thường nào chưa
            boolean typeNormalExisted = listPlanByBic.stream().anyMatch(p -> p.getChannelType() == ChannelType.NORMAL);

            if (typeNormalExisted) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh thông thường"));
            }
        } else if (newChannelType == ChannelType.ALL) {
            //Kiểm tra xem đã tồn tại bất kỳ plan nào chưa
            if (listPlanByBic.size() > 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho loại khác"));
            }
        } else {
            boolean channelExisted = listPlanByBic.stream().anyMatch(p -> (p.getChannelType() == ChannelType.SPECIAL && p.getChannelId().contentEquals(input.getChannelId())));
            if (channelExisted) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí với kênh này do đã tồn tại trước đó"));
            }
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
