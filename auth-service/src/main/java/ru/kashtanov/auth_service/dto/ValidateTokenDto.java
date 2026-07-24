package ru.kashtanov.auth_service.dto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenDto {
    private int status;
    private String errorName;
    private boolean isValid;

}
