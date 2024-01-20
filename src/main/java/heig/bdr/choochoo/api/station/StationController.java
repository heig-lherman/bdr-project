package heig.bdr.choochoo.api.station;

import heig.bdr.choochoo.api.station.mapping.StationSearchMapper;
import heig.bdr.choochoo.api.station.model.ConnectingStationViewModel;
import heig.bdr.choochoo.api.station.model.PathfindingViewModel;
import heig.bdr.choochoo.api.station.model.StationSearchViewModel;
import heig.bdr.choochoo.business.repository.ConnectingStationRepository;
import heig.bdr.choochoo.business.repository.PathfindingSearchRepository;
import heig.bdr.choochoo.business.repository.StationSearchRepository;
import heig.bdr.choochoo.business.repository.data.PathfindingResult;
import lombok.RequiredArgsConstructor;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.jts.JTS;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/stations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StationController {

    private final StationSearchRepository stationSearchRepository;
    private final ConnectingStationRepository connectingStationRepository;
    private final PathfindingSearchRepository pathfindingSearchRepository;
    private final StationSearchMapper stationSearchMapper;

    @GetMapping(path = "/search")
    @Transactional(readOnly = true)
    public List<StationSearchViewModel> searchStations(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "amount", defaultValue = "10") int amount
    ) {
        return stationSearchRepository.searchFuzzy(query, amount).stream()
                .map(stationSearchMapper::toViewModel)
                .toList();
    }

    @GetMapping(path = "/search/{lon}/{lat}/closest")
    @Transactional(readOnly = true)
    public List<StationSearchViewModel> searchClosest(
            @PathVariable(name = "lon") double lon,
            @PathVariable(name = "lat") double lat,
            @RequestParam(name = "amount", defaultValue = "5") int amount
    ) {
        var coordinate = JTS.to(new Point<>(new G2D(lon, lat), CoordinateReferenceSystems.WGS84));
        return stationSearchRepository.findClosest(coordinate, amount).stream()
                .map(stationSearchMapper::toViewModel)
                .toList();
    }

    @GetMapping(path = "/{opuic}/search/connecting")
    @Transactional(readOnly = true)
    public List<ConnectingStationViewModel> searchConnecting(
            @PathVariable(name = "opuic") long opuic,
            @RequestParam(name = "distance", defaultValue = "15") int distance
    ) {
        return connectingStationRepository.findConnectingStations(opuic, distance).stream()
                .map(stationSearchMapper::toViewModel)
                .toList();
    }

    @GetMapping(path = "/{startOpuic}/{endOpuic}/search/path-finding")
    @Transactional(readOnly = true)
    public List<PathfindingViewModel> searchPathFinding(
            @PathVariable(name = "startOpuic") long startOpuic,
            @PathVariable(name = "endOpuic") long endOpuic,
            @RequestParam(name = "amount", defaultValue = "4") int amount
    ) {
        return pathfindingSearchRepository.findPaths(startOpuic, endOpuic, amount).stream()
                .sorted(Comparator.comparing(PathfindingResult::getRank))
                .map(stationSearchMapper::toViewModel)
                .toList();
    }
}
