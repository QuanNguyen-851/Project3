package com.example.project3.model.entity;

import com.example.project3.model.dto.BillDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewProdResponse {
Long count;
List<ProductResponse> data;
}
