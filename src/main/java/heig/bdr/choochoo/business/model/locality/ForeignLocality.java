package heig.bdr.choochoo.business.model.locality;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "foreign_locality")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "fk_frgnlocl__locality"))
@Getter
@Setter
@Accessors(chain = true)
public class ForeignLocality extends Locality {

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_fk", foreignKey = @ForeignKey(name = "fk_frgnlocl__country"), nullable = false)
    private Country country;
}
