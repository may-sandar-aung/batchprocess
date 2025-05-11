package com.customerdata.batchprocess.controller;

import com.customerdata.batchprocess.entity.User;
import com.customerdata.batchprocess.enums.Role;
import com.customerdata.batchprocess.model.TransactionDto;
import com.customerdata.batchprocess.model.TransactionUpdateRequest;
import com.customerdata.batchprocess.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public Page<TransactionDto> getTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String description,
            Pageable pageable , Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean isAdmin = user.getRole().equals(Role.ADMIN);

        if (!isAdmin) {
            customerId = String.valueOf(user.getId());
        }

        if (customerId == null && accountNumber == null && description == null) {
            throw new IllegalArgumentException("At least one filter (customerId, accountNumber, or description) is required.");
        }
        return transactionService.getTransactions(customerId, accountNumber, description, pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/description")
    public ResponseEntity<?> updateDescription(@PathVariable Long id, @RequestBody TransactionUpdateRequest request) {
               transactionService.updateDescription(id, request.getDescription());
        return ResponseEntity.ok("Description updated successfully.");
    }
}
