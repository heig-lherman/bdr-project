package heig.bdr.choochoo.api.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class RegisterRequestBody {

    private final @NotBlank @Email String username;
    private final @NotBlank String displayName;
    private final @NotBlank String firstName;
    private final @NotBlank String lastName;
    @ToString.Exclude
    private final @NotBlank String password;
}
