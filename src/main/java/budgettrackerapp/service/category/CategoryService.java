package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.entity.Category;

import java.math.BigDecimal;
import java.util.List;


public interface CategoryService {

    CategoryDTO create(Category category, Long userId);

    CategoryDTO getById(Long id);

    void updateBalance(BigDecimal amount, Category category);

    List<CategoryDTO> getAll();

    void delete(Long id);
}
