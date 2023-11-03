package com.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Swagger-контроллер
 */
@Controller
public class SwaggerUIController {

    @RequestMapping("/swagger-ui")
    public String swaggerUI() {
        return "redirect:/swagger-ui/index.html";
    }
}
