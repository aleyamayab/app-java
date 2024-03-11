package myapp.Helloword;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class HelloWorldController {
    @RequestMapping("/")
    public String helloworld() {
        String password = "mysecretpassword";
        // Aquí introducimos un problema de seguridad al imprimir la contraseña
        System.out.println("Password: " + password);
        return "Hello World";
    }
}
