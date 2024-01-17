package heig.bdr.choochoo.api.security;

import heig.bdr.choochoo.api.security.mapping.UserMapper;
import heig.bdr.choochoo.api.security.model.AuthenticationViewModel;
import heig.bdr.choochoo.api.security.model.UserViewModel;
import heig.bdr.choochoo.api.security.request.LoginRequestBody;
import heig.bdr.choochoo.api.security.request.RefreshTokenRequestBody;
import heig.bdr.choochoo.api.security.request.RegisterRequestBody;
import heig.bdr.choochoo.business.service.security.AuthenticationService;
import heig.bdr.choochoo.security.UserSecurityGetter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @GetMapping("/@me")
    @Transactional(rollbackOn = Throwable.class)
    public ResponseEntity<UserViewModel> getUser() {
        return ResponseEntity.ok(userMapper.toViewModel(
                UserSecurityGetter.getAuthenticatedUser()
        ));
    }

    @PostMapping("/login")
    @Transactional(rollbackOn = Throwable.class)
    public ResponseEntity<AuthenticationViewModel> login(
            @Valid @RequestBody LoginRequestBody request
    ) {
        return ResponseEntity.ok(
                authenticationService.login(request)
        );
    }

    @PostMapping("/register")
    @Transactional(rollbackOn = Throwable.class)
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequestBody request
    ) {
        authenticationService.register(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    @Transactional(rollbackOn = Throwable.class)
    public ResponseEntity<AuthenticationViewModel> refreshToken(
            @Valid @RequestBody RefreshTokenRequestBody request
    ) {
        return new ResponseEntity<>(
                authenticationService.refreshToken(request),
                HttpStatus.CREATED
        );
    }
}
