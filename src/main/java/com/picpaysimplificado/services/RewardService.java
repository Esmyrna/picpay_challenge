package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.reward.Reward;
import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repositories.RewardRepository;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RewardService {
    private final TransactionRepository transactionRepository;
    private final RewardRepository rewardRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository, TransactionRepository transactionRepository) {
        this.rewardRepository = rewardRepository;
        this.transactionRepository = transactionRepository;
    }
    public Reward findRewardById(Long id) {
        Optional<Reward> reward = rewardRepository.findById(id);
        return reward.orElse(null);
    }

    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    public Reward updateReward(Long rewardId, BigDecimal newAmount, String newDescription) {
        Optional<Reward> optionalReward = rewardRepository.findById(rewardId);
        if (optionalReward.isPresent()) {
            Reward reward = optionalReward.get();
            reward.setAmount(newAmount);
            reward.setDescription(newDescription);
            return rewardRepository.save(reward);
        }
        return null;
    }
    public void checkAndGrantReward(User user) {
        int numberOfTransactionsForReward = 5;

        List<Transaction> userTransactions = transactionRepository.findBySenderOrReceiver(user, user);
        if (userTransactions.size() >= numberOfTransactionsForReward) {
            Reward reward = new Reward();
            reward.setUser(user);
            reward.setAmount(new BigDecimal("10.00")); // Defina o valor do prêmio conforme necessário
            reward.setDescription("Parabéns! Você alcançou o número de transações para este prêmio.");
            rewardRepository.save(reward);
        }
    }
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
}
