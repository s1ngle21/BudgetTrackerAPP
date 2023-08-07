package budgettrackerapp;


import budgettrackerapp.controller.ExpenditureController;
import budgettrackerapp.dto.ExpenditureDTO;
import budgettrackerapp.service.expenditure.ExpenditureService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenditureControllerIntegrationTest {

    @InjectMocks
    private ExpenditureController expenditureController;

    @Mock
    private ExpenditureService expenditureService;

    private MockMvc mockMvc;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenditureController).build();
    }


    @Test
    public void whenPerformCreateExpenditureOperationThenExpenditureMustBeCreated() throws Exception {
        Long userId = 1L;
        Long categoryId = 1L;
        ExpenditureDTO expenditureDto = new ExpenditureDTO();
        expenditureDto.setAmount(BigDecimal.valueOf(200));
        expenditureDto.setComment("Test Comment");

        ExpenditureDTO responseDto = new ExpenditureDTO();
        responseDto.setId(1L);
        responseDto.setAmount(expenditureDto.getAmount());
        responseDto.setComment(expenditureDto.getComment());

        when(expenditureService.createAndAddToCategory(any(ExpenditureDTO.class), eq(categoryId), eq(userId)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/expenditures/users/{userId}/categories/{categoryId}", categoryId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(expenditureDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(200))
                .andExpect(jsonPath("$.comment").value("Test Comment"));
    }


    @Test
    public void testDeleteExpenditure() throws Exception {
        Long userId = 1L;
        Long categoryId = 1L;
        Long expenditureId = 2L;

        mockMvc.perform(delete("/expenditures/{expenditureId}/users/{userId}/categories/{categoryId}",
                        expenditureId, userId, categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Expenditure has been deleted"));
    }

    @Test
    public void testMoveToAnotherCategory() throws Exception {
        Long userId = 1L;
        Long expenditureId = 1L;
        Long categoryDestinationId = 2L;

        mockMvc.perform(post("/expenditures/{expenditureId}/users/{userId}/categories/{categoryDestinationId}",
                        expenditureId, userId, categoryDestinationId))
                .andExpect(status().isOk())
                .andExpect(content().string("Expenditure has been moved to another category"));
    }

    private static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
