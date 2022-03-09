package org.zerock.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.ClubMemberRepository;
import org.zerock.club.entity.ClubMemberRole;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = null;
        if(clientName.equals("Google"))
            email = oAuth2User.getAttribute("email");

        ClubMember clubMember = saveSocialMember(email);
        return new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet()),
                oAuth2User.getAttributes()
        );
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);

        if(result.isPresent())
            return result.get();

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        clubMemberRepository.save(clubMember);
        return clubMember;
    }
}
