package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.util.report.FileDocxComponent;
import app.web.pavelk.storage2.util.report.FilePdfComponent;
import app.web.pavelk.storage2.util.report.FileXlsxComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final FileXlsxComponent fileXlsxComponent;
    private final FileDocxComponent fileDocxComponent;
    private final FilePdfComponent filePdfComponent;
    private final ClientService clientService;

    public ResponseEntity<byte[]> getReportXlsx() throws IOException {
        log.info("getReportXlsx");
        return getBodyBuilder()
                .body(fileXlsxComponent.getReportXlsx(clientService.isSpecificationGetClient()).toByteArray());
    }

    public ResponseEntity<byte[]> getReportDocx() throws IOException {
        log.info("getReportDocx");
        return getBodyBuilder()
                .body(fileDocxComponent.getReportDocx(clientService.isSpecificationGetClient()).toByteArray());
    }

    public ResponseEntity<byte[]> getReportPdf() throws Exception {
        log.info("getReportPdf");
        return getBodyBuilder()
                .body(filePdfComponent.getReportPdf(clientService.isSpecificationGetClient()).toByteArray());

    }

    public ResponseEntity.BodyBuilder getBodyBuilder() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Expose-Headers", "Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + URLEncoder
                                .encode("Список клиентов.pdf", StandardCharsets.UTF_8).replace('+', ' '))
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
    }


}
