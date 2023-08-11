package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.CategoryDateInfoDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.Month;


public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDto, Long userId);

    CategoryDTO getById(Long id, Long userId);

    void updateBalance(BigDecimal amount, CategoryDTO categoryDto);

    Page<CategoryDTO> getAll(Long userId, int pageNumber, int pageSize);

    void delete(Long id, Long userId);

    CategoryDTO getByIdAndSortedByDate(CategoryDateInfoDTO categoryDateInfoDto);
}
