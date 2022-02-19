package com.example.project3.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChartResponse {
private Long month;
private Long importTotal;
private Long soldTotal;
private Long billCount;
}
