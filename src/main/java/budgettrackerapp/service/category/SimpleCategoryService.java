package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.repository.category.CategoryRepository;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    private UserService userService;

    @Override
    public CategoryDTO create(Category category, Long userId) {
        User user = userService.findById(userId);
        category.setUser(user);
        category.setAmount(BigDecimal.ZERO);

        Category savedCategory = categoryRepository.save(category);

        return mapToCategoryDTO(savedCategory);
    }

    @Override
    public void updateBalance(BigDecimal amount, Category category) {
        category.setAmount(amount);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        return mapToCategoryDTO(categoryRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories
                .stream()
                .map(this::mapToCategoryDTO)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToCategoryDTO(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setUserId(category.getUser().getId());
        categoryDto.setAmount(category.getAmount());
        categoryDto.setExpenditures(mapExpendituresToDto(category.getExpenditures()));
        return categoryDto;
    }

    private List<ExpenditureDTO> mapExpendituresToDto(List<Expenditure> expenditures) {
        if (expenditures == null) {
            return new ArrayList<>();
        }
        return expenditures
                .stream()
                .map(this::mapExpenditureToDto)
                .collect(Collectors.toList());

    }

    private ExpenditureDTO mapExpenditureToDto(Expenditure expenditure) {
        ExpenditureDTO expenditureDto = new ExpenditureDTO();
        expenditureDto.setId(expenditure.getId());
        expenditureDto.setCreatedAt(expenditure.getCreatedAt());
        expenditureDto.setComment(expenditure.getComment());
        expenditureDto.setAmount(expenditure.getAmount());
        return expenditureDto;
    }

}
