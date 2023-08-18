package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private BigDecimal amount;
    private List<ExpenditureDTO> expenditures = new ArrayList<>();
    private Long userId;

    public CategoryDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }


}
