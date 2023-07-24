package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class ExpenditureDTO {
    private Long id;
    private LocalDate createdAt;
    private BigDecimal amount;
    private String comment;
}
