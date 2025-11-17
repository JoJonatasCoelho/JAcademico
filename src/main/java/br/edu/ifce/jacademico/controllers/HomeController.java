package br.edu.ifce.jacademico.controllers;

// HomeController: renders homepage and dashboard entry.
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
