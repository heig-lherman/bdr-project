package heig.bdr.choochoo.api.team.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class TeamRequestBody {

    private final @NotBlank String name;
}
