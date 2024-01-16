package heig.bdr.choochoo.business.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "traveller_token")
@Getter
@Setter
@Accessors(chain = true)
public class UserToken {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expired", nullable = false)
    private boolean expired = false;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "traveller_fk", foreignKey = @ForeignKey(name = "fk_token__user"), nullable = false)
    private User user;

    public void revoke() {
        this.expired = true;
        this.revoked = true;
    }
}
