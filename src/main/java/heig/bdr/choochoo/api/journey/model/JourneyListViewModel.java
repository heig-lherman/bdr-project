package heig.bdr.choochoo.api.journey.model;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public interface JourneyListViewModel {

    @NotNull Long getId();
    @NotNull Instant getStartDate();
    @NotNull Instant getEndDate();
    @NotNull Integer getGrade();
    @NotNull String getReview();
    @NotNull Long getSegmentCount();
}
