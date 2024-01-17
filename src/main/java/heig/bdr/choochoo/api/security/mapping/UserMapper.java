package heig.bdr.choochoo.api.security.mapping;

import heig.bdr.choochoo.api.security.model.UserViewModel;
import heig.bdr.choochoo.business.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "teamId", source = "team.id")
    UserViewModel toViewModel(User user);
}
