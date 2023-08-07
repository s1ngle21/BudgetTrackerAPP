package budgettrackerapp.controller;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDto, @PathVariable Long userId) {
        return ResponseEntity
                .ok(categoryService.create(categoryDto, userId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<CategoryDTO>> getAll(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity
                .ok(categoryService.getAll(userId, pageNumber, pageSize));
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @PathVariable Long userId) {
        categoryService.delete(id, userId);
        return ResponseEntity
                .ok("Category has been deleted");
    }

    @GetMapping("/{categoryId}/users/{userId}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long categoryId,
                                               @PathVariable Long userId,
                                               @RequestParam(defaultValue = "2023") int year,
                                               @RequestParam(required = false) Month month) {
        return ResponseEntity
                .ok(categoryService.getById(categoryId, userId, year, month));
    }


}
