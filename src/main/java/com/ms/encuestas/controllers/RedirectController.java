package com.ms.encuestas.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ab")
public class RedirectController {

	@GetMapping("/ab")
    public String index() {
        return "redirect:/index.html";
    }
}
