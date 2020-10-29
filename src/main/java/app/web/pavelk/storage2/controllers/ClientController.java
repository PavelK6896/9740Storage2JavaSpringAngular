package app.web.pavelk.storage2.controllers;


import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.services.ClientService;
import app.web.pavelk.storage2.util.filter.ClientFilter;
import app.web.pavelk.storage2.util.filter.ClientFilterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@Slf4j
public class ClientController {

    private ClientService clientService;
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClient() {
        log.info("getClient");
        return clientService.getClient();
    }

    @PostMapping(value = "/filter", consumes = "application/json", produces = "application/json")
    public List<Client> getClient(@RequestBody(required = false) String jsonString) throws JsonProcessingException {
        log.info("getClient filter");
        if (jsonString != null) {
            ClientFilterDto clientFilterDto = objectMapper.readValue(jsonString, ClientFilterDto.class);
            ClientFilter clientFilter = new ClientFilter(clientFilterDto);
            return clientService.getClient(clientFilter.getSpec());
        }
        return clientService.getClient();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> add(@RequestBody Client client) {
        log.info("PostMapping! " + client);
        ResponseEntity<?> tResponseEntity;

        if (client != null) {
            if (client.getPhone() != null) {
                if (!clientService.existsByPhone(client.getPhone())) {
                    client.setId(null);
                    tResponseEntity = new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
                } else {
                    //такой телефон уже есть
                    tResponseEntity = ResponseEntity
                            .status(HttpStatus.NO_CONTENT).contentType(MediaType.TEXT_PLAIN)
                            .body("phone exists");
                }
            } else {
                //нет телефона
                tResponseEntity = ResponseEntity
                        .status(HttpStatus.NO_CONTENT)//204
                        .body("not phone");
            }
        } else {
            tResponseEntity = ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("not client");

        }

        return tResponseEntity;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyProduct(@RequestBody Client client) {
        ResponseEntity<Client> tResponseEntity;
        if (client.getId() != null) {
            if (clientService.existsById(client.getId())) {
                tResponseEntity = new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
                log.info("update " + client.toString());
            } else {
                tResponseEntity = new ResponseEntity<>(client, HttpStatus.NOT_FOUND);
                log.info("not product " + client.toString());
            }
        } else {
            tResponseEntity = new ResponseEntity<>(client, HttpStatus.NO_CONTENT);
            log.info("not product " + client.toString());
        }
        return tResponseEntity;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = false) Long id) {
        if (id != null) {
            log.info("Delete id " + id);
            clientService.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not id");
    }

  @GetMapping(value = "/reportXlsx")
  public HttpEntity<? extends Serializable> getReportXlsx() {
    log.info("getReportXlsx");
    ResponseEntity<byte[]> body;
    try {
      body = ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report1.xlsx")
        .header("filename", "report1.xlsx")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(clientService.getReportXlsx().toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
    }
    return body;
  }

}
