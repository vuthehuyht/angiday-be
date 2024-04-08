package com.macrace.angidaybe.repositories;

import com.macrace.angidaybe.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setupData() {
        User userTest = User.builder()

                .build();
    }
}
