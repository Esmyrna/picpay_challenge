package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.reward.Reward;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Reward createReward(User user, BigDecimal amount, String description) {
        Reward newReward = new Reward();
        newReward.setUser(user);
        newReward.setAmount(amount);
        newReward.setDescription(description);
        return rewardRepository.save(newReward);
    }



    // Outros métodos conforme necessário, como atualizar uma recompensa, encontrar por ID, etc.
}
