package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;

public interface ExpenditureService {

    ExpenditureDTO createAndAddToCategory(Expenditure expenditure, Long categoryId);

    void delete(Long expenditureId, Long categoryId);

    void moveToAnotherCategory(Long expenditureId, Long categoryId);
}
