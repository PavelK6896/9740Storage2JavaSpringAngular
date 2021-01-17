package app.web.pavelk.storage2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Setter
@Getter
@Builder
public class MessageErrorDto {
    String message;
    LocalDateTime localDateTime;
    Collection<? extends GrantedAuthority> role;
}
