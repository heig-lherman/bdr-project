package heig.bdr.choochoo.api.station.mapping;

import heig.bdr.choochoo.api.station.model.ConnectingStationViewModel;
import heig.bdr.choochoo.api.station.model.PathfindingViewModel;
import heig.bdr.choochoo.api.station.model.StationSearchViewModel;
import heig.bdr.choochoo.business.repository.data.ConnectingStation;
import heig.bdr.choochoo.business.repository.data.PathfindingResult;
import heig.bdr.choochoo.business.repository.data.StationSearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StationSearchMapper {

    StationSearchViewModel toViewModel(StationSearch station);

    @Mapping(target = "position", source = "geoposition")
    ConnectingStationViewModel toViewModel(ConnectingStation station);

    PathfindingViewModel toViewModel(PathfindingResult station);
}
