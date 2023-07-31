package budgettrackerapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Role implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}
