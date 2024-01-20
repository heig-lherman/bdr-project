package heig.bdr.choochoo.api.progress.mapping;

import heig.bdr.choochoo.api.progress.model.CantonProgressViewModel;
import heig.bdr.choochoo.business.repository.data.CantonProgress;
import org.mapstruct.Mapper;

@Mapper
public interface CantonProgressMapper {

    CantonProgressViewModel toViewModel(CantonProgress cantonProgress);
}
