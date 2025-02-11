package co.kr.nomadlab.springgdic.example;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import org.springframework.data.domain.PageRequest;

public interface UserExample {

    User user = new User(1L, "ssar", "1234", "ssar@nate.com", RoleType.USER, UserStatus.ACTIVE);

    PageRequest pageRequest = PageRequest.of(1, 10);

}