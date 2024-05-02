package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.PaymentRequestDto;
import com.petpal.petpalservice.model.dto.PaymentResponseDto;
import com.petpal.petpalservice.model.entity.Payment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor

public class PaymentMapper {
    private final ModelMapper modelMapper;
    public Payment convertToEntity(PaymentRequestDto paymentRequestDto) {
        return modelMapper.map(paymentRequestDto, Payment.class);
    }

    public PaymentResponseDto convertToDto(Payment payment) {
        return modelMapper.map(payment, PaymentResponseDto.class);
    }

    public List<PaymentResponseDto> convertToDto(List<Payment> payments) {
        return payments.stream()
                .map(this::convertToDto)
                .toList();
    }
}
