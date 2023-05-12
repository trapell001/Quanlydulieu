/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.sup;

import com.napas.achoffline.reportoffline.models.PaymentsExport;
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

/**
 *
 * @author HuyNX
 */
public class PaymentsExcelExporter {
    private List<PaymentsExport> listUsers;
    private String fileName;
    private String filePath;
    
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    public PaymentsExcelExporter(List<PaymentsExport> listUsers, String paymentExportDir) {
        this.listUsers = listUsers;        
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        fileName = String.format("Payment_%s.xlsx", currentDateTime);
        filePath = paymentExportDir + "/" + fileName;
    }

    private void writeHeaderLine(SXSSFWorkbook workbook, Sheet sheet) {
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);
        
        int columnCount = 0;
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 13*256);
        sheet.setColumnWidth(columnCount++, 14*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 17*256);
        sheet.setColumnWidth(columnCount++, 15*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 14*256);
        sheet.setColumnWidth(columnCount++, 14*256);

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
        createCell(row, columnCount++, "Accept Date", style);
        createCell(row, columnCount++, "Trans Date", style);
        createCell(row, columnCount++, "Request Num", style);
        createCell(row, columnCount++, "Status", style);
        createCell(row, columnCount++, "Authorise", style);
        createCell(row, columnCount++, "Amount", style);
        createCell(row, columnCount++, "Debtor agent", style);
        createCell(row, columnCount++, "Creditor agent", style);
        createCell(row, columnCount++, "Msg Type", style);
        createCell(row, columnCount++, "Session ID", style);
        createCell(row, columnCount++, "Error code", style);
        createCell(row, columnCount++, "Refusal code", style);
        createCell(row, columnCount++, "TTC", style);
        createCell(row, columnCount++, "Channel ID", style);
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
        
        DateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        for (PaymentsExport user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.processDate().format(formatter), style);
            createCell(row, columnCount++, user.transDate().format(formatter), style);
            createCell(row, columnCount++, user.txid(), style);
            createCell(row, columnCount++, user.transStatus(), style);
            createCell(row, columnCount++, user.authInfo(), style);
            createCell(row, columnCount++, String.valueOf(user.settlementAmount()), amountStyle);
            createCell(row, columnCount++, user.instructingAgent(), style);
            createCell(row, columnCount++, user.instructedAgent(), style);
            createCell(row, columnCount++, user.type(), style);
            createCell(row, columnCount++, user.sessionId(), style);
            createCell(row, columnCount++, user.achResultCode(), style);
            createCell(row, columnCount++, user.partyResultCode(), style);
            createCell(row, columnCount++, user.ttc(), style);
            createCell(row, columnCount++, user.channelId(), style);
        }
    }
    
    public void export(HttpServletResponse response) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), 100, true, true);
                FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Payment");
            writeHeaderLine(workbook, sheet);
            writeDataLines(workbook, sheet);
            workbook.write(fos);
        }

        File file = new File(filePath);
        
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("X-Suggested-Filename", fileName);
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
    }
}
