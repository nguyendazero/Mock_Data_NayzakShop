package com.haibazo.bff.mock.webapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class for representing pagination metadata.
 *
 * <p>
 * This class contains information about pagination, including the current page
 * index,
 * the number of items displayed per page, and the total number of items
 * available.
 * </p>
 *
 * <p>
 * The fields in this class are used to facilitate pagination in API responses,
 * providing
 * necessary metadata for the frontend or client to implement pagination
 * controls.
 * </p>
 *
 * <p>
 * Lombok annotations are used to automatically generate getters, setters, and
 * constructors:
 * </p>
 * <ul>
 * <li>{@link Data}: Generates getters, setters, toString, equals, and hashCode
 * methods.</li>
 * <li>{@link AllArgsConstructor}: Generates a constructor with parameters for
 * all fields.</li>
 * <li>{@link NoArgsConstructor}: Generates a no-arguments constructor for
 * creating empty objects.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDto {

    /**
     * The index of the current page.
     * <p>
     * This field indicates which page is currently being viewed in the pagination
     * system.
     * </p>
     */
    private int pageIndex;

    /**
     * The number of items displayed per page.
     * <p>
     * This field specifies how many items are shown on each page of the pagination.
     * </p>
     */
    private int pageSize;

    /**
     * The total number of items available.
     * <p>
     * This field provides the total number of items, which can be used to calculate
     * the
     * total number of pages in the pagination system.
     * </p>
     */
    private int totalItems;
}
