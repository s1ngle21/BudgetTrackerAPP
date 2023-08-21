package budgettrackerapp.dto;


import budgettrackerapp.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private BigDecimal balance;
    private List<CategoryDTO> categories;
    private List<Role> roles;
}
