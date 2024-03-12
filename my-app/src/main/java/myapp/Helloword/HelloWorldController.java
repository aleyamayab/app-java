package myapp.Helloword;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/")
    public String helloworld() {
        // Puedes agregar un mensaje adicional para indicar que la prueba ha sido exitosa
        String successMessage = " - Test successful!";
        return "Hello World - Version 2.0" + successMessage;
    }
}
