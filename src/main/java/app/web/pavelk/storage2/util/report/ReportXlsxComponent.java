package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ReportXlsxComponent {

  public ByteArrayOutputStream getReportXlsx(List<Client> client) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Workbook workbook = new XSSFWorkbook(); //HSSFWorkbook

    //list1
    Sheet sheetOne = workbook.createSheet("list1");

    Row rowHeader = sheetOne.createRow(0);
    rowHeader.createCell(0).setCellValue("id");
    rowHeader.createCell(1).setCellValue("phone");
    rowHeader.createCell(2).setCellValue("name");
    rowHeader.createCell(3).setCellValue("title");

    int rowSetTableList = 1;
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
    for (Client client2 : client) {
      Row row = sheetOne.createRow(rowSetTableList);
      row.createCell(0).setCellValue(client2.getId());
      row.createCell(1).setCellValue(client2.getPhone());
      row.getCell(1).setCellStyle(cellStyle);
      row.createCell(2).setCellValue(client2.getName());
      row.createCell(3).setCellValue(client2.getTitle());
      rowSetTableList++;
    }
    sheetOne.autoSizeColumn(1);

    workbook.write(byteArrayOutputStream);
    workbook.close();
    return byteArrayOutputStream;
  }
}
