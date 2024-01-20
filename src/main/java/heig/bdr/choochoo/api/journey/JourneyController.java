package heig.bdr.choochoo.api.journey;

import heig.bdr.choochoo.api.journey.model.JourneyListViewModel;
import heig.bdr.choochoo.api.journey.request.JourneyRequestBody;
import heig.bdr.choochoo.business.repository.JourneyRepository;
import heig.bdr.choochoo.security.UserSecurityGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/journeys", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyRepository journeyRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<JourneyListViewModel> getAll() {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        return journeyRepository.findAll(email);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createJourney(
            @RequestBody JourneyRequestBody body
    ) {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        journeyRepository.createJourney(
                email,
                body.getStartDate().atZone(ZoneOffset.UTC),
                body.getEndDate().atZone(ZoneOffset.UTC),
                body.getSegments(),
                body.getGrade(),
                body.getReview(),
                body.isTeam()
        );
        return ResponseEntity.accepted().build();
    }
}
