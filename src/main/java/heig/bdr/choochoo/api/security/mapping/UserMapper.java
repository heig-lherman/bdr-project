package heig.bdr.choochoo.api.security.mapping;

import heig.bdr.choochoo.api.security.model.UserViewModel;
import heig.bdr.choochoo.business.model.user.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserViewModel toViewModel(User user);
}
