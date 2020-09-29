package app.web.pavelk.storage2.util.filter;


import app.web.pavelk.storage2.entities.Client;
import app.web.pavelk.storage2.repositories.specifications.ClientSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public class ClientFilter {
    private Specification<Client> spec;
    private StringBuilder filterDefinition;


    public ClientFilter(ClientFilterDto clientFilterDto) {
        this.spec = Specification.where(null);
        if (clientFilterDto.getName() != null) {
            spec = spec.and(ClientSpecifications.nameLike(clientFilterDto.getName()));
        }
        if (clientFilterDto.getPhone() != null) {
            spec = spec.and(ClientSpecifications.phoneLike(clientFilterDto.getPhone()));
        }
        if (clientFilterDto.getTitle() != null) {
            spec = spec.and(ClientSpecifications.titleLike(clientFilterDto.getTitle()));
        }
    }


}
