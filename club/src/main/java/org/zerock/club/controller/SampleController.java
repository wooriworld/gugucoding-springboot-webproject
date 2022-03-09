package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {
    @GetMapping("/all")
    public void all() {
        log.info("all");
    }

    @GetMapping("/member")
    public void member() {
        log.info("member");
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("admin");
    }
}
