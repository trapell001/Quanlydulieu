package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.models.HisRtpTransactionDTO;
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

public class RptTransactionExporter {
    private List<HisRtpTransactionDTO> listRtp;
    private String fileName;
    private String filePath;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    public RptTransactionExporter(List<HisRtpTransactionDTO> listRtp, String rtpExportDir) {
        this.listRtp = listRtp;
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        fileName = String.format("Rtp_%s.xlsx", currentDateTime);
        filePath = rtpExportDir + "/" + fileName;
    }


    public void export(HttpServletResponse response) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), 100, true, true);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Rtp");
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
        sheet.setColumnWidth(columnCount++, 4*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 43*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 16*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 25*256);
        sheet.setColumnWidth(columnCount++, 10*256);
        sheet.setColumnWidth(columnCount++, 22*256);
        sheet.setColumnWidth(columnCount++, 12*256);
        sheet.setColumnWidth(columnCount++, 12*256);
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
        createCell(row, columnCount++, "MsgID RTP", style);
        createCell(row, columnCount++, "MsgID Payment", style);
        createCell(row, columnCount++, "Initiator Agent", style);
        createCell(row, columnCount++, "Debtor Agent", style);
        createCell(row, columnCount++, "Creditor Agent", style);
        createCell(row, columnCount++, "Creditor System", style);
        createCell(row, columnCount++, "Trans Date", style);
        createCell(row, columnCount++, "Requested Execution Date", style);
        createCell(row, columnCount++, "RTP Type", style);
        createCell(row, columnCount++, "Amount", style);
        createCell(row, columnCount++, "Status", style);
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
        int dem = 1;
        for (HisRtpTransactionDTO user : listRtp) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, dem++, style);
            createCell(row, columnCount++, user.getPaymentInfoId(), style);
            createCell(row, columnCount++, user.getMxTxid(), style);
            createCell(row, columnCount++, user.getInitiatingParty(), style);
            createCell(row, columnCount++, user.getForwardingAgent(), style);
            createCell(row, columnCount++, user.getInitiatingParty(), style);
            createCell(row, columnCount++, user.getCreditorSystem(), style);
            createCell(row, columnCount++, dateFormat.format(user.getMxTransDate()), style);
            createCell(row, columnCount++, dateFormat1.format(user.getRequestedExecutionDate()), style);
            createCell(row, columnCount++, user.getRtpServiceType(), style);
            createCell(row, columnCount++, user.getInstructedAmount(), amountStyle);
            createCell(row, columnCount++, user.getTransStatus(), style);
            createCell(row, columnCount++, user.getRespondCode() != null ? user.getRespondCode() : "", style);
        }
    }
}
