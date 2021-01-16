package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


@Component
public class FileDocxComponent {

    public ByteArrayOutputStream getReportDocx(List<Client> client) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XWPFDocument xwpfDocument = new XWPFDocument();
        XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
        xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText("Список клиентов");

        XWPFTable xwpfTable = xwpfDocument.createTable();

        CTTblWidth width = xwpfTable.getCTTbl().addNewTblPr().addNewTblW();
        width.setType(STTblWidth.DXA);
        width.setW(BigInteger.valueOf(9072));

        XWPFTableRow xwpfTableRowOne = xwpfTable.getRow(0);

        xwpfTableRowOne.getCell(0).setText("id");
        xwpfTableRowOne.addNewTableCell().setText("phone");
        xwpfTableRowOne.addNewTableCell().setText("name");
        xwpfTableRowOne.addNewTableCell().setText("title");

        for (Client row : client) {
            XWPFTableRow xwpfTableRow = xwpfTable.createRow();
            xwpfTableRow.getCell(0).setText(row.getId().toString());
            xwpfTableRow.getCell(1).setText(row.getPhone());
            xwpfTableRow.getCell(2).setText(row.getName());
            xwpfTableRow.getCell(3).setText(row.getTitle());
        }

        xwpfDocument.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }
}
