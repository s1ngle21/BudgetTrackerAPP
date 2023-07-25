package budgettrackerapp.controller;

import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.entity.Category;
import budgettrackerapp.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category, @PathVariable Long userId) {
        return ResponseEntity
                .ok(categoryService.create(category, userId));
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity
                .ok(categoryService.getById(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity
                .ok(categoryService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity
                .ok("Category has been deleted");
    }


}
