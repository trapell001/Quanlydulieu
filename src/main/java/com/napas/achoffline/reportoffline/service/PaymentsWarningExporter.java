package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.DisputeCase;
import com.napas.achoffline.reportoffline.entity.Payments;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class PaymentsWarningExporter {
    private List<Payments> listPayment;
    private String fileName;
    private String filePath;
    private String s;
    private Date date;

    public PaymentsWarningExporter(List<Payments> payments, String disputeExportDir, String tctv, Date date) {
        this.listPayment = payments;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        if (tctv == null) {
            tctv = "NAPAS";
            this.s = "Tất cả";
        } else {
            this.s = tctv;
        }
        this.date = date;
        fileName = String.format("ACH_NRT_%s_Cảnh báo GD ACSP-NAUT_%s.xlsx",tctv, currentDateTime);
        filePath = disputeExportDir + "/" + fileName;
    }


    public String export() throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), 100, true, true);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Payments");
            writeHeaderLine(workbook, sheet);
            writeDataLines(workbook, sheet);
            workbook.write(fos);
        }
        return fileName;
    }

    private void writeHeaderLine(SXSSFWorkbook workbook, Sheet sheet) {
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);

        int columnCount = 0;

        sheet.setColumnWidth(columnCount++, 4*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
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

        columnCount = 0;
        createCell(row, columnCount++, "STT", style);
        createCell(row, columnCount++, "Trans Date", style);
        createCell(row, columnCount++, "Request Num", style);
        createCell(row, columnCount++, "Status", style);
        createCell(row, columnCount++, "Authorise", style);
        createCell(row, columnCount++, "Amount", style);
        createCell(row, columnCount++, "Debtor Agent", style);
        createCell(row, columnCount++, "Creditor Agent", style);
        createCell(row, columnCount++, "Msg Type", style);
        createCell(row, columnCount++, "Session ID", style);
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
        int rowCount = 1;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        for (Payments user : listPayment) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, dateFormat.format(Date.from(user.getTransDate().toInstant())), style);
            createCell(row, columnCount++, user.getMsgID(), style);
            createCell(row, columnCount++, user.getTransStatus(), style);
            createCell(row, columnCount++, user.getAuthInfo(), style);
            Long amount = user.getSettlementAmount().longValue();
            createCell(row, columnCount++, amount, amountStyle);
            createCell(row, columnCount++, user.getDebtorAgent(), style);
            createCell(row, columnCount++, user.getCreditorAgent(), style);
            createCell(row, columnCount++, user.getType(), style);
            createCell(row, columnCount++, user.getSessionId(), style);
        }
    }
}
