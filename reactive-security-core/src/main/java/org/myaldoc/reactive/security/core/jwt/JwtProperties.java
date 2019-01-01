package org.myaldoc.reactive.security.core.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Component
@ConfigurationProperties("jwt.properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
    private String secret;
    private String expiration;
}
