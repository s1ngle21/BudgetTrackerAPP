package budgettrackerapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private BigDecimal balance;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;


    public void subtractFromBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }


}
