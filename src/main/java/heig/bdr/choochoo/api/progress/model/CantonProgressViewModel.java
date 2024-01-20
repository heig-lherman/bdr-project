package heig.bdr.choochoo.api.progress.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CantonProgressViewModel {

    private final @NotNull String cantonCode;
    private final @NotNull String cantonName;
    private final @NotNull long travelledCount;
    private final @NotNull long totalCount;
}
