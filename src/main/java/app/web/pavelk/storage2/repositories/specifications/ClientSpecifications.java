package app.web.pavelk.storage2.repositories.specifications;


import app.web.pavelk.storage2.entities.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecifications {

    public static Specification<Client> titleLike(String title) {
        return (Specification<Client>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), String.format("%%%s%%", title.toLowerCase()));
    }

    public static Specification<Client> nameLike(String name) {
        return (Specification<Client>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), String.format("%%%s%%", name.toLowerCase()));
    }

    public static Specification<Client> phoneLike(String phone) {
        return (Specification<Client>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("phone"), String.format("%%%s%%", phone));
    }
}
