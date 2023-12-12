package heig.bdr.choochoo.business.job.mapper;

import heig.bdr.choochoo.business.model.Station;
import heig.bdr.choochoo.business.model.locality.*;
import heig.bdr.choochoo.business.model.reference.StationType;
import heig.bdr.choochoo.business.repository.*;
import heig.bdr.choochoo.connector.opendata.ApiStation;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Slf4j
@Component
public class StationItemProcessor implements ItemProcessor<ApiStation, Station> {

    private final EntityManager em;
    private final SwissLocalityRepository swissLocalityRepository;
    private final ForeignLocalityRepository foreignLocalityRepository;
    private final CountryRepository countryRepository;
    private final CantonRepository cantonRepository;
    private final StationTypeRepository stationTypeRepository;

    public StationItemProcessor(
            EntityManager em,
            SwissLocalityRepository swissLocalityRepository,
            ForeignLocalityRepository foreignLocalityRepository,
            CountryRepository countryRepository,
            CantonRepository cantonRepository,
            StationTypeRepository stationTypeRepository
    ) {
        this.em = em;
        this.swissLocalityRepository = swissLocalityRepository;
        this.foreignLocalityRepository = foreignLocalityRepository;
        this.countryRepository = countryRepository;
        this.cantonRepository = cantonRepository;
        this.stationTypeRepository = stationTypeRepository;
    }

    @Override
    public Station process(ApiStation item) {
        if (!isViable(item)) {
            return null;
        }

        var parsedType = item.getParsedStationType();

        return new Station()
                .setOpuic(item.getOpuic())
                .setName(item.getName())
                .setAbbreviatedName(item.getAbbreviation())
                .setPosition(item.getPosition().toPoint())
                .setLocality(buildLocality(item))
                .setStationType(buildStationType(parsedType));
    }

    private boolean isViable(ApiStation item) {
        return nonNull(item.getAbbreviation())
                && nonNull(item.getPosition());
    }

    private Locality buildLocality(ApiStation item) {
        if (item.getCountryIso2().equals("CH")) {
            return buildSwissLocality(item);
        }

        return buildForeignLocality(item);
    }

    private SwissLocality buildSwissLocality(ApiStation item) {
        return em.merge(
                swissLocalityRepository.findByFsoNumber(item.getLocalityFsoNumber()).orElseGet(() -> {
                    var canton = cantonRepository.findById(item.getCantonIso2()).orElseGet(() -> (
                            new Canton()
                                    .setIsoCode(item.getCantonIso2())
                                    .setName(item.getCantonName())
                    ));

                    return (SwissLocality) new SwissLocality()
                            .setFsoNumber(item.getLocalityFsoNumber())
                            .setCanton(canton)
                            .setName(item.getLocality());
                })
        );
    }

    private ForeignLocality buildForeignLocality(ApiStation item) {
        return em.merge(
                foreignLocalityRepository.findByName(item.getName()).orElseGet(() -> {
                    var country = countryRepository.findById(item.getCountryIso2()).orElseGet(() -> (
                            new Country()
                                    .setIsoCode(item.getCountryIso2())
                                    .setName(item.getCountryIso2())
                    ));

                    return (ForeignLocality) new ForeignLocality()
                            .setCountry(country)
                            .setName(item.getName());
                })
        );
    }

    private StationType buildStationType(ApiStation.StationType item) {
        if (item == null) {
            return buildStationType(ApiStation.StationType.TRAIN);
        }

        return em.merge(
                stationTypeRepository.findById(item.getName()).orElseGet(() -> (
                        new StationType()
                                .setCode(item.getName())
                                .setDescription(item.getDescription())
                ))
        );
    }
}
