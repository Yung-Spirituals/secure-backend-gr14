package no.ntnu.secureBackendGr14.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TestController
{
    @GetMapping("/")
    public String testPublic()
    {
        return "This is public :)";
    }

    @GetMapping("/user")
    public String testUser()
    {
        return "This is user level :D";
    }

    @GetMapping("/admin")
    public String testAdmin()
    {
        return "This is admin level!!! ;D";
    }
}
