package co.kr.nomadlab.springgdic.consts;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import org.springframework.data.domain.PageRequest;

public interface UserConst {
    User user = new User(1L, "kkr", "1234", "kkr@nate.com", RoleType.USER, UserStatus.ACTIVE);

    PageRequest pageRequest = PageRequest.of(1, 10);
}
