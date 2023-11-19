//package com.project.system_integration.services;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.cglib.core.internal.Function;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class JwtServiceTest {
//    @InjectMocks
//    private JwtService jwtService;
//
//    @Mock
//    private JwtService mockJwtService;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void extractLogin() {
//        // Mock a JWT token
//        String token = generateSampleToken("user123", "ROLE_USER");
//
//        // Call the method under test
//        String login = jwtService.extractLogin(token);
//
//        // Assert the result
//        assertThat(login).isEqualTo("user123");
//    }
//
//    @Test
//    void extractRole() {
//        // Mock a JWT token
//        String token = generateSampleToken("user123", "ROLE_USER");
//
//        // Call the method under test
//        String role = jwtService.extractRole(token);
//
//        // Assert the result
//        assertThat(role).isEqualTo("ROLE_USER");
//    }
//
//    @Test
//    void extractClaims() {
//        // Mock a JWT token
//        String token = generateSampleToken("user123", "ROLE_USER");
//
//        // Define a custom claims resolver to extract a custom claim
//        Function<Claims, String> customClaimResolver = claims -> claims.get("customClaim", String.class);
//
//        // Call the method under test
//        String customClaim = jwtService.extractClaims(token, customClaimResolver);
//
//        // Assert the result
//        assertThat(customClaim).isNull(); // Custom claim does not exist in this token
//    }
//
//    @Test
//    void generateToken() {
//        // Mock the current time to ensure consistent token generation
//        long currentTimeMillis = System.currentTimeMillis();
////        when(mockJwtService.generateToken("user123", "USER")).thenCallRealMethod();
//
//        // Call the method under test
//        String token = jwtService.generateToken("user123", "USER");
//
//        // Parse and verify the generated token
//        Claims claims = Jwts.parserBuilder().setSigningKey(jwtService.getSignInKey()).build().parseClaimsJws(token).getBody();
//
//        // Assert the claims in the token
//        assertThat(claims.getSubject()).isEqualTo("user123");
//        assertThat(claims.getIssuer()).isEqualTo("USER");
//        assertThat(claims.getIssuedAt().getTime()).isBetween(currentTimeMillis - 1000, currentTimeMillis + 1000);
//        assertThat(claims.getExpiration().getTime()).isBetween(currentTimeMillis + 1000*60*24*24 - 1000, currentTimeMillis + 1000*60*24*24 + 1000);
//    }
//
//    @Test
//    void isTokenValid() {
//        // Mock a JWT token
//        String validToken = generateSampleToken("user123", "ROLE_USER");
//
//        // Call the method under test with a valid token
//        boolean valid = jwtService.isTokenValid(validToken, "user123");
//
//        // Assert that the token is valid
//        assertThat(valid).isTrue();
//
//        // Mock a token with a different login
//        String differentLoginToken = generateSampleToken("anotherUser", "ROLE_USER");
//
//        // Call the method under test with a token having a different login
//        boolean differentLogin = jwtService.isTokenValid(differentLoginToken, "user123");
//
//        // Assert that the token with a different login is invalid
////        assertThat(differentLogin).isFalse();
//    }
//
//    private String generateSampleToken(String login, String role) {
//        return generateSampleToken(login, role, 0);
//    }
//
//    private String generateSampleToken(String login, String role, int secondsOffset) {
//        long currentTimeMillis = System.currentTimeMillis() + secondsOffset * 1000;
//        return Jwts.builder()
//                .setSubject(login)
//                .setIssuer(role)
//                .setIssuedAt(new Date(currentTimeMillis))
//                .setExpiration(new Date(currentTimeMillis + 1000*60*24*24)) // 24 days
//                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//}