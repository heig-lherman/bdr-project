package heig.bdr.choochoo.business.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "group")
@Getter
@Setter
@Accessors(chain = true)
public class Group {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<User> users;

    private Group addUser(User user) {
        if (null == users) {
            users = new HashSet<>();
        }

        users.add(user);
        user.setGroup(this);

        return this;
    }
}
