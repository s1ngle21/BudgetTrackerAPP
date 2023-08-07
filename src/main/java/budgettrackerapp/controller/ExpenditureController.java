package budgettrackerapp.controller;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.service.expenditure.ExpenditureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenditures")
@AllArgsConstructor
public class ExpenditureController {

    private ExpenditureService expenditureService;

    @PostMapping("/users/{userId}/categories/{categoryId}")
    public ResponseEntity<ExpenditureDTO> create(@RequestBody ExpenditureDTO expenditureDto,
                                                 @PathVariable Long userId,
                                                 @PathVariable Long categoryId) {
        return ResponseEntity
                .ok(expenditureService.createAndAddToCategory(expenditureDto, categoryId, userId));
    }

    @DeleteMapping("/{expenditureId}/users/{userId}/categories/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId,
                                         @PathVariable Long expenditureId,
                                         @PathVariable Long userId) {
        expenditureService.delete(categoryId, expenditureId, userId);
        return ResponseEntity
                .ok("Expenditure has been deleted");
    }

    @PostMapping("/{expenditureId}/users/{userId}/categories/{categoryDestinationId}")
    public ResponseEntity<String> moveToAnotherCategory(@PathVariable Long categoryDestinationId,
                                                        @PathVariable Long expenditureId,
                                                        @PathVariable Long userId) {
        expenditureService.moveToAnotherCategory(expenditureId, categoryDestinationId, userId);
        return ResponseEntity
                .ok("Expenditure has been moved to another category");
    }
}
