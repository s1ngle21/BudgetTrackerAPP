package budgettrackerapp.mapper;

import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper implements EntityMapper<User, UserDTO> {

    private CategoryMapper categoryMapper;
    @Override
    public UserDTO mapToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setBalance(user.getBalance());
        userDto.setPassword(user.getPassword());
        userDto.setCategories(categoryMapper.mapToDto(user.getCategories()));
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    @Override
    public User mapToEntity(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setBalance(userDto.getBalance());
        return user;
    }

    @Override
    public List<UserDTO> mapToDto(List<User> users) {
        return users
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
