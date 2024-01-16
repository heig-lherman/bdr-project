package heig.bdr.choochoo.business.model.user;

import heig.bdr.choochoo.business.model.Journey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "traveller")
@Getter
@Setter
@Accessors(chain = true)
public class User implements UserDetails {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Getter(AccessLevel.NONE)
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "team_fk", foreignKey = @ForeignKey(name = "fk_user__team"))
    private Team team;

    @OneToMany(mappedBy = "user")
    private Set<Journey> journeys;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserToken> tokens;

    private User addJourney(Journey journey) {
        if (null == journeys) {
            journeys = new HashSet<>();
        }

        journeys.add(journey);
        journey.setUser(this);

        return this;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @Override
    @Transient
    public String getUsername() {
        return email;
    }

    @Transient
    public String getDisplayName() {
        return username;
    }
}
