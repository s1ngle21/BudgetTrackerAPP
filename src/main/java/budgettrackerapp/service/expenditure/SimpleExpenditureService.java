package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.repository.expenditure.ExpenditureRepository;
import budgettrackerapp.service.category.CategoryService;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import budgettrackerapp.mapper.ExpenditureMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class SimpleExpenditureService implements ExpenditureService {

    private ExpenditureRepository expenditureRepository;
    private CategoryService categoryService;
    private UserService userService;
    private ExpenditureMapper expenditureMapper;
    private CategoryMapper categoryMapper;

    @Override
    public ExpenditureDTO createAndAddToCategory(Expenditure expenditure, Long categoryId) {
        Objects.requireNonNull(expenditure, "Please, at least provide name and amount for your expenditure");
        Objects.requireNonNull(expenditure, "Id must be provided for this operation");
        CategoryDTO categoryDto = categoryService.getById(categoryId);
        User user = userService.findById(categoryDto.getUserId());

        BigDecimal amountOfExpenditure = expenditure.getAmount();
        user.setBalance((user.getBalance()).subtract(amountOfExpenditure));
        userService.save(user);

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));

        categoryService.updateBalance(categoryDto.getAmount().add(amountOfExpenditure), categoryMapper.mapToEntity(categoryDto));

        Expenditure savedExpenditure = expenditureRepository.save(expenditure);
        return expenditureMapper.mapToDto(savedExpenditure);
    }

    @Override
    public void delete(Long categoryId, Long expenditureId) {
        Objects.requireNonNull(categoryId, "Category id must be provided for this operation");
        Objects.requireNonNull(expenditureId, "Expenditure id must be provided for this operation");

        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        Objects.requireNonNull(expenditure, "Can not find expenditure");

        CategoryDTO categoryDto = categoryService.getById(categoryId);
        Objects.requireNonNull(expenditure, "Can not find category");

        expenditureMapper.mapToEntity(categoryDto.getExpenditures()
                .stream()
                .filter(expenditureDto -> expenditureDto.getId().equals(expenditureId))
                .findFirst()
                .orElseThrow(() -> new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure does not exist in this category: " +
                        categoryDto.getName())));

        categoryService.updateBalance(categoryDto.getAmount().subtract(expenditure.getAmount()), categoryMapper.mapToEntity(categoryDto));
        User user = userService.findById(categoryDto.getUserId());
        user.setBalance(user.getBalance().add(expenditure.getAmount()));
        expenditureRepository.deleteById(expenditureId);
    }

    @Override
    public void moveToAnotherCategory(Long expenditureId, Long categoryId) {
        Objects.requireNonNull(categoryId, "Category id must be provided for this operation");
        Objects.requireNonNull(expenditureId, "Expenditure id must be provided for this operation");

        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        Objects.requireNonNull(expenditure, "Can not find expenditure");

        CategoryDTO categoryDto = categoryService.getById(categoryId);
        Objects.requireNonNull(expenditure, "Can not find category");

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));
        expenditureRepository.save(expenditure);
    }

}
