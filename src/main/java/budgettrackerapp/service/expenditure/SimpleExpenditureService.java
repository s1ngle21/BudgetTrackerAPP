package budgettrackerapp.service.expenditure;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.entity.User;
import budgettrackerapp.exeptions.ExpenditureInSpecificCategoryDoesNotExistException;
import budgettrackerapp.repository.expenditure.ExpenditureRepository;
import budgettrackerapp.service.category.CategoryService;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class SimpleExpenditureService implements ExpenditureService {

    private ExpenditureRepository expenditureRepository;
    private CategoryService categoryService;
    private UserService userService;

    @Override
    public ExpenditureDTO createAndAddToCategory(Expenditure expenditure, Long categoryId) {
        CategoryDTO categoryDto = categoryService.getById(categoryId);
        User user = userService.findById(categoryDto.getUserId());

        BigDecimal amountOfExpenditure = expenditure.getAmount();
        user.setBalance((user.getBalance()).subtract(amountOfExpenditure));
        userService.save(user);

        expenditure.setCategory(mapToCategory(categoryDto));

        categoryService.updateBalance(categoryDto.getAmount().add(amountOfExpenditure), mapToCategory(categoryDto));

        Expenditure savedExpenditure = expenditureRepository.save(expenditure);
        return mapToExpenditureDto(savedExpenditure);
    }

    @Override
    public void delete(Long categoryId, Long expenditureId) {
        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        CategoryDTO categoryDto = categoryService.getById(categoryId);

        mapToExpenditure(categoryDto.getExpenditures()
                .stream()
                .filter(expenditureDto -> expenditureDto.getId().equals(expenditureId))
                .findFirst()
                .orElseThrow(() -> new ExpenditureInSpecificCategoryDoesNotExistException("Expenditure does not exist in this category: " +
                        categoryDto.getName())));

        categoryService.updateBalance(categoryDto.getAmount().subtract(expenditure.getAmount()), mapToCategory(categoryDto));
        User user = userService.findById(categoryDto.getUserId());
        user.setBalance(user.getBalance().add(expenditure.getAmount()));
        expenditureRepository.deleteById(expenditureId);
    }

    @Override
    public void moveToAnotherCategory(Long expenditureId, Long categoryId) {
        Expenditure expenditure = expenditureRepository.findById(expenditureId).get();
        CategoryDTO categoryDto = categoryService.getById(categoryId);
        expenditure.setCategory(mapToCategory(categoryDto));
        expenditureRepository.save(expenditure);
    }

    private ExpenditureDTO mapToExpenditureDto(Expenditure expenditure) {
        ExpenditureDTO expenditureDto = new ExpenditureDTO();
        expenditureDto.setId(expenditure.getId());
        expenditureDto.setCreatedAt(expenditure.getCreatedAt());
        expenditureDto.setAmount(expenditure.getAmount());
        expenditureDto.setComment(expenditure.getComment());
        return expenditureDto;
    }

    private Category mapToCategory(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        User user = userService.findById(categoryDto.getUserId());
        category.setUser(user);
        return category;
    }

    private Expenditure mapToExpenditure(ExpenditureDTO expenditureDto) {
        Expenditure expenditure = new Expenditure();
        expenditure.setId(expenditureDto.getId());
        expenditure.setAmount(expenditureDto.getAmount());
        expenditure.setComment(expenditureDto.getComment());
        return expenditure;
    }


}
