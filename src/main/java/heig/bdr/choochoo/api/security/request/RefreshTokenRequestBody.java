package heig.bdr.choochoo.api.security.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class RefreshTokenRequestBody {

    private final String refreshToken;
}
