package org.zerock.club;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder() {
        String rawPassword = "1111";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        boolean matches = passwordEncoder.matches(rawPassword,encodedPassword);
        assertThat(matches).isTrue();
    }
}