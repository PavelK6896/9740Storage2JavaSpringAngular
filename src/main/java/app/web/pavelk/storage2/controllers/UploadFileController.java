package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.exceptions.DownloadFileException;
import app.web.pavelk.storage2.services.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/file")
public class UploadFileController {

    private final UploadFileService uploadFileService;

    @GetMapping(value = "/reportXlsx")
    public ResponseEntity<byte[]> getReportXlsx() throws DownloadFileException {
        return uploadFileService.getReportXlsx();
    }

    @GetMapping(value = "/reportDocx")
    public ResponseEntity<byte[]> getReportDocx() throws DownloadFileException {
        return uploadFileService.getReportDocx();
    }

    @GetMapping(value = "/reportPdf")
    public ResponseEntity<byte[]> getReportPdf() throws DownloadFileException {
        return uploadFileService.getReportPdf();
    }

    @GetMapping(value = "/reportTxt")
    public ResponseEntity<byte[]> getReportTxt() throws DownloadFileException {
        return uploadFileService.getReportTxt();
    }

    @GetMapping(value = "/reportXml")
    public ResponseEntity<byte[]> getReportXml() throws DownloadFileException {
        return uploadFileService.getReportXml();
    }

    @GetMapping(value = "/reportOdt")
    public ResponseEntity<byte[]> getReportOdt() throws DownloadFileException {
        return uploadFileService.getReportOdt();
    }

}
