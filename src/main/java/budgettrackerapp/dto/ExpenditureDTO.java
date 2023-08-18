package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ExpenditureDTO {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private String comment;
}
