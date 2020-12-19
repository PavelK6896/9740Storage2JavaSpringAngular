package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.repositories.ClientRepository;
import app.web.pavelk.storage2.util.filter.ClientFilterDto;
import app.web.pavelk.storage2.util.filter.ClientFilterSpecification;
import app.web.pavelk.storage2.util.report.ReportXlsxComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final ReportXlsxComponent reportXlsxComponent;
    private final ObjectMapper objectMapper;
    private ClientFilterSpecification clientFilterSpecification = null;

    public ResponseEntity<List<Client>> getClient() {
        log.info("getClient");
        clientFilterSpecification = null;
        return ResponseEntity.ok().body(clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    public ResponseEntity<List<Client>> getClient(@NonNull Specification<Client> specification) {
        return ResponseEntity.ok().body(clientRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "id")));
    }

    public ResponseEntity<?> getClientFilter(String filterParam) {
        log.info("getClientFilter");
        try {
            clientFilterSpecification = new ClientFilterSpecification(objectMapper.readValue(filterParam, ClientFilterDto.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error filter param");
        }
        return getClient(clientFilterSpecification.getSpec());
    }

    @Transactional
    public ResponseEntity<?> addClient(Client client) {
        log.info("addClient");
        ResponseEntity<?> responseEntity;
        if (client != null) {
            if (client.getPhone() != null) {
                if (!clientRepository.existsByPhone(client.getPhone())) {
                    client.setId(null);
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(client));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("phone exists");
                }
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not phone");
            }
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not client");
        }
        return responseEntity;
    }

    @Transactional
    public ResponseEntity<?> updateClient(Client client) {
        log.info("updateClient");
        ResponseEntity<?> responseEntity;
        if (client.getId() != null) {
            if (clientRepository.existsById(client.getId())) {
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(client));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not client" + client.toString());
            }
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not client" + client.toString());
        }
        return responseEntity;
    }

    public ResponseEntity<?> deleteClient(Long id) {
        log.info("deleteClient");
        if (id != null) {
            clientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not id " + id);
    }

    public ResponseEntity<?> getReportXlsx() {
        log.info("getReportXlsx");
        ResponseEntity<byte[]> responseEntity;
        try {
            responseEntity = ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report1.xlsx")
                    .header("filename", "report1.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(reportXlsxComponent.getReportXlsx(isSpecificationGetClient()).toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
        }
        return responseEntity;
    }

    public List<Client> isSpecificationGetClient() {
        if (clientFilterSpecification != null) {
            return getClient(clientFilterSpecification.getSpec()).getBody();
        }
        return getClient().getBody();
    }
}
