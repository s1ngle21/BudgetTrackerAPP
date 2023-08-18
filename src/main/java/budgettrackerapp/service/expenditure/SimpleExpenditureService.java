package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import budgettrackerapp.exeptions.UserDoesNotExistException;
import budgettrackerapp.mapper.CategoryMapper;
import budgettrackerapp.repository.expenditure.ExpenditureRepository;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.category.CategoryService;
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
        User user = userRepository.findById(categoryDto.getUserId()).orElseThrow(() -> new UserDoesNotExistException("User not found"));
        Expenditure expenditure = expenditureMapper.mapToEntity(expenditureDto);

        BigDecimal amount = expenditure.getAmount();
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));

        categoryService.updateBalance(categoryDto.getAmount().add(amount), categoryDto);

        Expenditure savedExpenditure = expenditureRepository.save(expenditure);
        return expenditureMapper.mapToDto(savedExpenditure);
    }

    @Override
    public void delete(Long categoryId, Long expenditureId, Long userId) {
        Objects.requireNonNull(categoryId, "categoryId id must not be null!");
        Objects.requireNonNull(expenditureId, "expenditureId must not be null!");
        Objects.requireNonNull(expenditureId, "userId must not be null!");
        Expenditure expenditure = expenditureRepository.findById(expenditureId).orElseThrow(() ->
                new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure not found"));


        CategoryDTO categoryDto = categoryService.getById(categoryId, userId);


        expenditureMapper.mapToEntity(categoryDto.getExpenditures()
                .stream()
                .filter(expenditureDto -> expenditureDto.getId().equals(expenditureId))
                .findFirst()
                .orElseThrow(() -> new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure does not exist in this category: " +
                        categoryDto.getName())));

        categoryService.updateBalance(categoryDto.getAmount().subtract(expenditure.getAmount()), categoryDto);
        User user = userRepository.findById(categoryDto.getUserId()).get();
        user.setBalance(user.getBalance().add(expenditure.getAmount()));
        userRepository.save(user);
        expenditureRepository.deleteById(expenditureId);
    }

    @Override
    public void moveToAnotherCategory(Long expenditureId, Long categoryId, Long userId) {
        Objects.requireNonNull(categoryId, "categoryId must not be null!");
        Objects.requireNonNull(expenditureId, "expenditureId must not be null!");
        Objects.requireNonNull(userId, "userId must not be null!");

        Expenditure expenditure = expenditureRepository.findById(expenditureId).orElseThrow(() ->
                new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure not found"));

        CategoryDTO categoryDto = categoryService.getById(categoryId, userId);

        expenditure.setCategory(categoryMapper.mapToEntity(categoryDto));
        expenditureRepository.save(expenditure);
    }

}
