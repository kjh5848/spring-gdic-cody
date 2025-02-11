package co.kr.nomadlab.springgdic.module.user;

import co.kr.nomadlab.springgdic.common.BaseTime;
import co.kr.nomadlab.springgdic.common.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    private Long id;

    @Comment("사용자 명")
    private String username;

    @Comment("사용자 비밀번호")
    private String password;

    @Comment("사용자 이메일")
    private String email;

    @Comment("사용자 권한")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Comment("사용자 상태")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
