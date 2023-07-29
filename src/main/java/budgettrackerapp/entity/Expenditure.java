package budgettrackerapp.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenditures")
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Expenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
