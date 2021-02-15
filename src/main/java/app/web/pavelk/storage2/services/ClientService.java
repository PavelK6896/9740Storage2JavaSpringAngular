package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.dto.ClientFilterDto;
import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.repositories.ClientRepository;
import app.web.pavelk.storage2.util.specifications.ClientFilterSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final ObjectMapper objectMapper;
    private ClientFilterSpecification clientFilterSpecification = null;

    public ResponseEntity<List<Client>> getClient() {
        log.info("getClient");
        clientFilterSpecification = null;
        return ResponseEntity.ok().body(clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    public ResponseEntity<List<Client>> getClient(@NonNull Specification<Client> specification) {
        return ResponseEntity.ok().body(clientRepository.findAll(specification,
                Sort.by(Sort.Direction.ASC, "id")));
    }

    public ResponseEntity<List<Client>> getClientFilter(String filterParam) {
        log.info("getClientFilter");
        try {
            clientFilterSpecification = new ClientFilterSpecification(objectMapper.readValue(filterParam, ClientFilterDto.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error filter param");
        }
        return getClient(clientFilterSpecification.getSpec());
    }

    @Transactional
    public ResponseEntity<Client> addClient(Client client) {
        log.info("addClient");
        if (client != null) {
            if (client.getPhone() != null) {
                if (!clientRepository.existsByPhone(client.getPhone())) {
                    client.setId(null);
                    return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(client));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "phone exists");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not phone");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not client");
        }
    }

    @Transactional
    public ResponseEntity<Client> updateClient(Client client) {
        log.info("updateClient");
        if (client.getId() != null) {
            if (clientRepository.existsById(client.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(client));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not client" + client.toString());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not client" + client.toString());
        }
    }

    public ResponseEntity<Void> deleteClient(Long id) {
        log.info("deleteClient");
        if (id != null) {
            clientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not id " + id);
    }


    public List<Client> isSpecificationGetClient() {
        if (clientFilterSpecification != null) {
            return getClient(clientFilterSpecification.getSpec()).getBody();
        }
        return getClient().getBody();
    }
}
