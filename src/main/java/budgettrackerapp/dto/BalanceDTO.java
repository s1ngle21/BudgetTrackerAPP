package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class BalanceDTO {
    private BigDecimal amount;
    private Long UserId;
}
