package org.zerock.club.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMemberRepositoryTest {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@zerock.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if(i > 80)
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            if(i > 90)
                clubMember.addMemberRole(ClubMemberRole.ADMIN);

            repository.save(clubMember);
        });
    }

    @Test
    void findByEmail() {
        String email = "user95@zerock.org";
        boolean social = false;
        Optional<ClubMember> result = repository.findByEmail(email, social);
        if(result.isPresent()) {
            ClubMember clubMember = result.get();
            System.out.println(clubMember);
        }
    }
}