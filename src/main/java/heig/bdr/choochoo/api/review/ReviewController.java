package heig.bdr.choochoo.api.review;

import heig.bdr.choochoo.api.review.mapping.ReviewDetailMapper;
import heig.bdr.choochoo.api.review.mapping.ReviewMapper;
import heig.bdr.choochoo.api.review.model.ReviewDetailViewModel;
import heig.bdr.choochoo.api.review.model.ReviewListViewModel;
import heig.bdr.choochoo.api.review.request.ReviewRequestBody;
import heig.bdr.choochoo.api.team.model.TeamDetailViewModel;
import heig.bdr.choochoo.api.team.request.TeamRequestBody;
import heig.bdr.choochoo.business.repository.ReviewRepository;
import heig.bdr.choochoo.security.UserSecurityGetter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/review", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewDetailMapper reviewDetailMapper;
    private final ReviewMapper reviewMapper;

    @GetMapping
    @Transactional(readOnly = true)
    public List<ReviewListViewModel> getAll() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return reviewRepository.findAll(email);
    }

    @GetMapping("/{reviewId}")
    @Transactional(readOnly = true)
    public ReviewDetailViewModel getOne(@PathVariable Long reviewId) {
        return reviewDetailMapper.toViewModel(reviewRepository.findDetailById(reviewId));
    }

    @PostMapping
    @Transactional
    public ReviewDetailViewModel createTeam(@Valid @RequestBody ReviewRequestBody body) {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        var result = reviewRepository.create(body.getLine(), body.getGrade(), body.getReview(), email);
        return reviewDetailMapper.toViewModel(result);
    }
}
