package com.example.project3.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillDayOfMonth {
private Long billCount;
private Long day;
}
