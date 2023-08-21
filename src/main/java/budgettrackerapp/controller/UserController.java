package budgettrackerapp.controller;

import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.UserInfoDTO;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/balance")
    public ResponseEntity<String> setBalance(@RequestBody BalanceDTO balanceDto) {
        userService.setBalance(balanceDto, balanceDto.getUserId());
        return ResponseEntity
                .ok("Balance has been updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable Long id) {
        return ResponseEntity
                .ok(userService.findUserInfoById(id));
    }
}
