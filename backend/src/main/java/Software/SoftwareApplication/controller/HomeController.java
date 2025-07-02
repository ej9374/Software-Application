package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.dto.HomeRequestDto;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;


    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @PostMapping("/home")
    public ResponseEntity<List<HomeResponseDto>> getHomeRecipes(@RequestBody Integer userId) {
        List<HomeResponseDto> recipes = homeService.getHomeRecipes(userId);
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }



}
