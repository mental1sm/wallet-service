package com.ment09.walletservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AppController {

    @GetMapping("/")
    public String getHome() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/secured")
    public String getSecuredIndex() {
        return "index-secured";
    }

}
