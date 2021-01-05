package app.web.pavelk.storage2.jwt;


import app.web.pavelk.storage2.dto.LoginResponseDto;
import app.web.pavelk.storage2.exceptions.JwtAuthenticationException;
import app.web.pavelk.storage2.services.SecurityUserImplements;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static java.util.Date.from;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

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

//    public String generateToken(UserDetails userDetails) {
//        List<String> rolesList = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        return createToken(userDetails.getUsername(), rolesList);
//    }


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


    public String createToken(String username, List<String> role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role.toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
