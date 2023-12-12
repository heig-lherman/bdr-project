package heig.bdr.choochoo.business.model;

import heig.bdr.choochoo.business.model.locality.Locality;
import heig.bdr.choochoo.business.model.reference.StationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "station", uniqueConstraints = {
        @UniqueConstraint(name = "uk_station__abbrv", columnNames = {"abbreviated_name"}),
}, indexes = {
        @Index(name = "idx_station__name", columnList = "name"),
        @Index(name = "idx_station__abbrv", columnList = "abbreviated_name"),
})
@Getter
@Setter
@Accessors(chain = true)
public class Station {

    @Id
    @Column(name = "opuic", nullable = false)
    private Long opuic;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abbreviated_name", nullable = false, length = 6)
    private String abbreviatedName;

    @Column(name = "position", nullable = false)
    private Point position;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "locality_fk", foreignKey = @ForeignKey(name = "fk_station__locality"), nullable = false)
    private Locality locality;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "station_type_fk", foreignKey = @ForeignKey(name = "fk_station__station_type"))
    private StationType stationType;
}
