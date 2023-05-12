package com.napas.achoffline.reportoffline.models;

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

public class DisputeExcelExporter {
    private List<DisputeCase> listDispute;
    private String fileName;
    private String filePath;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    public DisputeExcelExporter(List<DisputeCase> listDispute, String disputeExportDir) {
        this.listDispute = listDispute;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        fileName = String.format("Dispute_%s.xlsx", currentDateTime);
        filePath = disputeExportDir + "/" + fileName;
    }


    public void export(HttpServletResponse response) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), 100, true, true);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Dispute");
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


    private void writeHeaderLine(SXSSFWorkbook workbook, Sheet sheet) {
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);

        int columnCount = 0;

        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 13*256);
        sheet.setColumnWidth(columnCount++, 40*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
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
        createCell(row, columnCount++, "Reference", style);
        createCell(row, columnCount++, "Claimant", style);
        createCell(row, columnCount++, "Claimee", style);
        createCell(row, columnCount++, "Disp Cat", style);
        createCell(row, columnCount++, "Status", style);
        createCell(row, columnCount++, "Status Desc", style);
        createCell(row, columnCount++, "Create Date", style);
        createCell(row, columnCount++, "Modif Date", style);
        createCell(row, columnCount++, "Respondent", style);
        createCell(row, columnCount++, "Disp Batch Reference", style);
        createCell(row, columnCount++, "Disp Txid", style);
        createCell(row, columnCount++, "Disp Instrid", style);
        createCell(row, columnCount++, "Disp Endtoendid", style);
        createCell(row, columnCount++, "Response Time", style);
        createCell(row, columnCount++, "Response Time Num", style);
        createCell(row, columnCount++, "Response Date", style);
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

        for (DisputeCase user : listDispute) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getReference(), style);
            createCell(row, columnCount++, user.getParticipantAssigner(), style);
            createCell(row, columnCount++, user.getParticipantAssignee(), style);
            createCell(row, columnCount++, user.getCode(), style);
            createCell(row, columnCount++, user.getStatusCode(), style);
            createCell(row, columnCount++, user.getStatusDescription(), style);
            createCell(row, columnCount++, dateFormat.format(user.getCreateDate()), style);
            createCell(row, columnCount++, dateFormat.format(user.getModifDate()), style);
            createCell(row, columnCount++, user.getParticipantRespondent(), style);
            createCell(row, columnCount++, user.getRequestNum(), style);
            createCell(row, columnCount++, user.getTxid(), style);
            createCell(row, columnCount++, user.getInstrid(), style);
            createCell(row, columnCount++, user.getEndtoendid(), style);
            createCell(row, columnCount++, user.getResponseTime(), style);
            createCell(row, columnCount++, user.getResponseTimeNum(), style);
            createCell(row, columnCount++, dateFormat.format(user.getResponseDate()), style);
        }
    }
}
