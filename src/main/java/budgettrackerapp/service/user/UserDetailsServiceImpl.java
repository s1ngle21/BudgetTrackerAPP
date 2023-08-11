package budgettrackerapp.service.user;

import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).get();
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new UserDoesNotExistException("Can not find user");
        }
        return user;
    }

}
