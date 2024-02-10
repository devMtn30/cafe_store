package com.payhere.kimsan.user.application;

import com.payhere.kimsan.user.application.UserService;
import com.payhere.kimsan.user.domain.User;
import com.payhere.kimsan.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void testFindByUserId() {
        String userId = "testUser";
        User user = new User();
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUserId(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }
}
