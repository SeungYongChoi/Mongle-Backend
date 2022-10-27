package com.rtsj.return_to_soju.common.auth;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomJwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * 에러처리하기
     * @param userId the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundUserException::new);
        return new CustomJwtUserDetails(user);
    }
}

