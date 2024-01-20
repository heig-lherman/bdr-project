package heig.bdr.choochoo.api.review.model;

public interface ReviewDetailViewModel {
    Long getId();

    String getReview();

    int getGrade();

    Long getLine();

    String getLineName();

    String getLineStartStation();

    String getLineEndStation();
}
