package budgettrackerapp;

import budgettrackerapp.controller.CategoryController;
import budgettrackerapp.dto.CategoryDTO;
import budgettrackerapp.service.category.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void whenCreateCategoryThenCategoryMustBeCreated() throws Exception {
        Long userId = 1L;
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Products");
        categoryDto.setUserId(userId);

        when(categoryService.create(any(CategoryDTO.class), eq(userId)))
                .thenReturn(categoryDto);


        MvcResult mvcResult = mockMvc.perform(post("/categories/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(categoryDto)))
                .andExpect(status().isOk())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();
        CategoryDTO createdCategory = new ObjectMapper().readValue(response, CategoryDTO.class);
        assertEquals(categoryDto.getName(), createdCategory.getName());
        assertEquals(categoryDto.getUserId(), createdCategory.getUserId());
    }

    @Test
    public void whenPerformGetAllOperationAllCategoriesMustBeReturned() throws Exception {
        Long userId = 1L;
        int pageNumber = 0;
        int pageSize = 2;
        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(new CategoryDTO("Products", userId));
        categories.add(new CategoryDTO("Car", userId));

        Page<CategoryDTO> page = new PageImpl<>(categories);

        when(categoryService.getAll(eq(userId), eq(pageNumber), eq(pageSize)))
                .thenReturn(page);

        mockMvc.perform(get("/categories/users/{userId}", userId)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value("Products"))
                .andExpect(jsonPath("$.content[1].name").value("Car"));
    }

    @Test
    public void whenPerformDeleteOperationThenCategoryMustBeDeleted() throws Exception {
        Long categoryId = 1L;
        Long userId = 1L;

        mockMvc.perform(delete("/categories/{id}/users/{userId}", categoryId, userId))
                .andExpect(status().isOk())
                .andExpect(content().string("Category has been deleted"));
    }


    @Test
    public void whenPerformGetByIdThenCorrectCategoryMustBeReturned() throws Exception {
        Long categoryId = 1L;
        Long userId = 1L;
        int year = 2023;
        Month month = Month.JANUARY;
        CategoryDTO categoryDto = new CategoryDTO("Products", userId);

        when(categoryService.getById(eq(categoryId), eq(userId), eq(year), eq(month)))
                .thenReturn(categoryDto);


        mockMvc.perform(get("/categories/{categoryId}/users/{userId}", categoryId, userId)
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Products"));
    }



    private static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
