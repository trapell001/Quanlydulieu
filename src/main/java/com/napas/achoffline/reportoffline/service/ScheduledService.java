package com.napas.achoffline.reportoffline.service;

//import com.napas.achoffline.reportoffline.config.EmailConfig;
import com.google.common.collect.Lists;
import com.napas.achoffline.reportoffline.define.SpecialDayType;
import com.napas.achoffline.reportoffline.entity.*;
import com.napas.achoffline.reportoffline.models.TblSessions;
import com.napas.achoffline.reportoffline.models.WorkingDay;
import com.napas.achoffline.reportoffline.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScheduledService {
    @Autowired
    private DisputeDAO disputeDAO;
    @Autowired
    private PaymentsDAO paymentsDAO;
    @Autowired
    private HisAlertEmailConfigDAO hisAlertEmailConfigDAO;

    @Autowired
    private AchSessionsDAO achSessionsDAO;

    @Autowired
    private DAOTblSpecialDay daoTblSpecialDay;
    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;
    @Value("${napas.ach.offline.export.dispute-export-dir}")
    private String disputeExportDir;

    @Scheduled(cron = "0 0 5 * * ?")
    public void sendAttachmentEmailWarningDue() throws MessagingException, ParseException, IOException {
        if(!checkSpecialDay()) return;
        List<HisAlertEmailConfig> list = hisAlertEmailConfigDAO.findAll();
        list = list.stream().filter(item -> item.getAlertCode()
                        .equalsIgnoreCase("M02_DEN_HAN_TRA_SOAT"))
                .collect(Collectors.toList());
        for(HisAlertEmailConfig entity : list) {
            if(entity.getParticipant().equals("NAPASVNV")) {
                sendAttachmentEmailDispute(entity.getEmailReceive(),null,"OPEN");
            } else {
                sendAttachmentEmailDispute(entity.getEmailReceive(),entity.getParticipant(),"OPEN");
            }
        }
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void sendAttachmentEmailWarningExpi() throws MessagingException, ParseException, IOException {
        if(!checkSpecialDay()) return;
        List<HisAlertEmailConfig> list = hisAlertEmailConfigDAO.findAll();
        list = list.stream().filter(item -> item.getAlertCode()
                        .equalsIgnoreCase("M03_QUA_HAN_TRA_SOAT"))
                .collect(Collectors.toList());
        for(HisAlertEmailConfig entity : list) {
            if(entity.getParticipant().equals("NAPASVNV")) {
                sendAttachmentEmailDispute(entity.getEmailReceive(),null,"EXPI");
            } else {
                sendAttachmentEmailDispute(entity.getEmailReceive(),entity.getParticipant(),"EXPI");
            }
        }
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void sendAttachmentEmailWarningPaymentReturn() throws MessagingException, ParseException, IOException {
        if(!checkSpecialDay()) return;
        List<HisAlertEmailConfig> list = hisAlertEmailConfigDAO.findAll();
        list = list.stream().filter(item -> item.getAlertCode()
                        .equalsIgnoreCase("M04_ACSP_NAUT_CHUA_RETURN"))
                .collect(Collectors.toList());
        for(HisAlertEmailConfig entity : list) {
            sendAttachmentEmailPayments(entity.getEmailReceive(),null);
        }
    }

    public void sendAttachmentEmailDispute(String receiver, String tctv, String status) throws MessagingException, ParseException, IOException {
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<DisputeCase> disputeCases = new ArrayList<>();
        String s = searchDateFmt.format(new Date());
        Date date = searchDateFmt.parse(s);
        if (status.equalsIgnoreCase("OPEN")) {
            disputeCases = disputeDAO.getDisputeWarningDue(
                    tctv, date, status);
        } else {
            disputeCases = disputeDAO.getDisputeWarningExpi(
                    tctv, date, status);
        }
        String name = exportDispute(disputeCases, tctv, status, date);

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "10.2.249.63");
        Session session = Session.getDefaultInstance(properties);
        try {
            InternetAddress[] to = InternetAddress.parse(receiver);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("achoffline@napas.com.vn"));
            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject(name.substring(0,name.length() - 5));
            message.setText(name.substring(0,name.length() - 5));
            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(String.format("./mail/%s",name));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(String.format("%s",name));
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public void sendAttachmentEmailPayments(String receiver, String tctv) throws MessagingException, ParseException, IOException {
        List<TblSessions> list = getListSession2WorkingDaysAgo(3L);
        List<Long> sessionId = new ArrayList<>();
        if(list.size() > 0) {
            list.stream().forEach(item -> {
                sessionId.add(item.getId());
            });
        }
        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String s = searchDateFmt.format(new Date());
        Date date = searchDateFmt.parse(s);
        List<Payments> payments = paymentsDAO.getPaymentsWarning(tctv,sessionId);

        String name = exportPayments(payments, tctv, date);

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "10.2.249.63");
        Session session = Session.getDefaultInstance(properties);
        try {
            InternetAddress[] to = InternetAddress.parse(receiver);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("achoffline@napas.com.vn"));
            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject(name.substring(0,name.length() - 5));
            message.setText(name.substring(0,name.length() - 5));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(String.format("./mail/%s",name));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(String.format("%s",name));
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public String exportDispute(List<DisputeCase> disputeCases, String tctv, String status, Date date) throws IOException {
        disputeCases.stream().forEach(entity -> {
            entity.setParticipantAssigner(entity.getParticipantAssigner().substring(0, 8));
            entity.setParticipantAssignee(entity.getParticipantAssignee().substring(0, 8));
            entity.setParticipantRespondent(entity.getParticipantRespondent().substring(0, 8));
        });

        disputeCases = disputeCases.stream().sorted(Comparator.comparing(DisputeCase::getCreateDate)).collect(Collectors.toList());

        int disputeCount = disputeCases.size();

        if (disputeCount <= maxRowsForXlsx) { // paymentExportDir
            DisputeWarningExporter excelExporter = new DisputeWarningExporter(disputeCases, disputeExportDir, tctv, status, date);
            String result = excelExporter.export();
            return result;
        }
        return null;
    }

    public String exportPayments(List<Payments> payments, String tctv, Date date) throws IOException {

        payments = payments.stream().sorted(Comparator.comparing(Payments::getTransDate)).collect(Collectors.toList());

        int paymentsCount = payments.size();

        if (paymentsCount <= maxRowsForXlsx) { // paymentExportDir
            PaymentsWarningExporter excelExporter = new PaymentsWarningExporter(payments, disputeExportDir, tctv, date);
            String result = excelExporter.export();
            return result;
        }
        return null;
    }

    public List<TblSessions> getListSession2WorkingDaysAgo(Long sessionType) {
        List<TblSessions> listSettleSession = new ArrayList<>();
        final LocalDate checkEndDate = LocalDate.now();
        LocalDate checkBeginDate = checkEndDate.minusDays(30);

        Date dbCheckDate = Date.from(checkBeginDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<WorkingDay> listWorkingDays = new ArrayList<>();

        List<TblSpecialDay> listSpecialDay = daoTblSpecialDay.findAllByBeginDateAfter(dbCheckDate);

        for (LocalDate checkDate = checkBeginDate; checkDate.compareTo(checkEndDate) <= 0; checkDate = checkDate.plusDays(1)) {
            WorkingDay workingDay = new WorkingDay();
            workingDay.setDate(checkDate);

            Date dateCompare = Date.from(checkDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (checkDate.getDayOfWeek() == DayOfWeek.SATURDAY || checkDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                boolean swapDay = listSpecialDay.stream().anyMatch(d -> d.getDayType() == SpecialDayType.SWAP
                        && dateCompare.compareTo(d.getBeginDate()) >= 0
                        && dateCompare.compareTo(d.getEndDate()) <= 0);

                if (swapDay) {
                    workingDay.setWork(true);
                } else {
                    workingDay.setWork(false);
                }
            } else {
                boolean holiday = listSpecialDay.stream().anyMatch(d -> d.getDayType() == SpecialDayType.HOLIDAY
                        && dateCompare.compareTo(d.getBeginDate()) >= 0
                        && dateCompare.compareTo(d.getEndDate()) <= 0);

                if (holiday) {
                    workingDay.setWork(false);
                } else {
                    workingDay.setWork(true);
                }
            }

            listWorkingDays.add(workingDay);
        }

        WorkingDay todayWorkday = listWorkingDays.get(listWorkingDays.size() - 1);
        if (!todayWorkday.getWork()) {
            return listSettleSession;
        }

        List<WorkingDay> listWorkingDayWithoutToday = listWorkingDays.subList(0, listWorkingDays.size() - 1);
        final LocalDate lastWorkDay = Lists.reverse(listWorkingDayWithoutToday)
                .stream().filter(d -> d.getWork()).findFirst().orElse(null).getDate();

        List<WorkingDay> listWorkOnDay = Lists.reverse(listWorkingDayWithoutToday)
                .stream().filter(d -> d.getWork()).collect(Collectors.toList());

        LocalDate twoWorkDayAgo = listWorkOnDay.get(1).getDate();

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<AchSessions> listSession = achSessionsDAO.findBySessionTypeIdAndStartTimeAfter(sort, sessionType, dbCheckDate);

        List<AchSessions> listSessionFiltered = listSession.stream()
                .filter(s -> s.getFinishTime() != null)
                .filter(s -> {
                    LocalDate sessionDate = s.getFinishTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return sessionDate.compareTo(twoWorkDayAgo) >= 0 && sessionDate.isBefore(lastWorkDay);
                }).toList();

        listSettleSession = listSessionFiltered.stream().map(s -> {
            TblSessions tblSession = new TblSessions();
            tblSession.setStartTime(s.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            tblSession.setFinishTime(s.getFinishTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            tblSession.setId(s.getId());
            return tblSession;
        }).toList();

        return listSettleSession;
    }

    public boolean checkSpecialDay() throws ParseException {
        LocalDate checkDate = new Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Date date = Date.from(checkDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        TblSpecialDay specialDay = daoTblSpecialDay.findByBetween(date);
        if(specialDay != null) {
            if (specialDay.getDayType() == SpecialDayType.HOLIDAY) return false;
            if (checkDate.getDayOfWeek() == DayOfWeek.SATURDAY || checkDate.getDayOfWeek() == DayOfWeek.SUNDAY)
                return true;
        }
        if (checkDate.getDayOfWeek() == DayOfWeek.SATURDAY || checkDate.getDayOfWeek() == DayOfWeek.SUNDAY) return false;
        return true;
    }
}