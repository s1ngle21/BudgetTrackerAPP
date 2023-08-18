package budgettrackerapp.service.authorization;

import budgettrackerapp.dto.*;
import budgettrackerapp.exeptions.PasswordsDoesNotMatchException;
import budgettrackerapp.exeptions.RegistrationException;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.exeptions.UserWithCurrentNameAlreadyExistException;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.user.UserDetailsServiceImpl;
import budgettrackerapp.service.user.UserService;
import budgettrackerapp.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SimpleAuthorizationService implements AuthorizationService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtils jwtTokenUtils;
    private UserService userService;
    private UserRepository userRepository;


    @Override
    public TokenResponse signIn(AuthRequest registrationRequest) {
        /*try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   registrationRequest.getUsername(), registrationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong username or password");
        }*/ //to remove

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(registrationRequest.getUsername());
            String token = jwtTokenUtils.generateToken(userDetails);
            return new TokenResponse(token);
        } catch (UsernameNotFoundException e) {
            throw new UserDoesNotExistException("User not found", e);
        }
    }

    @Override
    public RegistrationResponse signUp(RegistrationUserDto registrationUserDto) {
        if (registrationUserDto.getUsername().isEmpty()) {
            throw new RegistrationException("Username field can not be empty!");
        }
        if (registrationUserDto.getPassword().isEmpty()) {
            throw new RegistrationException("Password field can not be empty!");
        }
        if (userRepository.existsByEmail(registrationUserDto.getUsername())) {
            throw new UserWithCurrentNameAlreadyExistException(String.format("User with current username %s already exist!",
                    registrationUserDto.getUsername()));
        }
        userService.createNewUser(registrationUserDto);
        return new RegistrationResponse("Registration successful");
    }
}
