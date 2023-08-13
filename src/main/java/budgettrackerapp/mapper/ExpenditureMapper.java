package budgettrackerapp.mapper;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.service.category.CategoryService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ExpenditureMapper implements EntityMapper<Expenditure, ExpenditureDTO> {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    @Override
    public ExpenditureDTO mapToDto(Expenditure expenditure) {
        ExpenditureDTO expenditureDto = new ExpenditureDTO();
        expenditureDto.setId(expenditure.getId());
        expenditureDto.setCreatedAt(expenditure.getCreatedAt());
        expenditureDto.setAmount(expenditure.getAmount());
        expenditureDto.setComment(expenditure.getComment());
        expenditureDto.setCategoryId(expenditure.getCategory().getId());
        expenditureDto.setUserId(expenditure.getCategory().getUser().getId());
        return expenditureDto;
    }

    @Override
    public Expenditure mapToEntity(ExpenditureDTO expenditureDto) {
        Expenditure expenditure = new Expenditure();
        expenditure.setId(expenditureDto.getId());
        expenditure.setAmount(expenditureDto.getAmount());
        expenditure.setComment(expenditureDto.getComment());
//        expenditure.setCategory(categoryMapper.mapToEntity(categoryService.getById(expenditureDto.getCategoryId(), expenditureDto.getUserId())));
        return expenditure;
    }

    @Override
    public List<ExpenditureDTO> mapToDto(List<Expenditure> expenditures) {
        if (expenditures == null) {
            return new ArrayList<>();
        }
        return expenditures
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
