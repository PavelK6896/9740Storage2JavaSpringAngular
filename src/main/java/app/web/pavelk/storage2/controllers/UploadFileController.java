package app.web.pavelk.storage2.controllers;


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
    public ResponseEntity<?> getReportXlsx() {
        return uploadFileService.getReportXlsx();
    }
}
