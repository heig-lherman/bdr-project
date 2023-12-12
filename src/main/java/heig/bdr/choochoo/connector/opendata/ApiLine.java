package heig.bdr.choochoo.connector.opendata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApiLine {

    @JsonProperty("linie")
    @EqualsAndHashCode.Include
    private int lineNumber;

    @JsonProperty("linienname")
    private String lineName;

    @JsonProperty("bpk_anfang")
    private String stationStartName;

    @JsonProperty("bpk_ende")
    private String stationEndName;
}
