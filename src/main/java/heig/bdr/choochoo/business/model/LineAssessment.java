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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(
        name = "line_assessment",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_lineassmt__line_user", columnNames = {"line_fk", "user_fk"})
        }
)
@Getter
@Setter
@Accessors(chain = true)
public class LineAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "line_fk", foreignKey = @ForeignKey(name = "fk_lineassmt__line"), nullable = false)
    private Line line;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "user_fk", foreignKey = @ForeignKey(name = "fk_lineassmt__user"), nullable = false)
    private User user;

    @Column(name = "grade", scale = 2, precision = 1, nullable = false)
    private Integer grade;

    @Column(name = "review", length = 2000)
    private String review;

}
