package com.napas.achoffline.reportoffline.service;


import com.napas.achoffline.reportoffline.define.AchInputMessages;
import com.napas.achoffline.reportoffline.models.AchInputMessagesDTO;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.napas.achoffline.reportoffline.entity.AchOutputMessages;
import com.napas.achoffline.reportoffline.models.AchOutputMessagesDTO;
import com.napas.achoffline.reportoffline.repository.AchInputMessagesDAO;
import com.napas.achoffline.reportoffline.repository.AchOutputMessagesDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author HuyNX
 */
@Service
public class AchMessageService {
    @Autowired
    private AchInputMessagesDAO achInputMsgDAO;

    @Autowired
    private AchOutputMessagesDAO achOutputMsgDAO;

    @Autowired
    private ModelMapper mapper;

    private AchInputMessagesDTO fromInputEntity(AchInputMessages entity) {
        AchInputMessagesDTO dto = mapper.map(entity, AchInputMessagesDTO.class);

        if(entity.getBlock4() != null && entity.getBlock4().length() > 0) {
            dto.setRawMsg(entity.getBlock4());
        } else {
            dto.setRawMsg(new String(entity.getBlock4Large()));
        }

        return dto;
    }

    private AchOutputMessagesDTO fromOutputEntity(AchOutputMessages entity) {
        AchOutputMessagesDTO dto = mapper.map(entity, AchOutputMessagesDTO.class);
        if(entity.getBlock4() != null && entity.getBlock4().length() > 0) {
            dto.setRawMsg(entity.getBlock4());
        } else {
            dto.setRawMsg(new String(entity.getBlock4Large()));
        }

        return dto;
    }

    public List<AchInputMessagesDTO> listInputMessagesByMsgID(String msgID) {
        List<AchInputMessages> listMsg = achInputMsgDAO.findByMxMsgIDOrMxOrigMsgID(Sort.by(Sort.Direction.ASC, "acceptDate"), msgID, msgID);
        List<AchInputMessagesDTO> output = listMsg.stream().map(entity -> fromInputEntity(entity)).collect(Collectors.toList());
        return output;
    }

    public List<AchOutputMessagesDTO> listOutputMessagesByMsgID(String msgID) {
        List<AchInputMessages> listMsg = achInputMsgDAO.findByMxMsgIDOrMxOrigMsgID(Sort.by(Sort.Direction.ASC, "acceptDate"), msgID, msgID);

        List<Long> listInputMsgId = listMsg.stream().map(AchInputMessages::getMessageID).collect(Collectors.toList());

        List<AchOutputMessages> listOutputMsg = achOutputMsgDAO.findByInputMessageIDIn(Sort.by(Sort.Direction.ASC, "creationTime"), listInputMsgId);

        List<AchOutputMessagesDTO> output = listOutputMsg.stream().map(entity -> fromOutputEntity(entity)).collect(Collectors.toList());
        return output;
    }
    
    public List<AchInputMessagesDTO> listInputMessagesDispute(String msgID, String reference) {
        List<AchInputMessages> listMsg = achInputMsgDAO.findByMxMsgIDOrMxOrigMsgID(Sort.by(Sort.Direction.ASC, "acceptDate"), reference, msgID);
        List<AchInputMessagesDTO> output = listMsg.stream().map(entity -> fromInputEntity(entity)).collect(Collectors.toList());
        return output;
    }

    public List<AchOutputMessagesDTO> listOutputMessagesDispute(String msgID, String reference) {
        List<AchInputMessages> listMsg = achInputMsgDAO.findByMxMsgIDOrMxOrigMsgID(Sort.by(Sort.Direction.ASC, "acceptDate"), reference, msgID);

        List<Long> listInputMsgId = listMsg.stream().map(AchInputMessages::getMessageID).collect(Collectors.toList());
        reference = "%" + reference + "%";
        List<AchOutputMessages> listOutputMsg = achOutputMsgDAO.findByInputMessageIDIn(
                Sort.by(Sort.Direction.ASC, "creationTime"), listInputMsgId);

        List<AchOutputMessagesDTO> output = listOutputMsg.stream().map(entity -> fromOutputEntity(entity)).collect(Collectors.toList());
        return output;
    }
}
