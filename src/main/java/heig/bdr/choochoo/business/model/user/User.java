package heig.bdr.choochoo.business.model.user;

import heig.bdr.choochoo.business.model.Journey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "traveller")
@Getter
@Setter
@Accessors(chain = true)
public class User {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

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

    private User addJourney(Journey journey) {
        if (null == journeys) {
            journeys = new HashSet<>();
        }

        journeys.add(journey);
        journey.setUser(this);

        return this;
    }
}
