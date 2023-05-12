package com.napas.achoffline.reportoffline.service;


import com.napas.achoffline.reportoffline.entity.HisBillingParticipant;
import com.napas.achoffline.reportoffline.entity.HisReportOnline;
import com.napas.achoffline.reportoffline.models.HisBillingParticipantDTO;
import com.napas.achoffline.reportoffline.models.HisReportOnlineDTO;
import com.napas.achoffline.reportoffline.models.HisReportOnlinePerformaneDTO;
import com.napas.achoffline.reportoffline.models.HisReportOnlineResendDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisReportOnlineDAO;
import com.napas.achoffline.reportoffline.utils.CalculateS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class HisReportOnlineService extends BaseService {
    @Value(value = "${napas.ach.offline.export.export-export-dir}")
    private String camtROExportDir;
    @Value(value = "${resend.urlSendRO}")
    private String url;
    @Value(value = "${resend.secret}")
    private String secret;
    @Value(value = "${resend.sha256_HMAC}")
    private String sha256HMAC;
    @Value(value = "${resend.HMAC-SHA256}")
    private String HMAC_SHA256;
    @Autowired
    private HisReportOnlineDAO hisReportOnlineDAO;
    public ResponseEntity<?> graphParticipant(   String fromSessionId,
                                          String toSessionId,
                                          String participant,
                                          String systemModule,
                                          String deliveryMethod,
                                          String rptType) throws ParseException {
        List<HisReportOnlinePerformaneDTO> list = hisReportOnlineDAO.graphParticipant(fromSessionId,toSessionId,participant , systemModule,deliveryMethod,rptType);
        List<HisReportOnlinePerformaneDTO> newList = new ArrayList<>();
        for(HisReportOnlinePerformaneDTO item : list) {
            Double temp = (double) (item.getProcessingTime() * 100000);
            if(item.getSlgd()!=0) {
                temp = (double) (temp / item.getSlgd());
                HisReportOnlinePerformaneDTO h = new HisReportOnlinePerformaneDTO();
                h.setSlgd(item.getSlgd());
                h.setSession(item.getSession());
                h.setProcessingTime(temp);
                newList.add(h);
            }else {
                HisReportOnlinePerformaneDTO h = new HisReportOnlinePerformaneDTO();
                h.setSlgd(item.getSlgd());
                h.setSession(item.getSession());
                h.setProcessingTime(Double.valueOf(0));
                newList.add(h);
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisReportOnlinePerformaneDTO::getSession)).collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }
    public ResponseEntity<?> graph(   String fromSessionId,
                                      String toSessionId,
                                      String systemModule,
                                      String deliveryMethod,
                                      String rptType
    ) throws ParseException {
        List<HisReportOnlinePerformaneDTO> list = hisReportOnlineDAO.graph(fromSessionId,toSessionId , systemModule,deliveryMethod,rptType);
        List<HisReportOnlinePerformaneDTO> newList = new ArrayList<>();
        for(HisReportOnlinePerformaneDTO item : list) {
            Double temp = (double) (item.getProcessingTime() * 100000);
            if(item.getSlgd()!=0) {
                temp = (double) (temp / item.getSlgd());
                HisReportOnlinePerformaneDTO h = new HisReportOnlinePerformaneDTO();
                h.setSlgd(item.getSlgd());
                h.setSession(item.getSession());
                h.setProcessingTime(temp);
                newList.add(h);
            }else {
                HisReportOnlinePerformaneDTO h = new HisReportOnlinePerformaneDTO();
                h.setSlgd(item.getSlgd());
                h.setSession(item.getSession());
                h.setProcessingTime(Double.valueOf(0));
                newList.add(h);
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisReportOnlinePerformaneDTO::getSession)).collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }

    public ResponseEntity<?> pageSearching(
            LocalDateTime beginDate,
            LocalDateTime endDate,
            String fromSessionId,
            String toSessionId,
            String rptType,
            String participant,
            String systemModule,
            Long pageNumber,
            String status,
            String deliveryMethod,
            String msgId,
            String groupMsgId,
            String fireType,
            Integer page,
            Integer pageSize){

        try {
            Pageable sortedById = null;

            Sort sort = Sort.by("CREATE_DATE").descending();

            if (pageSize == -1) {
                sortedById = Pageable.unpaged();
            } else {
                sortedById = PageRequest.of(page, pageSize, sort);
            }

            Page<HisReportOnlineDTO> listPayments = hisReportOnlineDAO.pageSearching(
                    beginDate,
                    endDate,
                    fromSessionId,
                    toSessionId,
                    rptType,
                    participant,
                    systemModule,
                    pageNumber,
                    status,
                    deliveryMethod,
                    msgId,
                    groupMsgId,
                    fireType,
                    sortedById);
            System.out.println("list"+ listPayments.toString());

            return ResponseEntity.ok(listPayments);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Lỗi truy vấn"));
        }
    }
    public HisReportOnlineDTO get(Long id){
        return hisReportOnlineDAO.findById(id);
    }
    public ResponseEntity<?> sendRO(HisReportOnlineResendDTO hisReportOnlineResendDTO) throws NoSuchAlgorithmException, InvalidKeyException, IOException, URISyntaxException, KeyStoreException, KeyManagementException {
        String timeStamp= String.valueOf(System.currentTimeMillis());
        String jsonText = hisReportOnlineResendDTO.toString();
        String message = String.valueOf(new StringBuilder(timeStamp+";"+jsonText));
        Mac sha256_HMAC = Mac.getInstance(sha256HMAC);
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), sha256HMAC);
        sha256_HMAC.init(secret_key);
        String hash = HMAC_SHA256+" "+ Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));//author
        RestTemplate restTemplate = restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",hash);
        headers.set("X-TimeStamp",timeStamp);
        HttpEntity request = new HttpEntity(jsonText,headers);
        URI uri = new URI(url);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return ResponseEntity.ok(response.getStatusCode().toString());
    }


    public void export(Long id, HttpServletResponse response) throws IOException {
        HisReportOnlineDTO hisReportOnlineDTO = hisReportOnlineDAO.findById(id);
        System.out.println(hisReportOnlineDTO.getDeliveryMethod());
        if (hisReportOnlineDTO.getDeliveryMethod().equals("OFFLINE")) {
            String filePath = camtROExportDir + "/" + "exportRO"+hisReportOnlineDTO.getMsgId()+".json";
            JSONObject json = new JSONObject();
            Field[] fields = hisReportOnlineDTO.getClass().getDeclaredFields();
            json.put(fields[0].getName(), hisReportOnlineDTO.getCreatDate());
            json.put(fields[1].getName(), hisReportOnlineDTO.getStartDate());
            json.put(fields[2].getName(), hisReportOnlineDTO.getEndDate());
            json.put(fields[3].getName(), hisReportOnlineDTO.getRptType());
            json.put(fields[4].getName(), hisReportOnlineDTO.getParticipant());
            json.put(fields[5].getName(), hisReportOnlineDTO.getAccount());
            json.put(fields[6].getName(), hisReportOnlineDTO.getSystemModule());
            json.put(fields[7].getName(), hisReportOnlineDTO.getPageNumber());
            json.put(fields[8].getName(), hisReportOnlineDTO.getStatus());
            json.put(fields[9].getName(), hisReportOnlineDTO.getSessionId());
            json.put(fields[10].getName(), hisReportOnlineDTO.getTotalPage());
            json.put(fields[11].getName(), hisReportOnlineDTO.getNbofntries());
            json.put(fields[12].getName(), hisReportOnlineDTO.getDeliveryMethod());
            json.put(fields[13].getName(), hisReportOnlineDTO.getMsgId());
            json.put(fields[14].getName(), hisReportOnlineDTO.getGroupMsgId());
            json.put(fields[15].getName(), hisReportOnlineDTO.getTotalEntries());
            json.put(fields[16].getName(), hisReportOnlineDTO.getFireType());
            json.put(fields[17].getName(), hisReportOnlineDTO.getHmacDigest());
            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))){
                out.write(json.toString());
            }
            File file = new File(filePath);

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + "exportRO"+hisReportOnlineDTO.getMsgId()+".json");
            response.setHeader("X-Suggested-Filename", "exportRO"+hisReportOnlineDTO.getMsgId()+".json");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // Content-Length
            response.setContentLength((int) file.length());

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStream.close();
            System.out.println("ra file json");

        }
        if (hisReportOnlineDTO.getDeliveryMethod().equals("ONLINE")) {
            String filePath = camtROExportDir + "/" + "exportRO"+hisReportOnlineDTO.getMsgId()+".xml";
            Field[] fields = hisReportOnlineDTO.getClass().getDeclaredFields();
            String xml =  "<"+ hisReportOnlineDTO.getMsgId()+">" +
                    "<"+fields[0].getName()+">"+hisReportOnlineDTO.getCreatDate()+ "</"+fields[0].getName()+">"+
                    "<"+fields[1].getName()+">"+hisReportOnlineDTO.getStartDate()+ "</"+fields[0].getName()+">"+
                    "<"+fields[2].getName()+">"+hisReportOnlineDTO.getEndDate()+ "</"+fields[0].getName()+">"+
                    "<"+fields[3].getName()+">"+hisReportOnlineDTO.getRptType()+ "</"+fields[0].getName()+">"+
                    "<"+fields[4].getName()+">"+hisReportOnlineDTO.getParticipant()+ "</"+fields[0].getName()+">"+
                    "<"+fields[5].getName()+">"+hisReportOnlineDTO.getAccount()+ "</"+fields[0].getName()+">"+
                    "<"+fields[6].getName()+">"+hisReportOnlineDTO.getSystemModule()+ "</"+fields[0].getName()+">"+
                    "<"+fields[7].getName()+">"+hisReportOnlineDTO.getPageNumber()+ "</"+fields[0].getName()+">"+
                    "<"+fields[8].getName()+">"+hisReportOnlineDTO.getStatus()+ "</"+fields[0].getName()+">"+
                    "<"+fields[9].getName()+">"+hisReportOnlineDTO.getSessionId()+ "</"+fields[0].getName()+">"+
                    "<"+fields[10].getName()+">"+hisReportOnlineDTO.getTotalPage()+ "</"+fields[0].getName()+">"+
                    "<"+fields[11].getName()+">"+hisReportOnlineDTO.getNbofntries()+ "</"+fields[0].getName()+">"+
                    "<"+fields[12].getName()+">"+hisReportOnlineDTO.getDeliveryMethod()+ "</"+fields[0].getName()+">"+
                    "<"+fields[13].getName()+">"+hisReportOnlineDTO.getMsgId()+ "</"+fields[0].getName()+">"+
                    "<"+fields[14].getName()+">"+hisReportOnlineDTO.getGroupMsgId()+ "</"+fields[0].getName()+">"+
                    "<"+fields[15].getName()+">"+hisReportOnlineDTO.getTotalEntries()+ "</"+fields[0].getName()+">"+
                    "<"+fields[16].getName()+">"+hisReportOnlineDTO.getFireType()+ "</"+fields[0].getName()+">"+
                    "<"+fields[17].getName()+">"+hisReportOnlineDTO.getHmacDigest()+ "</"+fields[0].getName()+">"+
                    "</"+hisReportOnlineDTO.getMsgId()+">";

            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))){
                out.write(xml);
            }

            File file = new File(filePath);

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + "exportRO"+hisReportOnlineDTO.getMsgId()+"xml");
            response.setHeader("X-Suggested-Filename", "exportRO"+hisReportOnlineDTO.getMsgId()+".xml");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // Content-Length
            response.setContentLength((int) file.length());
            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStream.close();
            System.out.println("ra file xml");
        }
    }
}