package com.haibazo.itsrct.api.mock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDto {
    private int pageIndex;
    private int pageSize;
    private int totalItems;
}
