package budgettrackerapp.service.user;

import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.dto.UserINFO;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.mapper.UserInfoMapper;
import budgettrackerapp.mapper.UserMapper;
import budgettrackerapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;


@Service
@Transactional
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private UserRepository userRepository;
    private UserInfoMapper userInfoMapper;
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserINFO findUserInfoById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        UserINFO userInfo = userInfoMapper.mapToDto(userRepository.findById(id).get());
        if (userInfo == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return userInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        UserDTO userDto = userMapper.mapToDto(userRepository.findById(id).get());
        if (userDto == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return userDto;
    }

    @Override
    public void setBalance(BalanceDTO balanceDto, Long userId) {
        Objects.requireNonNull(balanceDto, "Amount must be provided for this operation");
        User user = userRepository.findById(userId).get();
        if (user == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        user.setBalance(balanceDto.getAmount());
        userRepository.save(user);
    }

}
