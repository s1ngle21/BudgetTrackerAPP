package budgettrackerapp.mapper;

import budgettrackerapp.dto.UserINFO;
import budgettrackerapp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserInfoMapper implements EntityMapper<User, UserINFO>{
    private CategoryMapper categoryMapper;
    @Override
    public UserINFO mapToDto(User user) {
        UserINFO userInfo = new UserINFO();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setBalance(user.getBalance());
        userInfo.setCategories(categoryMapper.mapToDto(user.getCategories()));
        return userInfo;
    }

    @Override
    public User mapToEntity(UserINFO userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        user.setEmail(userInfo.getEmail());
        user.setBalance(userInfo.getBalance());
        return user;
    }

    @Override
    public List<UserINFO> mapToDto(List<User> users) {
        Objects.requireNonNull(users, "Can not find users");
        return users
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
