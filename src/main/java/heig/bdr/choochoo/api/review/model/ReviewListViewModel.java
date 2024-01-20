package heig.bdr.choochoo.api.review.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ReviewListViewModel {
    @NotNull Long getLine();

    @NotBlank String getLineName();
    @NotBlank String getStationStart();
    @NotBlank String getStationEnd();
    @NotBlank Long getId();

    @NotNull Long getGrade();
}
