package heig.bdr.choochoo.business.model;

import jakarta.persistence.*;
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
    private Double distance;

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
