package budgettrackerapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

    @OneToMany(mappedBy = "category")
    private List<Expenditure> expenditures;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
