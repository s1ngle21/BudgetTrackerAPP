package budgettrackerapp.service.authorization;

import budgettrackerapp.dto.*;

public interface AuthorizationService {
    TokenResponse getAuthToken(AuthorizationRequest registrationRequest);

    RegistrationResponse createUser(RegistrationUserDto registrationUserDto);
}
