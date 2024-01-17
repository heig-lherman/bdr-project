package heig.bdr.choochoo.api.team;

import heig.bdr.choochoo.api.team.mapping.TeamDetailMapper;
import heig.bdr.choochoo.api.team.model.TeamDetailViewModel;
import heig.bdr.choochoo.api.team.model.TeamListViewModel;
import heig.bdr.choochoo.api.team.request.TeamRequestBody;
import heig.bdr.choochoo.business.repository.TeamRepository;
import heig.bdr.choochoo.business.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.joinTeam(email, teamId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/leave")
    @Transactional
    public ResponseEntity<Void> leaveTeam() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.leaveTeam(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    @Transactional
    public TeamDetailViewModel createTeam(@Valid @RequestBody TeamRequestBody name) {
        var team = teamRepository.create(name.getName());
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.joinTeam(email, team.getId());
        return teamDetailMapper.toViewModel(team);
    }
}
