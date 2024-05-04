package com.example.Helloworldjava;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
        @RequestMapping("/")
        public String helloworld() {
            return "Este es un nuevo intento, lo lograste!";
    }
}


