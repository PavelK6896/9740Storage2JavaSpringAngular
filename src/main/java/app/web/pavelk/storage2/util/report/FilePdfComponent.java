package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;


@Component
public class FilePdfComponent {

    public ByteArrayOutputStream getReportPdf(List<Client> client) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        PdfPTable pdfPTable = new PdfPTable(4);
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setPhrase(new Phrase("id"));
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("phone"));
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("name"));
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("title"));
        pdfPTable.addCell(pdfPCell);
        for (Client row : client) {
            pdfPTable.addCell(row.getId().toString());
            pdfPTable.addCell(row.getPhone());
            pdfPTable.addCell(row.getName());
            pdfPTable.addCell(row.getTitle());
        }
        document.add(pdfPTable);
        document.close();
        return byteArrayOutputStream;
    }
}
