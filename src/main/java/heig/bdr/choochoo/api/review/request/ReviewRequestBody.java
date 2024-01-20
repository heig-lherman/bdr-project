package heig.bdr.choochoo.api.review.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ReviewRequestBody {
    private final String review;
    private final int grade;
    private final long line;
}
