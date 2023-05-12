/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.support;

import com.napas.achoffline.reportoffline.models.PaymentsExportAcspNaut;
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
public class PaymentsExcelExporterAcspNaut {
    private List<PaymentsExportAcspNaut> listUsers;
    private String fileName;
    private String filePath;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    public PaymentsExcelExporterAcspNaut(List<PaymentsExportAcspNaut> listUsers, String paymentExportDir) {
        this.listUsers = listUsers;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        fileName = String.format("PaymentACSPNAUT_%s.xlsx", currentDateTime);
        filePath = paymentExportDir + "/" + fileName;
        System.out.println("xuat ra file roi");
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
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 17*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 17*256);
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
        createCell(row, columnCount++, "Return Number", style);
        createCell(row, columnCount++, "Return Status", style);
        createCell(row, columnCount++, "Return Amount", style);
        createCell(row, columnCount++, "Return TransDate", style);
        createCell(row, columnCount++, "Return SessionID", style);
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

        for (PaymentsExportAcspNaut user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.beginDate(), style);
            createCell(row, columnCount++, user.endDate(), style);
            createCell(row, columnCount++, user.msgId(), style);
            createCell(row, columnCount++, user.transStatus(), style);
            createCell(row, columnCount++, user.authInfo(), style);
            createCell(row, columnCount++, user.amount(), style);
            createCell(row, columnCount++, user.debtorAgent(), style);
            createCell(row, columnCount++, user.creditorAgent(), style);
            createCell(row, columnCount++, user.mxType(), style);
            createCell(row, columnCount++, user.sessionId(), style);
            createCell(row, columnCount++, user.txid004(), style);
            createCell(row, columnCount++, user.transStatus004(), style);
            createCell(row, columnCount++, user.amount004(), style);
            createCell(row, columnCount++, user.endDate004(), style);
            createCell(row, columnCount++, user.sessionId004(), style);

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
