package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipant;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantNRTRequestDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantNRTRequestDTO;
import com.napas.achoffline.reportoffline.models.TblTariffPlanParticipantNRTDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblAchBankDAO;
import com.napas.achoffline.reportoffline.repository.TblAchBankParticipantsDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanParticipantDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanParticipantNRTMultiDAO;
import org.checkerframework.checker.units.qual.C;
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

public class TblTariffPlanParticipantMultiService {
    @Autowired
    private TblTariffPlanParticipantNRTMultiDAO tariffPlanParticipantNRTMultiDAO;
    @Autowired
    private ModelMapper mapper;

    private TblTariffPlanParticipantDTO fromEntity(TblTariffPlanParticipant entity) {
        TblTariffPlanParticipantDTO dto = mapper.map(entity, TblTariffPlanParticipantDTO.class);
        return dto;
    }


    public List<TblTariffPlanParticipantNRTDTO> list(Integer tariffPlanId, String channelId, ChannelType channelType, String bic) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "bic", "channelType", "channelId");
        Set<Integer> setTariffPlanId = new LinkedHashSet<>();
        Set<String> setChannelId =  new LinkedHashSet<>();
        Set<ChannelType> setChannelType =  new LinkedHashSet<>();
        // list DTO
        List<TblTariffPlanParticipantDTO> list = tariffPlanParticipantNRTMultiDAO.getAllByBicAndTariffPlanIdAndAndChannelIdAndChannelType(tariffPlanId, channelId, channelType,bic).stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
        //list tariffpalndid
        for (TblTariffPlanParticipantDTO t : list) {
            System.out.println(t.getTariffPlanId());
            setTariffPlanId.add(t.getTariffPlanId());
            setChannelType.add(t.getChannelType());
            setChannelId.add(t.getChannelId());
        }
        System.out.println(setTariffPlanId);
        List<TblTariffPlanParticipantNRTDTO> tblTctvDTOS = new ArrayList<>();
        for (Integer tariffplanid : setTariffPlanId) {
            for (String channelid : setChannelId
            ) {
                for (ChannelType channeltype : setChannelType
                ) {

                    List<String> bics = new ArrayList<>();
                    if((channeltype==ChannelType.SPECIAL&&channelid!=null)||channeltype==ChannelType.NORMAL){
                        for (TblTariffPlanParticipant t : tariffPlanParticipantNRTMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tariffplanid, channelid, channeltype)
                        ) {
                            bics.add(t.getBic());
                        }
                        if (!bics.isEmpty()) {
                            TblTariffPlanParticipantNRTDTO tblTctvDTO = new TblTariffPlanParticipantNRTDTO();
                            tblTctvDTO.setTariffPlanId(tariffplanid);
                            tblTctvDTO.setChannelId(channelid);
                            tblTctvDTO.setChannelType(channeltype);
                            tblTctvDTO.setBic(bics);
                            tblTctvDTOS.add(tblTctvDTO);
                        }
                    }

                }
            }
        }
        return tblTctvDTOS;
    }

    public TblTariffPlanParticipantNRTRequestDTO get(int tariffPlanId) {
        TblTariffPlanParticipantNRTRequestDTO tblTctvDTO = new TblTariffPlanParticipantNRTRequestDTO();
        tblTctvDTO.setTariffPlanIdOld(tariffPlanId);
        List<String> bic = new ArrayList<>();
        for (TblTariffPlanParticipant t : tariffPlanParticipantNRTMultiDAO.findByTariffPlanId(tariffPlanId)
        ) {
            bic.add(t.getBic());
        }
        tblTctvDTO.setBic(bic);
        return tblTctvDTO;
    }

    public ResponseEntity<?> post(TblTariffPlanParticipantNRTDTO tblTctvDTO) throws ParseException {
        List<String> add = tblTctvDTO.getBic();
        List<TblTariffPlanParticipant> tblTariffPlanParticipantList = new ArrayList<>();
        if (tariffPlanParticipantNRTMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblTctvDTO.getTariffPlanId(), tblTctvDTO.getChannelId(), tblTctvDTO.getChannelType()).isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date createdDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
            Date modifiedDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
            for (String bic: add
            ) {
                List<TblTariffPlanParticipant> tblAchBankParticipantsList = tariffPlanParticipantNRTMultiDAO.findByBic(bic);
                ResponseEntity<?> checkNewPlan = isNewPlanValid(tblTctvDTO, tblAchBankParticipantsList);
                if (checkNewPlan.getStatusCode() == HttpStatus.ACCEPTED) {
                    TblTariffPlanParticipant tblTariffPlanParticipant = new TblTariffPlanParticipant(
                            bic,
                            tblTctvDTO.getTariffPlanId(),
                            tblTctvDTO.getChannelId(),
                            tblTctvDTO.getChannelType(),
                            createdDate,
                            modifiedDate
                    );
                    tblTariffPlanParticipantList.add(tblTariffPlanParticipant);
                }
            }
            if (tblTctvDTO.getBic().size() == tblTariffPlanParticipantList.size()) {
                try {
                    tariffPlanParticipantNRTMultiDAO.saveAll(tblTariffPlanParticipantList);
                    return ResponseEntity.ok(tblTariffPlanParticipantList);
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
    public ResponseEntity<?> put(TblTariffPlanParticipantNRTRequestDTO tblTctvDTO) throws ParseException {
        List<TblTariffPlanParticipantDTO> list = tariffPlanParticipantNRTMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblTctvDTO.getOldTariffPlanId(), tblTctvDTO.getChannelId(), tblTctvDTO.getChannelType()).stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
        List<TblTariffPlanParticipantDTO> tblList = list.stream().filter(h -> h.getTariffPlanId()==tblTctvDTO.getOldTariffPlanId()).collect(Collectors.toList());
        List<String> bicCheck = new ArrayList<>();
        for (TblTariffPlanParticipantDTO t :tblList
        ) {
            bicCheck.add(t.getBic());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date createdDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));
        Date modifiedDate = simpleDateFormat.parse(String.valueOf(LocalDateTime.now()));


        List<String> check = bicCheck.stream().filter(h->(tblTctvDTO.getBic().stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        List<String> add = tblTctvDTO.getBic().stream().filter(h->!(bicCheck.stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        List<String> delete = bicCheck.stream().filter(h->!(tblTctvDTO.getBic().stream().anyMatch(b->b.equals(h)))).collect(Collectors.toList());//
        if(!add.stream().anyMatch(
                h->(tariffPlanParticipantNRTMultiDAO.findByChannelIdAndChannelType(tblTctvDTO.getChannelId(),tblTctvDTO.getChannelType()).stream().map(
                        b->b.getBic()).collect(Collectors.toList())).stream().anyMatch(
                        c->c.equals(h))))
        {
            if(tblTctvDTO.getOldTariffPlanId()==tblTctvDTO.getNewTariffPlanId()){
                //xoa phan tu
                for (String s : delete
                ) {
                    tariffPlanParticipantNRTMultiDAO.deleteById(tariffPlanParticipantNRTMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(
                            s,
                            tblTctvDTO.getOldTariffPlanId(),
                            tblTctvDTO.getChannelId(),
                            tblTctvDTO.getChannelType()
                    ).getTariffPlanParticipantId());
                }
                for (String bic: add
                ) {
                    tariffPlanParticipantNRTMultiDAO.save(new TblTariffPlanParticipant(
                            bic,
                            tblTctvDTO.getNewTariffPlanId(),
                            tblTctvDTO.getChannelId(),
                            tblTctvDTO.getChannelType(),
                            createdDate,
                            modifiedDate
                    ));
                }

            }else {
                if(tariffPlanParticipantNRTMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tblTctvDTO.getNewTariffPlanId(), tblTctvDTO.getChannelId(),tblTctvDTO.getChannelType()).isEmpty()
                ){
                    for (String s : delete
                    ) {
                        tariffPlanParticipantNRTMultiDAO.deleteById(tariffPlanParticipantNRTMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(
                                s,
                                tblTctvDTO.getOldTariffPlanId(),
                                tblTctvDTO.getChannelId(),
                                tblTctvDTO.getChannelType()
                        ).getTariffPlanParticipantId());
                    }
                    //add theem phaan tuwr
                    for (String bic: add
                    ) {
                        tariffPlanParticipantNRTMultiDAO.save(new TblTariffPlanParticipant(
                                bic,
                                tblTctvDTO.getNewTariffPlanId(),
                                tblTctvDTO.getChannelId(),
                                tblTctvDTO.getChannelType(),
                                createdDate,
                                modifiedDate
                        ));
                    }
                    for (String s: check
                    ) {
                        tariffPlanParticipantNRTMultiDAO.save(
                                new TblTariffPlanParticipant(
                                        s,
                                        tblTctvDTO.getNewTariffPlanId(),
                                        tblTctvDTO.getChannelId(),
                                        tblTctvDTO.getChannelType(),
                                        tariffPlanParticipantNRTMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(s,tblTctvDTO.getOldTariffPlanId(),tblTctvDTO.getChannelId(),tblTctvDTO.getChannelType()).getTariffPlanParticipantId(),
                                        tariffPlanParticipantNRTMultiDAO.findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(s,tblTctvDTO.getOldTariffPlanId(),tblTctvDTO.getChannelId(),tblTctvDTO.getChannelType()).getDateCreated(),
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
        return ResponseEntity.ok(tblTctvDTO);
    }
    public ResponseEntity<?> delete( Integer tariffPlanId, String channelId, ChannelType channelType ){
        for (TblTariffPlanParticipant t : tariffPlanParticipantNRTMultiDAO.getAllByTariffPlanIdAndAndChannelIdAndChannelType(tariffPlanId, channelId, channelType)
        ) {
            tariffPlanParticipantNRTMultiDAO.delete(t);
        }
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
    private ResponseEntity<?> isNewPlanValid(TblTariffPlanParticipantNRTDTO input, List<TblTariffPlanParticipant> listPlanByBic) {
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
