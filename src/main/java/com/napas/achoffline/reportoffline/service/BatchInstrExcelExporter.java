package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.models.HisBatchInstrDTO;
import com.napas.achoffline.reportoffline.models.HisBatchPaymentsDTO;
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

public class BatchInstrExcelExporter {
    private List<HisBatchInstrDTO> listUsers;
    private String fileName;
    private String filePath;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    public BatchInstrExcelExporter(List<HisBatchInstrDTO> listUsers, String paymentExportDir) {
        this.listUsers = listUsers;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        fileName = String.format("BatchInstr_%s.xlsx", currentDateTime);
        filePath = paymentExportDir + "/" + fileName;
    }

    private void writeHeaderLine(SXSSFWorkbook workbook, Sheet sheet) {
        sheet.createFreezePane(0, 1);

        Row row = sheet.createRow(0);

        int columnCount = 0;
        sheet.setColumnWidth(columnCount++, 4*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 22*256);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        columnCount = 0;
        createCell(row, columnCount++, "STT", style);
        createCell(row, columnCount++, "Txid", style);
        createCell(row, columnCount++, "Process Date", style);
        createCell(row, columnCount++, "Value Date", style);
        createCell(row, columnCount++, "Debtor Agent", style);
        createCell(row, columnCount++, "Creditor Agent", style);
        createCell(row, columnCount++, "Trans Status", style);
        createCell(row, columnCount++, "View Status", style);
        createCell(row, columnCount++, "Transaction Amount", style);
        createCell(row, columnCount++, "TTC", style);
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
        int index = 1;
        for (HisBatchInstrDTO user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, user.getMxTxid(), style);
            createCell(row, columnCount++, sfd.format(user.getProcessDate()), style);
            createCell(row, columnCount++, sfd.format(user.getValueDate()), style);
            createCell(row, columnCount++, user.getMxDebtorAgent(), style);
            createCell(row, columnCount++, user.getMxCreditorAgent(), style);
            createCell(row, columnCount++, user.getStatus(), style);
            createCell(row, columnCount++, user.getViewStatus(), style);
            createCell(row, columnCount++, user.getMxSettlementAmount().longValue(), amountStyle);
            createCell(row, columnCount++, user.getMxTtc(), style);
            createCell(row, columnCount++, user.getResultCode() != null ? user.getResultCode() : "", style);
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
