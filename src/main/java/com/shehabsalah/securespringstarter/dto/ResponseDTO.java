package com.shehabsalah.securespringstarter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * A generic class representing a response data transfer object (DTO) used for API responses.
 * The class provides a standardized structure for encapsulating response information.
 * It includes fields such as message, success status, error details, status code, timestamp, and data.
 *
 * <p>
 * The class is designed to be used with a generic type parameter 'T' to accommodate different types of data.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private String message;
    private Boolean success;
    private String error;
    private Integer status;
    private ZonedDateTime timestamp;
    private T data;
}
