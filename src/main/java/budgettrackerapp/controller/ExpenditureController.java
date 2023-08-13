package budgettrackerapp.controller;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.dto.ExpenditureMoveRequestDTO;
import budgettrackerapp.service.expenditure.ExpenditureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenditures")
@AllArgsConstructor
public class ExpenditureController {

    private ExpenditureService expenditureService;

    @PostMapping
    public ResponseEntity<ExpenditureDTO> create(@RequestBody ExpenditureDTO expenditureDto) {
        return ResponseEntity
                .ok(expenditureService.createAndAddToCategory(expenditureDto, expenditureDto.getCategoryId(), expenditureDto.getUserId()));
    }

    @DeleteMapping("/{expenditureId}/users/{userId}/categories/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId,
                                         @PathVariable Long expenditureId,
                                         @PathVariable Long userId) {
        expenditureService.delete(categoryId, expenditureId, userId);
        return ResponseEntity
                .ok("Expenditure has been deleted");
    }

    @PostMapping("/move")
    public ResponseEntity<String> moveToAnotherCategory(@RequestBody ExpenditureMoveRequestDTO expenditureMoveRequestDto) {
        expenditureService.moveToAnotherCategory(expenditureMoveRequestDto.getExpenditureId(),
                                                    expenditureMoveRequestDto.getCategoryDestinationId(),
                                                    expenditureMoveRequestDto.getUserId());
        return ResponseEntity
                .ok("Expenditure has been moved to another category");
    }
}
