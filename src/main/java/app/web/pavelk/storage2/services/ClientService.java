package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public void setUserRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Client> getClient(){
        return clientRepository.findAll();
    }

    public List<Client> getClient(Specification<Client> spec){
        return clientRepository.findAll(spec);
    }

    @Transactional
    public void deleteById(Long id){
        if (existsById(id))
        clientRepository.deleteById(id);
    }

    public boolean existsById(Long id){
        return clientRepository.existsById(id);
    }

    public boolean existsByPhone(String phone){
        return clientRepository.existsByPhone(phone);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
