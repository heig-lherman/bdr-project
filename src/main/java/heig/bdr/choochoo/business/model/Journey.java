package heig.bdr.choochoo.business.model;

import heig.bdr.choochoo.business.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "journey")
@Getter
@Setter
@Accessors(chain = true)
public class Journey {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "review", length = 2000)
    private String review;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "user_fk", foreignKey = @ForeignKey(name = "fk_journey__user"), nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "journey_segment",
            joinColumns = @JoinColumn(
                    name = "journey_fk",
                    foreignKey = @ForeignKey(name = "fk_journey_segment__journey")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "segment_fk",
                    foreignKey = @ForeignKey(name = "fk_journey_segment__segment")
            )
    )
    private Set<Segment> segments;
}
