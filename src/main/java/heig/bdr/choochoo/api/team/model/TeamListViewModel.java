package heig.bdr.choochoo.api.team.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface TeamListViewModel {

    @NotNull Long getId();
    @NotBlank String getName();
    @NotNull Long getUserCount();
}
