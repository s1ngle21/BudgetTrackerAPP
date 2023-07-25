package budgettrackerapp.controller;

import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{userId}/balance")
    public ResponseEntity<String> setBalance(@PathVariable Long userId, @RequestParam (required = false)BigDecimal amount) {
        userService.setBalance(userId, amount);
        return ResponseEntity
                .ok("Balance has been updated");
    }
}
