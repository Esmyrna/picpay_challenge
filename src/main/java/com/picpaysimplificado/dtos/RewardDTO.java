package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.user.User;

import java.math.BigDecimal;

public record RewardDTO(User user, BigDecimal amount, String description) {
}
