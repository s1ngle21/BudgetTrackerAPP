package budgettrackerapp.controller;

import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.entity.Expenditure;
import budgettrackerapp.service.expenditure.ExpenditureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class ExpenditureController {

    private ExpenditureService expenditureService;

    @PostMapping("categories/{categoryId}/expenditures")
    public ResponseEntity<ExpenditureDTO> create(@RequestBody Expenditure expenditure, @PathVariable Long categoryId) {
        return ResponseEntity
                .ok(expenditureService.createAndAddToCategory(expenditure, categoryId));
    }

    @DeleteMapping("categories/{categoryId}/expenditures/{expenditureId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId, @PathVariable Long expenditureId) {
        expenditureService.delete(categoryId, expenditureId);
        return ResponseEntity
                .ok("Expenditure has been deleted");
    }

    @PostMapping("categories/{categoryId}/expenditures/{expenditureId}")
    public ResponseEntity<String> moveToAnotherCategory(@PathVariable Long categoryId, @PathVariable Long expenditureId) {
        expenditureService.moveToAnotherCategory(expenditureId, categoryId);
        return ResponseEntity
                .ok("Expenditure has been moved to another category");
    }
}
