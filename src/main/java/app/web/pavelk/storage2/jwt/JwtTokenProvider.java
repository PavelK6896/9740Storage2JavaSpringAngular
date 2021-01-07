package app.web.pavelk.storage2.jwt;


import app.web.pavelk.storage2.dto.LoginResponseDto;
import app.web.pavelk.storage2.services.SecurityUserImplements;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import static java.util.Date.from;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public LoginResponseDto generateToken(Authentication authentication) {
        SecurityUserImplements principalUser = (SecurityUserImplements) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(principalUser.getUsername());
        claims.put("role", principalUser.getAuthorities().toString());

        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(validityInMilliseconds);

        return LoginResponseDto.builder()
                .username(principalUser.getUsername())
                .expiresAt(localDateTime)
                .authenticationToken(Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(from(Instant.now()))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .setExpiration(java.sql.Timestamp.valueOf(localDateTime))
                        .compact()).build();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
