package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {
    @GetMapping("/all")
    public void all() {
        log.info("all");
    }

    @GetMapping("/member")
    public void member(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("member");
        log.info(clubAuthMemberDTO);
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("admin");
    }
}
