package heig.bdr.choochoo.api.team.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TeamDetailViewModel(
        @NotNull Long id,
        @NotBlank String name,
        List<User> users
) {

    public record User(
            @NotBlank String displayName,
            @NotBlank String firstName,
            @NotBlank String lastName
    ) {
    }
}
