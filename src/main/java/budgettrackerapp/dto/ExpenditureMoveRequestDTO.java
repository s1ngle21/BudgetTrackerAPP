package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExpenditureMoveRequestDTO {
    private Long categoryDestinationId;
    private Long expenditureId;
    private Long userId;
}
