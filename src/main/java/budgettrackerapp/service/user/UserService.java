package budgettrackerapp.service.user;


import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.dto.UserINFO;


public interface UserService {
    UserDTO findById(Long id);

    UserINFO findUserInfoById(Long id);

    void setBalance(BalanceDTO balanceDto, Long userId);

}
