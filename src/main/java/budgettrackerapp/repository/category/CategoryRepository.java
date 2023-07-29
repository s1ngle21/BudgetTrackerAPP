package budgettrackerapp.repository.category;


import budgettrackerapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(Long userId);

    Category findByIdAndUserId(Long id, Long userId);
}
