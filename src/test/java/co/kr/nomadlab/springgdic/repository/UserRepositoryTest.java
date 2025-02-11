package co.kr.nomadlab.springgdic.repository;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserRepository;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("UserRepository 테스트")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        setUp("setUpUser", "1234", "setUpUser@nate.com", RoleType.ADMIN, UserStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("사용자 전체 조회")
    void selectAll() {
        List<User> userList = this.userRepository.findAll();
        Assertions.assertNotEquals(userList.size(), 0);

        User user = userList.get(0);
        Assertions.assertEquals(user.getUsername(), "admin");
    }

    @Test
    @DisplayName("사용자 조회 및 수정")
    void selectAndUpdate() {
        Optional<User> optionalUser = this.userRepository.findById(1L);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Assertions.assertEquals(user.getUsername(), "admin");

            String username = "임꺽정";
            user.setUsername(username);

            User merge = entityManager.merge(user);
            Assertions.assertEquals(merge.getUsername(), "임꺽정");
        } else {
            Assertions.assertNotNull(optionalUser.get());
        }
    }

    @Test
    @DisplayName("사용자 삽입 및 삭제")
    void insertAndDelete() {
        User user = setUp("user2", "1234", "user2@nate.com", RoleType.ADMIN, UserStatus.ACTIVE);
        Optional<User> optionalUser = this.userRepository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User result = optionalUser.get();
            Assertions.assertEquals(result.getUsername(), "user2");
            this.entityManager.remove(result);

            Optional<User> deletedUser = this.userRepository.findById(result.getId());
            if (deletedUser.isPresent()) {
                Assertions.assertNull(deletedUser.get());
            }
        } else {
            Assertions.assertNotNull(optionalUser.get());
        }
    }

    private User setUp (String username, String password, String email, RoleType role, UserStatus status){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        user.setStatus(status);
        return entityManager.persist(user);
    }
}
