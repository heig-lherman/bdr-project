package heig.bdr.choochoo.security;

import heig.bdr.choochoo.business.model.user.User;
import heig.bdr.choochoo.exception.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public interface UserSecurityGetter {

    static User getAuthenticatedUser() {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .orElseThrow(UserNotAuthenticatedException::new);
    }

    private static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

}
