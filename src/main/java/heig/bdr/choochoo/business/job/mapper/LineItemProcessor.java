package heig.bdr.choochoo.business.job.mapper;

import heig.bdr.choochoo.business.model.Line;
import heig.bdr.choochoo.business.repository.StationRepository;
import heig.bdr.choochoo.connector.opendata.ApiLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LineItemProcessor implements ItemProcessor<ApiLine, Line> {

    private final StationRepository stationRepository;

    public LineItemProcessor(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Line process(ApiLine item) {
        var stationStart = stationRepository.findByNameInsensitive(item.getStationStartName()).orElse(null);
        var stationEnd = stationRepository.findByNameInsensitive(item.getStationEndName()).orElse(null);

        if (stationStart == null || stationEnd == null) {
            log.warn("Skipping segment {} because station {} or {} does not exist", item, item.getStationStartName(), item.getStationEndName());
            return null;
        }

        return new Line()
                .setLineNumber(item.getLineNumber())
                .setName(item.getLineName())
                .setStationStart(stationStart)
                .setStationEnd(stationEnd);
    }
}
