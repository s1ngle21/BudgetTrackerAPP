package budgettrackerapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Month;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDateInfoDTO {
    private Long categoryId;
    private Long userId;
    private int year;
    private Month month;
}
