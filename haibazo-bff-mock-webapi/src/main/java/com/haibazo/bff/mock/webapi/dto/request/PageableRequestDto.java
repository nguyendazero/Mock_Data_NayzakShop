package com.haibazo.bff.mock.webapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for handling pagination requests.
 * Provides standardized pagination parameters with validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableRequestDto {

    /**
     * The current page index (1-based indexing)
     */
    @NotNull(message = "HBZE000001I")
    @Min(value = 1, message = "HBZE100001I")
    @JsonProperty("page_index")
    @Builder.Default
    private Integer pageIndex = 1;

    /**
     * Number of items per page
     */
    @NotNull(message = "HBZE000001I")
    @Min(value = 1, message = "HBZE100001I")
    @Max(value = 100, message = "HBZE100002I")
    @JsonProperty("page_size")
    @Builder.Default
    private Integer pageSize = 10;

    /**
     * Calculates the offset for database queries
     * 
     * @return offset value
     */
    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

}