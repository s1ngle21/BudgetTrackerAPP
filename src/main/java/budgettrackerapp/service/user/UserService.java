package budgettrackerapp.service.user;


import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.RegistrationUserDto;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.dto.UserInfoDTO;
import budgettrackerapp.entity.User;


public interface UserService {
    void createNewUser(RegistrationUserDto registrationUserDto);
    User findByName(String email);
    UserDTO findById(Long id);

    UserInfoDTO findUserInfoById(Long id);

    void setBalance(BalanceDTO balanceDto, Long userId);

}
