package heig.bdr.choochoo.api.journey.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Data
@Builder
@Jacksonized
public class JourneyRequestBody {

    private final @NotNull boolean team;
    private final @NotNull Instant startDate;
    private final @NotNull Instant endDate;
    private final @NotEmpty long[] segments;
    private final @NotNull int grade;
    private final @NotNull String review;
}
