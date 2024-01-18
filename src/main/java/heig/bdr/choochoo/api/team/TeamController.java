package heig.bdr.choochoo.api.team;

import heig.bdr.choochoo.api.team.mapping.TeamDetailMapper;
import heig.bdr.choochoo.api.team.model.TeamDetailViewModel;
import heig.bdr.choochoo.api.team.model.TeamListViewModel;
import heig.bdr.choochoo.api.team.request.TeamRequestBody;
import heig.bdr.choochoo.business.repository.TeamRepository;
import heig.bdr.choochoo.business.repository.UserRepository;
import heig.bdr.choochoo.security.UserSecurityGetter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/teams", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TeamController {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamDetailMapper teamDetailMapper;

    @GetMapping
    @Transactional(readOnly = true)
    public List<TeamListViewModel> getAll() {
        return teamRepository.findAll();
    }

    @GetMapping("/{teamId}")
    @Transactional(readOnly = true)
    public TeamDetailViewModel getOne(@PathVariable Long teamId) {
        return teamDetailMapper.toViewModel(teamRepository.findDetailById(teamId));
    }

    @PostMapping("/{teamId}/join")
    @Transactional
    public ResponseEntity<Void> joinTeam(@PathVariable Long teamId) {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        userRepository.joinTeam(email, teamId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/leave")
    @Transactional
    public ResponseEntity<Void> leaveTeam() {
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        userRepository.leaveTeam(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    @Transactional
    public TeamDetailViewModel createTeam(@Valid @RequestBody TeamRequestBody name) {
        var team = teamRepository.create(name.getName());
        var email = UserSecurityGetter.getAuthenticatedUser().getEmail();
        userRepository.joinTeam(email, team.getId());
        return teamDetailMapper.toViewModel(team);
    }
}
