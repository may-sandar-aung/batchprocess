package com.customerdata.batchprocess.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionUpdateRequest {
    @NotBlank
    private String description;
}
