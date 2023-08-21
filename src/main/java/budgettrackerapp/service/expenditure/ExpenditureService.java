package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;

public interface ExpenditureService {

    ExpenditureDTO createAndAddToCategory(ExpenditureDTO expenditureDto, Long categoryId, Long userId);

    void delete(Long categoryId, Long expenditureId, Long userId);

    void moveToAnotherCategory(Long expenditureId, Long categoryId, Long userId);
}
