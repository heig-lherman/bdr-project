package heig.bdr.choochoo.api.review.mapping;

import heig.bdr.choochoo.api.review.model.ReviewDetailViewModel;
import heig.bdr.choochoo.business.model.LineAssessment;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewDetailMapper {
    ReviewDetailViewModel toViewModel(LineAssessment lineAssessment);
}
