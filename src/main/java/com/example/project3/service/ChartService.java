package com.example.project3.service;

import com.example.project3.model.dto.BillDayOfMonth;
import com.example.project3.model.dto.ChartResponse;
import java.util.List;

public interface ChartService {

  List<ChartResponse> linechart();
  List<BillDayOfMonth> daychart();

}
