package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.exceptions.DownloadFileException;
import app.web.pavelk.storage2.util.report.*;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final ClientService clientService;
    private final FileXlsxComponent fileXlsxComponent;
    private final FileDocxComponent fileDocxComponent;
    private final FilePdfComponent filePdfComponent;
    private final FileTxtComponent fileTxtComponent;
    private final FileXmlComponent fileXmlComponent;
    private final FileOdtComponent fileOdtComponent;

    public ResponseEntity.BodyBuilder getBodyBuilder(String format) {
        return ResponseEntity
                .ok()
                .header("Access-Control-Expose-Headers", "Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + URLEncoder
                                .encode("Список клиентов." + format, StandardCharsets.UTF_8).replace('+', ' '))
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
    }

    public ResponseEntity<byte[]> getReportXlsx() throws DownloadFileException {
        log.info("getReportXlsx");
        try {
            return getBodyBuilder("xlsx")
                    .body(fileXlsxComponent.getReportXlsx(clientService.isSpecificationGetClient()).toByteArray());
        } catch (IOException e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> getReportDocx() throws DownloadFileException {
        log.info("getReportDocx");
        try {
            return getBodyBuilder("docx")
                    .body(fileDocxComponent.getReportDocx(clientService.isSpecificationGetClient()).toByteArray());
        } catch (IOException e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> getReportPdf() throws DownloadFileException {
        log.info("getReportPdf");
        try {
            return getBodyBuilder("pdf")
                    .body(filePdfComponent.getReportPdf(clientService.isSpecificationGetClient()).toByteArray());
        } catch (DocumentException e) {
            throw new DownloadFileException(e.getMessage());
        }

    }

    public ResponseEntity<byte[]> getReportTxt() throws DownloadFileException {
        log.info("getReportTxt");
        try {
            return getBodyBuilder("txt")
                    .body(fileTxtComponent.getReportTxt(clientService.isSpecificationGetClient()).toByteArray());
        } catch (IOException e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> getReportXml() throws DownloadFileException {
        log.info("getReportXml");
        try {
            return getBodyBuilder("xml")
                    .body(fileXmlComponent.getReportXml(clientService.isSpecificationGetClient()).toByteArray());
        } catch (ParserConfigurationException e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> getReportOdt() throws DownloadFileException {
        log.info("getReportOdt");
        try {
            return getBodyBuilder("odt")
                    .body(fileOdtComponent.getReportOdt(clientService.isSpecificationGetClient()).toByteArray());
        } catch (Exception e) {
            throw new DownloadFileException(e.getMessage());
        }
    }
}
