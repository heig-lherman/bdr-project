package heig.bdr.choochoo.business.service.security;

import heig.bdr.choochoo.api.security.model.AuthenticationViewModel;
import heig.bdr.choochoo.api.security.request.LoginRequestBody;
import heig.bdr.choochoo.api.security.request.RefreshTokenRequestBody;
import heig.bdr.choochoo.api.security.request.RegisterRequestBody;
import heig.bdr.choochoo.business.model.user.User;
import heig.bdr.choochoo.business.model.user.UserToken;
import heig.bdr.choochoo.business.repository.UserRepository;
import heig.bdr.choochoo.business.repository.UserTokenRepository;
import heig.bdr.choochoo.exception.TokenExpiredException;
import heig.bdr.choochoo.exception.UsernameAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserTokenRepository tokenRepository;

    public AuthenticationViewModel login(LoginRequestBody request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email [" + request.getEmail() + "] not found"));

        revokeAllUserTokens(user);

        return saveUserTokenAndReturnAuthResponse(user);
    }

    public AuthenticationViewModel register(RegisterRequestBody request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UsernameAlreadyExistsException();
        }

        User user = userRepository.save(buildUser(request));
        return saveUserTokenAndReturnAuthResponse(user);
    }

    public AuthenticationViewModel refreshToken(RefreshTokenRequestBody request) {
        var refreshToken = request.getRefreshToken();
        try {
            var userEmail = jwtService.extractUsername(refreshToken);
            var user = userRepository.findByEmail(userEmail).orElseThrow();

            var isRefreshTokenValid = jwtService.isTokenValid(refreshToken, user);
            if (!isRefreshTokenValid) {
                refreshToken = jwtService.generateRefreshToken(user);
            }

            String accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveToken(user, accessToken, refreshToken);

            return AuthenticationViewModel.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        }
    }

    private User buildUser(RegisterRequestBody request) {
        return new User()
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName());
    }

    private AuthenticationViewModel saveUserTokenAndReturnAuthResponse(User user) {
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveToken(user, jwtToken, refreshToken);

        return AuthenticationViewModel.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveToken(User user, String jwtToken, String refreshToken) {
        var token = new UserToken()
                .setUser(user)
                .setToken(jwtToken)
                .setRefreshToken(refreshToken);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        tokenRepository.expireEveryActiveTokenForUser(user.getEmail());
    }
}
