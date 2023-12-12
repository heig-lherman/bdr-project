package heig.bdr.choochoo.business.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "team")
@Getter
@Setter
@Accessors(chain = true)
public class Team {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    private Set<User> users;

    private Team addUser(User user) {
        if (null == users) {
            users = new HashSet<>();
        }

        users.add(user);
        user.setTeam(this);

        return this;
    }
}
