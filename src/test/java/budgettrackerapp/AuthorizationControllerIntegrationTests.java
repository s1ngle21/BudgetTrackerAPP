package budgettrackerapp;

import budgettrackerapp.controller.AuthorizationController;
import budgettrackerapp.dto.AuthorizationRequest;
import budgettrackerapp.dto.RegistrationResponse;
import budgettrackerapp.dto.RegistrationUserDto;
import budgettrackerapp.dto.TokenResponse;
import budgettrackerapp.exeptions.RegistrationException;
import budgettrackerapp.exeptions.UserWithCurrentNameAlreadyExistException;
import budgettrackerapp.service.authorization.SimpleAuthorizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerIntegrationTests {

    @Mock
    private SimpleAuthorizationService authorizationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthorizationController authorizationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build();
    }

    @Test
    public void whenPerformGetAuthTokenOperationTokenMustBeReturned() throws Exception {
        AuthorizationRequest request = new AuthorizationRequest();
        request.setUsername("test_username");
        request.setPassword("test_password");

        TokenResponse tokenResponse = new TokenResponse("test_token");

        when(authorizationService.getAuthToken(any(AuthorizationRequest.class)))
                .thenReturn(tokenResponse);


        mockMvc.perform(post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test_token"));
    }

    @Test
    public void whenPerformCreateUserOperationUserMustBeCreated() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test_username");
        registrationUserDto.setPassword("test_password");
        registrationUserDto.setConfirmPassword("test_password");

        RegistrationResponse response = new RegistrationResponse("Registration successful");

        when(authorizationService.createUser(any(RegistrationUserDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(registrationUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"));
    }

    @Test
    public void whenPerformGetAuthTokenOperationWithWrongCredentialsBadCredentialsExceptionMustBeThrown() throws Exception {
        AuthorizationRequest request = new AuthorizationRequest();
        request.setUsername("test_username");
        request.setPassword("wrong_password");

        when(authorizationService.getAuthToken(any(AuthorizationRequest.class)))
                .thenThrow(new BadCredentialsException("Wrong username or password"));

        mockMvc.perform(post("/authorization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Wrong username or password"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
                .andExpect(result -> assertEquals("Wrong username or password", result.getResolvedException().getMessage()));
    }

    @Test
    public void whenPerformCreateUserOperationAndPasswordsDoesNotMatchesThenPasswordsDoesNotMatchExceptionMustBeThrown() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test_username");
        registrationUserDto.setPassword("test_password");
        registrationUserDto.setConfirmPassword("wrong_test_password");

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(registrationUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Passwords does not match!"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    public void whenPerformCreateUserOperationAndUsernameIsEmptyThenRegistrationExceptionMustBeThrown() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(registrationUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username field can not be empty"))
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    public void whenPerformCreateUserOperationAndPasswordIsEmptyThenRegistrationExceptionMustBeThrown() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test_username");
        registrationUserDto.setPassword("");
        registrationUserDto.setConfirmPassword("");



        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(registrationUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password field can not be empty"))
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    public void whenPerformCreateUserAndUserWithGivenUserNameAlreadyExistThenUserWithCurrentNameAlreadyExistExceptionMustBeThrown() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("existing_user");
        registrationUserDto.setPassword("test_password");
        registrationUserDto.setConfirmPassword("test_password");

        when(authorizationService.createUser(any(RegistrationUserDto.class)))
                .thenThrow(new UserWithCurrentNameAlreadyExistException("User with current username already exist!"));

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(registrationUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with current username already exist!"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserWithCurrentNameAlreadyExistException))
                .andExpect(result -> assertEquals("Wrong username or password", result.getResolvedException().getMessage()));


    }

    public String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
