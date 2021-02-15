package app.web.pavelk.storage2.security;


import app.web.pavelk.storage2.dto.LoginResponseDto;
import app.web.pavelk.storage2.dto.SecurityUserImplements;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long exp;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public LoginResponseDto generateToken(Authentication authentication) {
        SecurityUserImplements principalUser = (SecurityUserImplements) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(principalUser.getUsername());
        claims.put("role", principalUser.getAuthorities().toString());

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeNowPlus = localDateTimeNow.plusSeconds(exp);

        return LoginResponseDto.builder()
                .username(principalUser.getUsername())
                .expiresAt(localDateTimeNowPlus)
                .authenticationToken(Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(java.sql.Timestamp.valueOf(localDateTimeNow))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .setExpiration(java.sql.Timestamp.valueOf(localDateTimeNowPlus))
                        .compact()).build();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
