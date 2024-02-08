package com.payhere.kimsan.user.application;


import static com.payhere.kimsan.common.exception.ErrorCode.INVALID_USER_INFO;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.user.domain.CustomUserDetails;
import com.payhere.kimsan.user.domain.User;
import com.payhere.kimsan.user.domain.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return username -> new CustomUserDetails(userRepository.findByUserId(username)
                                                               .orElseThrow(
                                                                   () -> new CustomException(
                                                                       INVALID_USER_INFO)));
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
