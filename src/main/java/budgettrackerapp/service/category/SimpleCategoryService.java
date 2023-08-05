package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.dto.UserDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.CategoryDoesNotExistException;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.mapper.UserMapper;
import budgettrackerapp.repository.category.CategoryRepository;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private UserRepository userRepository;

    @Override
    public CategoryDTO create(CategoryDTO categoryDto, Long userId) {
        Objects.requireNonNull(categoryDto.getName(), "Please, at least provide name for your category!"); //+++
        Objects.requireNonNull(userId, "User Id must be provided to create category!");
        User user = userRepository.findById(userId).get();
        categoryDto.setUserId(user.getId());

        Category category = categoryMapper.mapToEntity(categoryDto);
        category.setUser(user);
        category.setAmount(BigDecimal.ZERO);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.mapToDto(savedCategory);
    }

    @Override
    public void updateBalance(BigDecimal amount, CategoryDTO categoryDto) {
        Category category = categoryMapper.mapToEntity(categoryDto);
        category.setAmount(amount);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id, Long userId) {
        Objects.requireNonNull(id, "Id must be provided for this operation!");
        Objects.requireNonNull(id, "UserId must be provided for this operation!");

        CategoryDTO categoryDto = categoryMapper.mapToDto(categoryRepository.findByIdAndUserId(id, userId));
        if (categoryDto == null) {
            throw new CategoryDoesNotExistException("Category does not exist!");
        }
        return categoryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAll(Long userId, int pageNumber, int pageSize) {
        List<CategoryDTO> categoriesDTO = categoryMapper.mapToDto(categoryRepository.findAllByUserId(userId));

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        Page<CategoryDTO> categoryPage = new PageImpl<>(categoriesDTO, pageable, categoriesDTO.size());

        if (!categoryPage.hasContent()) {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return categoryPage;
    }

    @Override
    public void delete(Long id, Long userId) {
        Objects.requireNonNull(id, "Id must be provided to delete category operation!");
        Objects.requireNonNull(id, "User Id must be provided to delete category operation!");
        CategoryDTO categoryDto = this.getById(id, userId);

        User user = userRepository.findById(categoryDto.getUserId()).get();
        user.setBalance(user.getBalance().add(categoryDto.getAmount()));
        userRepository.save(user);

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long categoryId, Long userId, int year, Month month) {
        Objects.requireNonNull(categoryId, "Category Id must be provided for this operation!");
        Objects.requireNonNull(categoryId, "User Id must be provided for this operation!");

        CategoryDTO categoryDto = this.getById(categoryId, userId);

        List<ExpenditureDTO> expendituresDto = categoryDto.getExpenditures();

        expendituresDto
                .stream()
                .filter(expenditure ->
                        expenditure.getCreatedAt().getMonth() == month &&
                                expenditure.getCreatedAt().getYear() == year)
                .collect(Collectors.toList());

        BigDecimal amount = expendituresDto
                .stream()
                .map(ExpenditureDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        categoryDto.setAmount(amount);
        return categoryDto;
    }

}
