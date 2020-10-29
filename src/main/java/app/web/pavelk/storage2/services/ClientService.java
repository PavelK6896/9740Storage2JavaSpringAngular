package app.web.pavelk.storage2.services;

import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.repositories.ClientRepository;
import app.web.pavelk.storage2.util.report.ReportXlsxComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;
  private final ReportXlsxComponent reportXlsxComponent;
  Specification<Client> specification = null;
  List<Client> list = null;


  public List<Client> getClient() {
    specification = null;
    return list = clientRepository.findAll();
  }

  public List<Client> getClient(Specification<Client> spec) {
    specification = spec;
    return list = clientRepository.findAll(spec);
  }

  @Transactional
  public void deleteById(Long id) {
    if (existsById(id))
      clientRepository.deleteById(id);
  }

  public ByteArrayOutputStream getReportXlsx() throws Exception {
    return reportXlsxComponent.getReportXlsx(isListClientCash());
  }

  //кешировать список клиентов в оперативке
  public List<Client> isListClientCash() {
    if (list != null) {
      return list;
    }
    return getClient();
  }

  public boolean existsById(Long id) {
    return clientRepository.existsById(id);
  }

  public boolean existsByPhone(String phone) {
    return clientRepository.existsByPhone(phone);
  }

  public Client save(Client client) {
    return clientRepository.save(client);
  }
}
