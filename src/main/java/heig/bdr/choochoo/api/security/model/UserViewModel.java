package heig.bdr.choochoo.api.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserViewModel {

    private final String email;
    private final String displayName;
    private final String firstName;
    private final String lastName;
}
