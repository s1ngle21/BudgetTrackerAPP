package budgettrackerapp.service.user;

import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.RegistrationUserDto;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.dto.UserInfoDTO;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.mapper.UserInfoMapper;
import budgettrackerapp.mapper.UserMapper;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;


@Service
@Transactional
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private UserRepository userRepository;
    private UserInfoMapper userInfoMapper;
    private UserMapper userMapper;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO findUserInfoById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        UserInfoDTO userInfo = userInfoMapper.mapToDto(userRepository.findById(id).get());
        if (userInfo == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return userInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public void createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setEmail(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.createRoleUser("USER")));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String email) {
        return userRepository.findByEmail(email).get();
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
