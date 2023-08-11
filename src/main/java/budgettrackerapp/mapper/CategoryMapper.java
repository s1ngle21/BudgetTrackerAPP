package budgettrackerapp.mapper;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.entity.User;
import budgettrackerapp.repository.user.UserRepository;
import budgettrackerapp.service.user.UserDetailsServiceImpl;
import budgettrackerapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryMapper implements EntityMapper<Category, CategoryDTO> {

    private ExpenditureMapper expenditureMapper;
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public CategoryDTO mapToDto(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        if (category.getUser() != null) {
            categoryDto.setUserId(category.getUser().getId());
        }
        categoryDto.setAmount(category.getAmount());
        categoryDto.setExpenditures(expenditureMapper.mapToDto(category.getExpenditures()));
        return categoryDto;
    }

    @Override
    public Category mapToEntity(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setAmount(categoryDto.getAmount());
        User user = userDetailsService.getById(categoryDto.getUserId());
        category.setUser(user);
        return category;
    }

    @Override
    public List<CategoryDTO> mapToDto(List<Category> categories) {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
