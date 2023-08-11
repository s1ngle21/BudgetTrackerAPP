package budgettrackerapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", scale = 3)
    private BigDecimal amount;

    @OneToMany(mappedBy = "category")
    private List<Expenditure> expenditures;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
