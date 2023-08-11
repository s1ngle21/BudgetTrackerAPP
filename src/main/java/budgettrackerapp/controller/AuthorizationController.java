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
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthorizationController {
    private AuthorizationService authorizationService;

    @PostMapping("/authorization")
    public ResponseEntity<TokenResponse> getAuthToken(@RequestBody AuthRequest registrationRequest) {
        return ResponseEntity.ok(authorizationService.signIn(registrationRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return ResponseEntity.ok(authorizationService.signUp(registrationUserDto));
    }
}
