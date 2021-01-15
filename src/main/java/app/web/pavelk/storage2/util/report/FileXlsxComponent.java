package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;

@Component
public class FileXlsxComponent {

    public ByteArrayOutputStream getReportXlsx(List<Client> client) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook(); //HSSFWorkbook

        //list1
        Sheet sheetOne = workbook.createSheet("list1");
        Row rowHeader = sheetOne.createRow(0);

        CellStyle cs = workbook.createCellStyle();
        cs.setBorderTop(BorderStyle.MEDIUM);
        cs.setBorderBottom(BorderStyle.MEDIUM);
        cs.setBorderLeft(BorderStyle.MEDIUM);
        cs.setBorderRight(BorderStyle.MEDIUM);
        cs.setAlignment(CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");
        font.setBold(true);
        cs.setFont(font);

        CellStyle cs2 = workbook.createCellStyle();
        cs2.setBorderTop(BorderStyle.THIN);
        cs2.setBorderBottom(BorderStyle.THIN);
        cs2.setBorderLeft(BorderStyle.THIN);
        cs2.setBorderRight(BorderStyle.THIN);
        cs2.setAlignment(CENTER);
        cs2.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 11);
        font2.setFontName("Times New Roman");
        cs2.setFont(font2);

        rowHeader.createCell(0).setCellValue("id");
        rowHeader.createCell(1).setCellValue("phone");
        rowHeader.createCell(2).setCellValue("name");
        rowHeader.createCell(3).setCellValue("title");

        for (int i = 0; i < 4; i++) {
            rowHeader.getCell(i).setCellStyle(cs);
        }

        int rowSetTableList = 1;

        for (Client client2 : client) {
            Row row = sheetOne.createRow(rowSetTableList);
            row.createCell(0).setCellValue(client2.getId());
            row.createCell(1).setCellValue(client2.getPhone());
            row.createCell(2).setCellValue(client2.getName());
            row.createCell(3).setCellValue(client2.getTitle());
            for (int i = 0; i < 4; i++) {
                row.getCell(i).setCellStyle(cs2);
            }
            rowSetTableList++;
        }

        for (int i = 0; i < 4; i++) {
            sheetOne.autoSizeColumn(i);
        }

        workbook.write(byteArrayOutputStream);
        workbook.close();
        return byteArrayOutputStream;
    }
}
