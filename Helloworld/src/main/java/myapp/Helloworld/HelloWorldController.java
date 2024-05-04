package myapp.Helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
        @RequestMapping("/app")
        public String helloworld() {
            return "Este es un nuevo intento, lo lograste!";
    }
}
