package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.model.dto.BillDayOfMonth;
import com.example.project3.model.dto.ChartResponse;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.ImportProductRepository;
import com.example.project3.service.BillService;
import com.example.project3.service.ChartService;
import com.example.project3.service.ImportProductService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChartServiceImpl implements ChartService {

  @Autowired
  private ImportProductRepository importProductRepository;

  @Autowired
  private BillService billService;
  @Autowired
  private BillRepository billRepository;
  @Override
  public List<ChartResponse> linechart() {
    var thisYear = LocalDateTime.now().getYear();
    var importTotal =0L;
    var soldTotal = 0L;
    var billCount = 0L;
    List<ChartResponse> chartResponses = new ArrayList<>();
    for(Long i=1L; i<=12; i++){
      String thismonth =(i>9)?i +"-"+thisYear:"0"+ i +"-"+thisYear;
      System.out.println(thismonth);
      var billS= billService.getTurnover(null, null, thismonth);
      importTotal = billS.getImportPrice();
      soldTotal = billS.getTurnover();
      billCount = billRepository.countByStatusAndMonth(null,null,thismonth);
      var chart = ChartResponse.builder()
          .month(i)
          .importTotal(importTotal)
          .soldTotal(soldTotal)
          .billCount(billCount)
          .build();
      chartResponses.add(chart);
    }
    return chartResponses;
  }

  @Override
  public List<BillDayOfMonth> daychart() {
    List<BillDayOfMonth> list = new ArrayList<>();
    var year = LocalDateTime.now().getYear();
    var month =  LocalDateTime.now().getMonth();
    var countDay= YearMonth.of(year, month).lengthOfMonth();
    for(Long i=1L; i<= countDay; i++){
      String day = (i<=9)? "0" + i: i.toString();
      String m ="-"+ (month.getValue()<=9?"0"+ month.getValue():month.getValue());
      String fullday = day + m + "-" + year;
      list.add(BillDayOfMonth.builder()
          .billCount(billRepository.countByDay(fullday))
          .day(i.longValue())
          .build());
    }
    return list;
  }
}
