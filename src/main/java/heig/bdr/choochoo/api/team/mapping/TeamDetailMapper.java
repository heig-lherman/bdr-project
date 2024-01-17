package heig.bdr.choochoo.api.team.mapping;

import heig.bdr.choochoo.api.team.model.TeamDetailViewModel;
import heig.bdr.choochoo.business.model.user.Team;
import org.mapstruct.Mapper;

@Mapper
public interface TeamDetailMapper {

    TeamDetailViewModel toViewModel(Team team);
}
