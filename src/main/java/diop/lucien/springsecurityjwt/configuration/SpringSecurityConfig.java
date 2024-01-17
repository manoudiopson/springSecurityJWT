package diop.lucien.springsecurityjwt.configuration;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user =
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER").build();

        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String jwtKey = "MIIBvTBXBgkqhkiG9w0BBQ0wSjApBgkqhkiG9w0BBQwwHAQIrv7iaa3Kt8ICAggA\n" +
            "MAwGCCqGSIb3DQIJBQAwHQYJYIZIAWUDBAECBBB8bNiNRKr2tahkvBCl82DVBIIB\n" +
            "YAkLi6R933hezxOkG0iA6cA2vkkPdShmRm70M4WBOMEAtaoq/rT8i2JG2Kln+n3V\n" +
            "+4y1dXnsP2QnVdqaFUye4XXZ6MEaYYMLcCRC/vBNM2hIikNLavP8BpIOq6v19Jsl\n" +
            "Y49kr03KPWH7QO048fwyl85Nx5KJhVwsqfBsBkRbyswXCwC73ke0KzbMbBGYffDz\n" +
            "yl3Xwlxnn2tSNOImz62W+yG5sJhIwYU5bGJUcJSv5InW0wqau+Ensh2e4UaAKn7o\n" +
            "Sojuq4dYYcB4h20+MOU/a7wS2ZgJfvmgYNMyQFF7YgD550Wg+ymumhRlLlhi6u4i\n" +
            "KiAZkZUvDzwHmxOqjcn1aDQGO7hCImYkDbZgSob9E4de7/W9HQAZq8R3gkWfvXLr\n" +
            "TSPFbSttqbzjalnob/YkY0GrjaK4tjjCDh5YNfPE6TxJ645a/wpwz4I4aagJHHU4\n" +
            "oBw3FT7Xm5dErNPU4+G/qe8=";
    @Bean
    public JwtDecoder jwtDecoder(){
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0,
                this.jwtKey.getBytes().length, "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }
}
