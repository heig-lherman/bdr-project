package heig.bdr.choochoo.business.model.locality;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "swiss_locality")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "fk_swsslocl__locality"))
@Getter
@Setter
@Accessors(chain = true)
public class SwissLocality extends Locality {

    @Column(name = "code", nullable = false)
    private Integer fsoNumber;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "canton_fk", foreignKey = @ForeignKey(name = "fk_swsslocl__canton"), nullable = false)
    private Canton canton;
}
