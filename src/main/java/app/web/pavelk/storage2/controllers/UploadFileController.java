package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.services.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/file")
public class UploadFileController {

    private final UploadFileService uploadFileService;

    @GetMapping(value = "/reportXlsx")
    public  ResponseEntity<byte[]> getReportXlsx() throws IOException {
        return uploadFileService.getReportXlsx();
    }

    @GetMapping(value = "/reportDocx")
    public  ResponseEntity<byte[]> getReportDocx() throws IOException {
        return uploadFileService.getReportDocx();
    }

    @GetMapping(value = "/reportPdf")
    public  ResponseEntity<byte[]> getReportPdf() throws Exception {
        return uploadFileService.getReportPdf();
    }

}
