package budgettrackerapp.controller;

import budgettrackerapp.dto.AuthRequest;
import budgettrackerapp.dto.RegistrationResponse;
import budgettrackerapp.dto.RegistrationUserDto;
import budgettrackerapp.dto.TokenResponse;
import budgettrackerapp.service.authorization.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private AuthorizationService authorizationService;

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signUp(@RequestBody AuthRequest registrationRequest) {
        return ResponseEntity.ok(authorizationService.signIn(registrationRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<RegistrationResponse> signIn(@RequestBody RegistrationUserDto registrationUserDto) {
        return ResponseEntity.ok(authorizationService.signUp(registrationUserDto));
    }
}
