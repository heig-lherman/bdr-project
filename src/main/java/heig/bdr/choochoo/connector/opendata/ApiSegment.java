package heig.bdr.choochoo.connector.opendata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApiSegment {

    @JsonProperty("linienr")
    @EqualsAndHashCode.Include
    private int lineNumber;

    @JsonProperty("linename")
    private String lineName;

    @JsonProperty("spurweite")
    private String trackType;

    @JsonProperty("km_agm_von")
    private double kmFrom;

    @JsonProperty("km_agm_bis")
    private double kmTo;

    @JsonProperty("bp_anfang")
    @EqualsAndHashCode.Include
    private String stationStart;

    @JsonProperty("bp_anf_bez")
    private String stationStartName;

    @JsonProperty("bp_ende")
    @EqualsAndHashCode.Include
    private String stationEnd;

    @JsonProperty("bp_end_bez")
    private String stationEndName;

    @JsonIgnore
    public double getDistance() {
        return kmTo - kmFrom;
    }
}
