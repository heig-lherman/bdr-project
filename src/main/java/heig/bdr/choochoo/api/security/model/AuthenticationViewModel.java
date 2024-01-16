package heig.bdr.choochoo.api.security.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AuthenticationViewModel {

    private final String accessToken;
    private final String refreshToken;
}
