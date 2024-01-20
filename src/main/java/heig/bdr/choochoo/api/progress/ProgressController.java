package heig.bdr.choochoo.api.progress;

import heig.bdr.choochoo.api.progress.mapping.CantonProgressMapper;
import heig.bdr.choochoo.api.progress.model.CantonProgressViewModel;
import heig.bdr.choochoo.api.progress.model.UserProgressViewModel;
import heig.bdr.choochoo.business.repository.CantonProgressRepository;
import heig.bdr.choochoo.business.repository.UserProgressRepository;
import heig.bdr.choochoo.security.UserSecurityGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/progress", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProgressController {

    private final CantonProgressRepository cantonProgressRepository;
    private final UserProgressRepository userProgressRepository;
    private final CantonProgressMapper cantonProgressMapper;

    @GetMapping("/cantons")
    @Transactional(readOnly = true)
    public List<CantonProgressViewModel> getCantonProgress() {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        return cantonProgressRepository.getUserCantonProgress(email).stream()
                .map(cantonProgressMapper::toViewModel)
                .toList();
    }

    @GetMapping("/user")
    @Transactional(readOnly = true)
    public UserProgressViewModel getUserProgress() {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        return UserProgressViewModel.from(userProgressRepository.getUserProgress(email));
    }
}
