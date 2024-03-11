package myapp.Helloword;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String helloworld() {
        // Violación: No se cierra el bloque correctamente
        if (true) {
            System.out.println("Hello World";
        }
        return "Hello World";
    }
}
