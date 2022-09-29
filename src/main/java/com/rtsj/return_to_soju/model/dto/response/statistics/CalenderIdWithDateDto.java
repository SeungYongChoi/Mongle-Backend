package com.rtsj.return_to_soju.model.dto.response.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalenderIdWithDateDto {

    private Long calenderId;
    private LocalDate date;
}
