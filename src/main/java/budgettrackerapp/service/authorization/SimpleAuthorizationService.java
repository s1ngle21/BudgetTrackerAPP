package budgettrackerapp.service.authorization;

import budgettrackerapp.dto.*;
import budgettrackerapp.exeptions.PasswordsDoesNotMatchException;
import budgettrackerapp.exeptions.UserWithCurrentNameAlreadyExistException;
import budgettrackerapp.service.user.UserDetailsServiceImpl;
import budgettrackerapp.service.user.UserService;
import budgettrackerapp.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SimpleAuthorizationService implements AuthorizationService {

    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;
    private JwtTokenUtils jwtTokenUtils;
    private UserService userService;


    @Override
    public TokenResponse getAuthToken(AuthorizationRequest registrationRequest) {
        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   registrationRequest.getUsername(), registrationRequest.getPassword()));
        } catch (BadCredentialsException e) {
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(registrationRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new TokenResponse(token);
    }

    @Override
    public RegistrationResponse createUser(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new PasswordsDoesNotMatchException("Passwords does not match!");
        }

        if (userService.findByName(registrationUserDto.getUsername()) != null) {
            throw new UserWithCurrentNameAlreadyExistException("User with current username already exist!");
        }
        userService.createNewUser(registrationUserDto);
        return new RegistrationResponse("Registration successful");
    }
}
