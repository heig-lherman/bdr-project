package heig.bdr.choochoo.business.model;

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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "segment")
@Getter
@Setter
@Accessors(chain = true)
public class Segment {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance", nullable = false)
    private Float distance;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "line_fk", foreignKey = @ForeignKey(name = "fk_segment__line"), nullable = false)
    private Line line;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "station_start_fk", foreignKey = @ForeignKey(name = "fk_segment__station_start"), nullable = false)
    private Station stationStart;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    @JoinColumn(name = "station_end_fk", foreignKey = @ForeignKey(name = "fk_segment__station_end"), nullable = false)
    private Station stationEnd;
}
