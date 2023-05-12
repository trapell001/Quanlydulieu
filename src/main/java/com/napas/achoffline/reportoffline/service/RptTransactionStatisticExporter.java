package com.napas.achoffline.reportoffline.service;


import com.napas.achoffline.reportoffline.models.RptTransactionStatistic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RptTransactionStatisticExporter {
    private List<RptTransactionStatistic> listUsers;
    private String typeGroup;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    public RptTransactionStatisticExporter(List<RptTransactionStatistic> listUsers, String typeGroup) {
        workbook = new XSSFWorkbook();
        this.listUsers = listUsers;
        this.typeGroup = typeGroup;
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Rtp");
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);

        int columnCount = 0;
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 6*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 10*256);
        sheet.setColumnWidth(columnCount++, 12*256);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        columnCount = 0;
        if(typeGroup.equals("0"))  createCell(row, columnCount++, "Date", style);
        else createCell(row, columnCount++, "Date", style);
        createCell(row, columnCount++, "RTP Type", style);
        createCell(row, columnCount++, "TTC", style);
        createCell(row, columnCount++, "Initiator Agent", style);
        createCell(row, columnCount++, "Debtor Agent", style);
        createCell(row, columnCount++, "Creditor Agent", style);
        createCell(row, columnCount++, "Creditor System", style);
        createCell(row, columnCount++, "Trans Status", style);
        createCell(row, columnCount++, "Quantity", style);
        createCell(row, columnCount++, "Error Code", style);
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

    private void writeDataLines() throws FileNotFoundException, IOException {
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
        int index = 1;
        for (RptTransactionStatistic user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getDate(), style);
            createCell(row, columnCount++, user.getRtpType(), style);
            createCell(row, columnCount++, user.getTtc(), style);
            createCell(row, columnCount++, user.getTcpl(), style);
            createCell(row, columnCount++, user.getTcnl(), style);
            createCell(row, columnCount++, user.getTcth(), style);
            createCell(row, columnCount++, user.getCreditorSystem() != null ? user.getCreditorSystem() : "", style);
            createCell(row, columnCount++, user.getStatus(), style);
            createCell(row, columnCount++, user.getSoLuong(), style);
            createCell(row, columnCount++, user.getResponseCode() != null ? user.getResponseCode() : "", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        try {
            writeHeaderLine();
            writeDataLines();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
