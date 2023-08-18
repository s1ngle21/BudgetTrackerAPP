package budgettrackerapp.repository.expenditure;

import budgettrackerapp.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

}
