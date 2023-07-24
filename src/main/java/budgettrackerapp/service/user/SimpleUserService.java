package budgettrackerapp.service.user;

import budgettrackerapp.entity.User;
import budgettrackerapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@Transactional
@AllArgsConstructor
public class SimpleUserService implements UserService{

    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void setBalance(Long id, BigDecimal amount) {
        User user = findById(id);
        user.setBalance(amount);
        save(user);
    }

}
