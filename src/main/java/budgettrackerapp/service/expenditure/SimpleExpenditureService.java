package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.mapper.UserMapper;
import budgettrackerapp.repository.expenditure.ExpenditureRepository;
import budgettrackerapp.repository.user.UserRepository;
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
    private UserRepository userRepository;
    private ExpenditureMapper expenditureMapper;
    private CategoryMapper categoryMapper;

    @Override
    public ExpenditureDTO createAndAddToCategory(ExpenditureDTO expenditureDto, Long categoryId, Long userId) {
        Objects.requireNonNull(expenditureDto.getAmount(), "Please, amount for your expenditure!");
        Objects.requireNonNull(categoryId, "Id must be provided for this operation!");
        Objects.requireNonNull(userId, "Id must be provided for this operation!");
        CategoryDTO categoryDto = categoryService.getById(categoryId, userId);
        User user = userRepository.findById(categoryDto.getUserId()).get();
        Expenditure expenditure = expenditureMapper.mapToEntity(expenditureDto);

        BigDecimal amountOfExpenditure = expenditure.getAmount();
        user.setBalance((user.getBalance()).subtract(amountOfExpenditure));
        userRepository.save(user);

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));

        categoryService.updateBalance(categoryDto.getAmount().add(amountOfExpenditure), categoryDto);

        Expenditure savedExpenditure = expenditureRepository.save(expenditure);
        return expenditureMapper.mapToDto(savedExpenditure);
    }

    @Override
    public void delete(Long categoryId, Long expenditureId, Long userId) {
        Objects.requireNonNull(categoryId, "Category id must be provided for this operation!");
        Objects.requireNonNull(expenditureId, "Expenditure id must be provided for this operation!");

        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        Objects.requireNonNull(expenditure, "Can not find expenditure");

        CategoryDTO categoryDto = categoryService.getById(categoryId, userId);
        Objects.requireNonNull(expenditure, "Can not find category");

        expenditureMapper.mapToEntity(categoryDto.getExpenditures()
                .stream()
                .filter(expenditureDto -> expenditureDto.getId().equals(expenditureId))
                .findFirst()
                .orElseThrow(() -> new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure does not exist in this category: " +
                        categoryDto.getName()))); //+++++++

        categoryService.updateBalance(categoryDto.getAmount().subtract(expenditure.getAmount()), categoryDto);
        User user = userRepository.findById(categoryDto.getUserId()).get();
        user.setBalance(user.getBalance().add(expenditure.getAmount()));
        userRepository.save(user);
        expenditureRepository.deleteById(expenditureId);
    }

    @Override
    public void moveToAnotherCategory(Long expenditureId, Long categoryId, Long userId) {
        Objects.requireNonNull(categoryId, "Category id must be provided for this operation!");
        Objects.requireNonNull(expenditureId, "Expenditure id must be provided for this operation!");
        Objects.requireNonNull(userId, "User id must be provided for this operation!");

        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        Objects.requireNonNull(expenditure, "Can not find expenditure");

        CategoryDTO categoryDto = categoryService.getById(categoryId, userId);
        Objects.requireNonNull(expenditure, "Can not find category");

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));
        expenditureRepository.save(expenditure);
    }

}
