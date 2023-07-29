package budgettrackerapp.controller;

import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.UserINFO;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> setBalance(@RequestBody BalanceDTO balanceDto,
                                             @PathVariable Long userId) {
        userService.setBalance(balanceDto, userId);
        return ResponseEntity
                .ok("Balance has been updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserINFO> getUser(@PathVariable Long id) {
        return ResponseEntity
                .ok(userService.findUserInfoById(id));
    }
}
