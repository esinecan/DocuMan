package com.documentsies.DocuMan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;

@Controller
public class RedirectController {

    @Operation(summary = "Redirect to Swagger UI", description = "Redirects the user to the Swagger UI documentation")
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}