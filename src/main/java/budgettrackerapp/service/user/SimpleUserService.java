package budgettrackerapp.service.user;

import budgettrackerapp.dto.BalanceDTO;
import budgettrackerapp.dto.RegistrationUserDto;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.dto.UserInfoDTO;
import budgettrackerapp.entity.Role;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.mapper.UserInfoMapper;
import budgettrackerapp.mapper.UserMapper;
import budgettrackerapp.repository.user.UserRepository;
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
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO findUserInfoById(Long userId) {
        Objects.requireNonNull(userId, "Parameter [userId] must not be null!");
        return userInfoMapper.mapToDto(userRepository.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long userId) {
        Objects.requireNonNull(userId, "Id must be provided for this operation!");
        return userMapper.mapToDto(userRepository.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User not found")));
    }

    @Override
    public void createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setEmail(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(Role.getDefault()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String email) {
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        return userRepository.findByEmail(email).get();
    }

    @Override
    public void setBalance(BalanceDTO balanceDto, Long userId) {
        Objects.requireNonNull(balanceDto.getAmount(), "Amount must be provided for this operation");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User not found"));
        user.setBalance(balanceDto.getAmount());
        userRepository.save(user);
    }

}
