package budgettrackerapp.mapper;

import budgettrackerapp.dto.UserInfoDTO;
import budgettrackerapp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserInfoMapper implements EntityMapper<User, UserInfoDTO>{
    private CategoryMapper categoryMapper;
    @Override
    public UserInfoDTO mapToDto(User user) {
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setBalance(user.getBalance());
        userInfo.setCategories(categoryMapper.mapToDto(user.getCategories()));
        return userInfo;
    }

    @Override
    public User mapToEntity(UserInfoDTO userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        user.setEmail(userInfo.getEmail());
        user.setBalance(userInfo.getBalance());
        return user;
    }

    @Override
    public List<UserInfoDTO> mapToDto(List<User> users) {
        return users
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
