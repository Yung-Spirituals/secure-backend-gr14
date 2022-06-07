package no.ntnu.secureBackendGr14.Controllers;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/")
    public String testPublic() {
        return "This is public :)";
    }

    @GetMapping("/user")
    public String testUser() {
        return "This is user level :D";
    }

    @GetMapping("/admin")
    public String testAdmin() {
        return "This is admin level!!! ;D";
    }

    //TODO: Implement this
    /*@PostMapping("/purchase")
    public String makeOrder(@RequestHeader Authorization authorization){
        //logic
    }*/
}