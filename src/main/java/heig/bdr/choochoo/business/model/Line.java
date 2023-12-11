package heig.bdr.choochoo.business.model;

import heig.bdr.choochoo.business.model.reference.StationType;
import heig.bdr.choochoo.business.model.reference.TrackType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "line")
@Getter
@Setter
@Accessors(chain = true)
public class Line {

    @Id
    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "track_type_fk", foreignKey = @ForeignKey(name = "fk_line__track_type"), nullable = false)
    private TrackType trackType;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "station_start_fk", foreignKey = @ForeignKey(name = "fk_line__station_start"), nullable = false)
    private Station stationStart;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "station_end_fk", foreignKey = @ForeignKey(name = "fk_line__station_end"), nullable = false)
    private Station stationEnd;

    @OneToMany(mappedBy = "line", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Set<Segment> segments;

}
