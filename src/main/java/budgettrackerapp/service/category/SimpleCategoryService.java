package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.CategoryDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.mapper.ExpenditureMapper;
import budgettrackerapp.repository.category.CategoryRepository;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;
    private UserService userService;
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO create(Category category, Long userId) {
        Objects.requireNonNull(category);
        User user = userService.findById(userId);
        category.setUser(user);
        category.setAmount(BigDecimal.ZERO);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.mapToDto(savedCategory);
    }

    @Override
    public void updateBalance(BigDecimal amount, Category category) {
        category.setAmount(amount);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        CategoryDTO categoryDto = categoryMapper.mapToDto(categoryRepository.findById(id).get());
        if (categoryDto == null) {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return categoryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories == null || categories.size() == 0) {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return categoryMapper.mapToDto(categories);

    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        categoryRepository.deleteById(id);
    }

}
