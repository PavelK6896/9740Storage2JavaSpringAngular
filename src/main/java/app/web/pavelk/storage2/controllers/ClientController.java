package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getClient() {
        return clientService.getClient();
    }

    @PostMapping(value = "/filter", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getClientFilter(@RequestBody @NonNull String filterParam) {
        return clientService.getClientFilter(filterParam);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addClient(@RequestBody @NonNull Client client) {
        return clientService.addClient(client);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateClient(@RequestBody @NonNull Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

    @GetMapping(value = "/reportXlsx")
    public ResponseEntity<?> getReportXlsx() {
        return clientService.getReportXlsx();
    }

}
