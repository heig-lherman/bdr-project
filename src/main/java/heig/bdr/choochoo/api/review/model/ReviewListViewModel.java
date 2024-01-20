package heig.bdr.choochoo.api.review.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ReviewListViewModel {

    @NotNull int getLine();

    Long getAssessmentId();
    Integer getGrade();
    String getComment();

    @NotBlank String getLineName();
    @NotBlank String getStationStart();
    @NotBlank String getStationEnd();
}
