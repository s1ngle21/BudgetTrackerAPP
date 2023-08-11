package budgettrackerapp.service.authorization;

import budgettrackerapp.dto.*;

public interface AuthorizationService {
    TokenResponse signIn(AuthRequest registrationRequest);

    RegistrationResponse signUp(RegistrationUserDto registrationUserDto);
}
