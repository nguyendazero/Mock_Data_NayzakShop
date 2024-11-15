package com.haibazo.itsrct.api.mock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
    private int page;
    private int size;
    private int totalItems;
}
