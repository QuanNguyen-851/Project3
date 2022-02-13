package com.example.project3.model.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewProfileResponse {
Long count;
List<ProfileEntity> data;
}
