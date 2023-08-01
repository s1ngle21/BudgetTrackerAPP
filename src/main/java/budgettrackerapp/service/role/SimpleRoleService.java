package budgettrackerapp.service.role;

import budgettrackerapp.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class SimpleRoleService implements RoleService {
    @Override
    public Role createRoleUser(String role) {
        Role roleUser = new Role();
        roleUser.setRole(role);
        return roleUser;
    }
}
