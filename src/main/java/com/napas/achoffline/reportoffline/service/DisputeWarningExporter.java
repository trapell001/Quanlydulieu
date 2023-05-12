package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.DisputeCase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DisputeWarningExporter {
    private List<DisputeCase> listDispute;
    private String fileName;
    private String filePath;
    private String s;
    private String status;
    private Date date;

    public DisputeWarningExporter(List<DisputeCase> listDispute, String disputeExportDir, String tctv, String status, Date date) {
        this.listDispute = listDispute;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        if (tctv == null) {
            tctv = "NAPAS";
            this.s = "Tất cả";
        } else {
            this.s = tctv;
        }
        this.date = date;
        this.status = status;
        if(status.equalsIgnoreCase("OPEN"))
            fileName = String.format("ACH_Dispute_%s_Cảnh báo GD tra soát đến hạn trả lời_%s.xlsx",tctv, currentDateTime);
        else
            fileName = String.format("ACH_Dispute_%s_Cảnh báo GD tra soát quá hạn trả lời_%s.xlsx",tctv, currentDateTime);
        filePath = disputeExportDir + "/" + fileName;
    }


    public String export() throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), 100, true, true);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Dispute");
            writeHeaderLine(workbook, sheet);
            writeDataLines(workbook, sheet);
            workbook.write(fos);
        }
        return fileName;
    }


    private void writeHeaderLine(SXSSFWorkbook workbook, Sheet sheet) {
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        Row row3 = sheet.createRow(3);
        Row row4 = sheet.createRow(4);
        Row row5 = sheet.createRow(5);
        Row row6 = sheet.createRow(6);
        Row row7 = sheet.createRow(7);

        int columnCount = 0;

        sheet.setColumnWidth(columnCount++, 4*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight((short)12);
//        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        if(status.equalsIgnoreCase("OPEN")) {
            createCell(row, 0, "BÁO CÁO CHI TIẾT GIAO DỊCH ĐẾN HẠN TRẢ LỜI TRA SOÁT", style);
            createCell(row1, 0, String.format("Ngày đến hạn: %s", dateFormat1.format(date)), style);
        }
        else {

            createCell(row, 0, "BÁO CÁO CHI TIẾT GIAO DỊCH QUÁ HẠN TRẢ LỜI TRA SOÁT", style);
            createCell(row1, 0, String.format("Ngày quá hạn: %s", dateFormat1.format(date)), style);
        }
        createCell(row2, 0, "Dịch vụ: Tất cả", style);
        createCell(row3, 0, String.format("NHTV: %s", s), style);
        createCell(row4, 0, "BOC: Tất cả", style);
        createCell(row5, 0, "TTC: Tất cả", style);
        createCell(row6, 0, "Loại tra soát: Tất cả", style);
        columnCount = 0;
        createCell(row7, columnCount++, "STT", style);
        createCell(row7, columnCount++, "Reference", style);
        createCell(row7, columnCount++, "Claimant", style);
        createCell(row7, columnCount++, "Respondent", style);
        createCell(row7, columnCount++, "Disp Cat", style);
        createCell(row7, columnCount++, "Status", style);
        createCell(row7, columnCount++, "Amount", style);
        createCell(row7, columnCount++, "Create Date", style);
        createCell(row7, columnCount++, "Modif Date", style);
        createCell(row7, columnCount++, "Disp Batch Reference", style);
        createCell(row7, columnCount++, "Disp Txid", style);
        createCell(row7, columnCount++, "Disp Amount", style);
        createCell(row7, columnCount++, "Disp Operday", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(SXSSFWorkbook workbook, Sheet sheet) throws FileNotFoundException, IOException {
        int rowCount = 8;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setFontHeight((short)12);
//        style.setFont(font);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        CellStyle amountStyle = workbook.createCellStyle();
        amountStyle.cloneStyleFrom(style);
        DataFormat amountFormat = workbook.createDataFormat();
        amountStyle.setDataFormat(amountFormat.getFormat("#,##0.00"));
        int index = 1;
        for (DisputeCase user : listDispute) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, user.getReference(), style);
            createCell(row, columnCount++, user.getParticipantAssigner(), style);
            createCell(row, columnCount++, user.getParticipantRespondent(), style);
            createCell(row, columnCount++, user.getCategoriesName(), style);
            createCell(row, columnCount++, user.getStatusCode(), style);
            createCell(row, columnCount++, user.getAmount(), amountStyle);
            createCell(row, columnCount++, dateFormat.format(user.getCreateDate()), style);
            createCell(row, columnCount++, dateFormat.format(user.getModifDate()), style);
            createCell(row, columnCount++, user.getRequestNum(), style);
            createCell(row, columnCount++, user.getTxid(), style);
            createCell(row, columnCount++, user.getPdAmount(), amountStyle);
            if(user.getOperday() != null) createCell(row, columnCount++, dateFormat1.format(user.getOperday()), style);
            if(user.getOperday() == null) createCell(row, columnCount++, "", style);
        }
    }
}
