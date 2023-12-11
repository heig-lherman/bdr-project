package heig.bdr.choochoo.business.model;

import heig.bdr.choochoo.business.model.locality.Locality;
import heig.bdr.choochoo.business.model.reference.StationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "station")
@Getter
@Setter
@Accessors(chain = true)
public class Station {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "didok_id", nullable = false)
    private String didokIdentifier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private Point position;

    @Column(name = "km_point", nullable = false)
    private Float kmPoint;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "locality_fk", foreignKey = @ForeignKey(name = "fk_station__locality"), nullable = false)
    private Locality locality;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "station_type_fk", foreignKey = @ForeignKey(name = "fk_station__station_type"), nullable = false)
    private StationType stationType;
}
