package heig.bdr.choochoo.business.job.mapper;

import heig.bdr.choochoo.business.model.Segment;
import heig.bdr.choochoo.business.repository.LineRepository;
import heig.bdr.choochoo.business.repository.StationRepository;
import heig.bdr.choochoo.connector.opendata.ApiSegment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SegmentItemProcessor implements ItemProcessor<ApiSegment, Segment> {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public SegmentItemProcessor(
            LineRepository lineRepository,
            StationRepository stationRepository
    ) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public Segment process(ApiSegment item) {
        var line = lineRepository.findById(item.getLineNumber()).orElse(null);
        var stationStart = stationRepository.findByAbbreviatedName(item.getStationStart()).orElse(null);
        var stationEnd = stationRepository.findByAbbreviatedName(item.getStationEnd()).orElse(null);

        if (line == null) {
            log.warn("Skipping segment {} because line {} does not exist", item, item.getLineNumber());
            return null;
        }

        if (stationStart == null || stationEnd == null) {
            log.warn("Skipping segment {} because station {} or {} does not exist", item, item.getStationStart(), item.getStationEnd());
            return null;
        }

        return new Segment()
                .setDistance(item.getDistance())
                .setStationStart(stationStart)
                .setStationEnd(stationEnd)
                .setLine(line)
                .setGeometry(item.getGeoShape().geometry());
    }
}
