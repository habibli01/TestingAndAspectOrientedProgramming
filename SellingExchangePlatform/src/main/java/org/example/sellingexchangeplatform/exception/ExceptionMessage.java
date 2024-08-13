package org.example.sellingexchangeplatform.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    NOT_FOUND("Not found with id: %s"),
    BAD_REQUEST("Bad request: %s"),
    UNAUTHORIZED("Unauthorized access: %s"),
    FORBIDDEN("Forbidden action: %s"),
    CONFLICT("Conflict occurred: %s"),
    INTERNAL_SERVER_ERROR("Internal server error: %s"),
    PRODUCT_NOT_FOUND("Product not found with id: %s"),
    USER_NOT_FOUND("User not found with id: %s"),
    INSUFFICIENT_BALANCE("Insufficient balance for user id: %s"),
    INVALID_PRODUCT_TYPE("Invalid product type: %s"),
    ACCESS_DENIED("Access denied for user id: %s"),
    INVALID_CREDENTIALS("Invalid credentials provided"),
    DUPLICATE_RESOURCE("Duplicate resource found: %s"),
    INVALID_INPUT("Invalid input: %s"),
    TRANSACTION_ERROR("Transaction error: %s");
    private final String message;
}
