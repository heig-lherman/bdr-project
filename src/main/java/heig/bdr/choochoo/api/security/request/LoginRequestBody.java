package heig.bdr.choochoo.api.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class LoginRequestBody {

    private final @NotBlank String email;
    @ToString.Exclude
    private final @NotBlank String password;
}
