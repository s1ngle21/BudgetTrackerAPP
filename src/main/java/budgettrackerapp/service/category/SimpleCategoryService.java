package budgettrackerapp.service.category;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.CategoryDateInfoDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.CategoryDoesNotExistException;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.repository.category.CategoryRepository;
import budgettrackerapp.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private UserRepository userRepository;

    @Override
    public CategoryDTO create(CategoryDTO categoryDto, Long userId) {
        Objects.requireNonNull(categoryDto.getName(), "Please, provide a name for your category!");
        Objects.requireNonNull(userId, "userId must be provided to create category!");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User not found!"));
        categoryDto.setUserId(user.getId());

        Category category = categoryMapper.mapToEntity(categoryDto);
        category.setUser(user);
        category.setAmount(BigDecimal.ZERO);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.mapToDto(savedCategory);
    }

    @Override
    public void updateBalance(BigDecimal amount, CategoryDTO categoryDto) {
        Category category = categoryRepository.findByIdAndUserId(categoryDto.getId(), categoryDto.getUserId()).orElseThrow(() ->
                new CategoryDoesNotExistException("Category not found"));
        category.setAmount(amount);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long categoryId, Long userId) {
        Objects.requireNonNull(categoryId, "Wrong parameter passed - pass categoryId!");
        Objects.requireNonNull(userId, "Wrong parameter passed - pass userId!");

        CategoryDTO categoryDto = categoryMapper.mapToDto(categoryRepository.findByIdAndUserId(categoryId, userId).orElseThrow(() ->
                new CategoryDoesNotExistException("Category not found")));
        if (categoryDto == null) {
            throw new CategoryDoesNotExistException(String.format("Category: [%s] does not exist!", categoryDto.getName()));
        }
        return categoryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAll(Long userId, int pageNumber, int pageSize) {
        List<CategoryDTO> categoriesDTO = categoryMapper.mapToDto(categoryRepository.findAllByUserId(userId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return new PageImpl<>(categoriesDTO, pageable, categoriesDTO.size());
    }

    @Override
    public void delete(Long categoryId, Long userId) {
        Objects.requireNonNull(categoryId, "Wrong parameter passed, pass categoryId!");
        Objects.requireNonNull(userId, "Wrong parameter passed, pass userId!");

        User user = userRepository.findById(userId).orElseThrow(() -> new UserDoesNotExistException("User not found"));
        userRepository.save(user);

        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getByIdAndSortedByDate(CategoryDateInfoDTO categoryDateInfoDto) {
        Objects.requireNonNull(categoryDateInfoDto.getCategoryId(), "categoryId must be provided for this operation!");
        Objects.requireNonNull(categoryDateInfoDto.getUserId(), "userId must be provided for this operation!");

        CategoryDTO categoryDto = this.getById(categoryDateInfoDto.getCategoryId(), categoryDateInfoDto.getUserId());

        List<ExpenditureDTO> expendituresDto = categoryDto.getExpenditures();

        List<ExpenditureDTO> sortedExpenditures = expendituresDto
                .stream()
                .filter(expenditure ->
                        expenditure.getCreatedAt().getMonth() == categoryDateInfoDto.getMonth() &&
                                expenditure.getCreatedAt().getYear() == categoryDateInfoDto.getYear())
                .toList();

        BigDecimal amount = sortedExpenditures
                .stream()
                .map(ExpenditureDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        categoryDto.setAmount(amount);
        return categoryDto;
    }

}
