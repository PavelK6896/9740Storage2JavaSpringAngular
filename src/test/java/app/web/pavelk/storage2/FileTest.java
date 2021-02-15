package app.web.pavelk.storage2;

import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.util.report.*;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileTest {

    static List<Client> clients;

    @BeforeAll
    public static void generate() {
        clients = Arrays.asList(
                Client.builder().id(1l).name("name 1").phone("780").title("title 1").build(),
                Client.builder().id(2l).name("name 2").phone("343").title("title 2").build(),
                Client.builder().id(3l).name("name 3").phone("546").title("title 3").build()
        );
    }

    @Test
    void docx() throws IOException {
        FileDocxComponent fileDocxComponent = new FileDocxComponent();
        ByteArrayOutputStream reportDocx = fileDocxComponent.getReportDocx(clients);
        Assertions.assertNotNull(reportDocx);
    }

    @Test
    void odt() throws Exception {
        FileOdtComponent fileOdtComponent = new FileOdtComponent();
        ByteArrayOutputStream reportDocx = fileOdtComponent.getReportOdt(clients);
        Assertions.assertNotNull(reportDocx);
    }

    @Test
    void pdf() throws DocumentException {
        FilePdfComponent filePdfComponent = new FilePdfComponent();
        ByteArrayOutputStream reportDocx = filePdfComponent.getReportPdf(clients);
        Assertions.assertNotNull(reportDocx);
    }

    @Test
    void txt() throws IOException {
        FileTxtComponent fileTxtComponent = new FileTxtComponent();
        ByteArrayOutputStream reportDocx = fileTxtComponent.getReportTxt(clients);
        Assertions.assertNotNull(reportDocx);
    }

    @Test
    void xlsx() throws IOException {
        FileXlsxComponent fileXlsxComponent = new FileXlsxComponent();
        ByteArrayOutputStream reportDocx = fileXlsxComponent.getReportXlsx(clients);
        Assertions.assertNotNull(reportDocx);
    }

    @Test
    void xml() throws ParserConfigurationException {
        FileXmlComponent fileXmlComponent = new FileXmlComponent();
        ByteArrayOutputStream reportDocx = fileXmlComponent.getReportXml(clients);
        Assertions.assertNotNull(reportDocx);
    }
}
