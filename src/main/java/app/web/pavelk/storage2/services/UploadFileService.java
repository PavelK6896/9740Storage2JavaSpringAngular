package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.util.report.FileXlsxComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final FileXlsxComponent fileXlsxComponent;
    private final ClientService clientService;

    public ResponseEntity<?> getReportXlsx() {
        log.info("getReportXlsx");
        ResponseEntity<byte[]> responseEntity;
        try {
            responseEntity = ResponseEntity
                    .ok()
                    .header("Access-Control-Expose-Headers", "Content-Disposition")
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + URLEncoder
                                    .encode("Список клиентов.xlsx", StandardCharsets.UTF_8).replace('+', ' '))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileXlsxComponent.getReportXlsx(clientService.isSpecificationGetClient()).toByteArray());
        } catch (Exception e) {
            log.info(e.getMessage(), " Ошибка");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
        }
        return responseEntity;
    }
}
