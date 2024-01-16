package heig.bdr.choochoo.configuration.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("choochoo.security.jwt")
public record JwtSecurityProperties(
        String secretKey,
        int expiration,
        RefreshToken refreshToken
) {

    public record RefreshToken(
            int expiration
    ) {
    }
}
