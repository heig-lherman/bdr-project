package heig.bdr.choochoo.business.model.locality;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "locality")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Accessors(chain = true)
public abstract class Locality {

    @Id
    @Column(name = "code", nullable = false)
    private Integer fsoNumber;

    @Column(name = "name", nullable = false)
    private String name;
}
