package heig.bdr.choochoo.api.review;

import heig.bdr.choochoo.api.review.model.ReviewListViewModel;
import heig.bdr.choochoo.api.review.request.ReviewRequestBody;
import heig.bdr.choochoo.business.repository.ReviewRepository;
import heig.bdr.choochoo.security.UserSecurityGetter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<ReviewListViewModel> getVisitedLines() {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        return reviewRepository.findVisitedLines(email);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createReview(@Valid @RequestBody ReviewRequestBody body) {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        reviewRepository.create(body.getLine(), body.getGrade(), body.getReview(), email);
        return ResponseEntity.accepted().build();
    }
}
