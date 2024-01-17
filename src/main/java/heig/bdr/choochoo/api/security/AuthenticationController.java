package heig.bdr.choochoo.api.security;

import heig.bdr.choochoo.api.security.mapping.UserMapper;
import heig.bdr.choochoo.api.security.model.AuthenticationViewModel;
import heig.bdr.choochoo.api.security.model.UserViewModel;
import heig.bdr.choochoo.api.security.request.LoginRequestBody;
import heig.bdr.choochoo.api.security.request.RefreshTokenRequestBody;
import heig.bdr.choochoo.api.security.request.RegisterRequestBody;
import heig.bdr.choochoo.business.service.security.AuthenticationService;
import heig.bdr.choochoo.security.UserSecurityGetter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @GetMapping("/@me")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<UserViewModel> getUser() {
        return ResponseEntity.ok(userMapper.toViewModel(
                UserSecurityGetter.getAuthenticatedUser()
        ));
    }

    @PostMapping("/login")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<AuthenticationViewModel> login(
            @Valid @RequestBody LoginRequestBody request
    ) {
        return ResponseEntity.ok(
                authenticationService.login(request)
        );
    }

    @PostMapping("/register")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequestBody request
    ) {
        authenticationService.register(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<AuthenticationViewModel> refreshToken(
            @Valid @RequestBody RefreshTokenRequestBody request
    ) {
        return new ResponseEntity<>(
                authenticationService.refreshToken(request),
                HttpStatus.CREATED
        );
    }
}
