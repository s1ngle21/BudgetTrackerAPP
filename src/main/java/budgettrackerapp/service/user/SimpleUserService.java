package budgettrackerapp.service.user;

import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;


@Service
@Transactional
@AllArgsConstructor
public class SimpleUserService implements UserService{

    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return user;
    }

    public User save(User user) {
        Objects.requireNonNull(user);
        return userRepository.save(user);
    }

    @Override
    public void setBalance(Long id, BigDecimal amount) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        Objects.requireNonNull(amount, "Please enter amount");
        User user = findById(id);
        if (user == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        user.setBalance(amount);
        save(user);
    }

}
