package org.vitaliistf.blocktracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private List<String> errors;
    private LocalDateTime timestamp;
}
