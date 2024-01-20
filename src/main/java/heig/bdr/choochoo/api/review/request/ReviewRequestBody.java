package heig.bdr.choochoo.api.review.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ReviewRequestBody {

    private final @NotNull @Positive long line;
    private final @NotBlank @Size(max = 2000) String review;
    private final @NotNull @Min(0) @Max(10) int grade;
}
