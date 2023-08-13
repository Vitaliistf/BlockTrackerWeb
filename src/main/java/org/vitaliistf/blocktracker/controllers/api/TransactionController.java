package org.vitaliistf.blocktracker.controllers.api;

import org.vitaliistf.blocktracker.dto.request.TransactionRequestDto;
import org.vitaliistf.blocktracker.dto.response.TransactionResponseDto;
import org.vitaliistf.blocktracker.models.Transaction;
import org.vitaliistf.blocktracker.service.UserDetailsService;
import org.vitaliistf.blocktracker.service.TransactionService;
import org.vitaliistf.blocktracker.service.mapper.RequestDtoMapper;
import org.vitaliistf.blocktracker.service.mapper.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserDetailsService userDetailsService;
    private final ResponseDtoMapper<TransactionResponseDto, Transaction> responseDtoMapper;
    private final RequestDtoMapper<TransactionRequestDto, Transaction> requestDtoMapper;

    @PostMapping
    public TransactionResponseDto create(Authentication authentication,
                                         @Valid @RequestBody TransactionRequestDto transactionRequestDTO) {
        Transaction transaction = requestDtoMapper.mapToModel(transactionRequestDTO);
        return responseDtoMapper.mapToDto(
                transactionService.addTransaction(transaction, userDetailsService.getClient(authentication)
        ));
    }

    @PutMapping("/{id}")
    public TransactionResponseDto update(Authentication authentication,
                                         @PathVariable Long id,
                                         @Valid @RequestBody TransactionRequestDto transactionRequestDTO) {
        Transaction transaction = requestDtoMapper.mapToModel(transactionRequestDTO);
        return responseDtoMapper.mapToDto(
                transactionService.editById(id, transaction, userDetailsService.getClient(authentication))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable Long id) {
        transactionService.deleteById(id, userDetailsService.getClient(authentication));
    }

    @GetMapping("/by-coin/{coinId}")
    public List<TransactionResponseDto> getTransactionsForCoin(Authentication authentication,
                                                               @PathVariable Long coinId) {
        return transactionService.getByCoinId(coinId, userDetailsService.getClient(authentication))
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TransactionResponseDto get(Authentication authentication, @PathVariable Long id) {
        return responseDtoMapper.mapToDto(
                transactionService.getById(id, userDetailsService.getClient(authentication))
        );
    }
}
