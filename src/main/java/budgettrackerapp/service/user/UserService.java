package budgettrackerapp.service.user;


import budgettrackerapp.entity.User;

import java.math.BigDecimal;


public interface UserService {
    User findById(Long id);

    User save(User user);

    void setBalance(Long id, BigDecimal amount);
}
