package co.kr.nomadlab.springgdic.config.security;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.user.UserRepository;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다."));
        if (user.getStatus().equals(UserStatus.DELETED)) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        } else if (user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new UsernameNotFoundException("비활성화 된 계정입니다.");
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}
