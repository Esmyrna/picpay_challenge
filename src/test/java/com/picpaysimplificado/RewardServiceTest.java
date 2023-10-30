package com.picpaysimplificado;

import com.picpaysimplificado.domain.reward.Reward;
import com.picpaysimplificado.services.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @Test
    public void testSaveReward() {
        Reward reward = new Reward();
        reward.setAmount(new BigDecimal("50.00"));
        reward.setDescription("Test Reward Description");

        Reward savedReward = rewardService.saveReward(reward);
        assert savedReward.getId() != null; // Verifica se o ID foi gerado ao salvar
    }

    @Test
    public void testFindRewardById() {
        Reward reward = new Reward();
        reward.setAmount(new BigDecimal("30.00"));
        reward.setDescription("Find Reward by ID Test");

        Reward savedReward = rewardService.saveReward(reward);

        Reward foundReward = rewardService.findRewardById(savedReward.getId());
        assert foundReward != null;
    }

    @Test
    public void testUpdateReward() {
        Reward reward = new Reward();
        reward.setAmount(new BigDecimal("25.00"));
        reward.setDescription("Update Reward Test");

        Reward savedReward = rewardService.saveReward(reward);

        String updatedDescription = "Updated Reward Description";
        savedReward.setDescription(updatedDescription);
        Reward updatedReward = rewardService.updateReward(savedReward.getId(), savedReward.getAmount(), updatedDescription);

        assert updatedReward.getDescription().equals(updatedDescription);
    }

    @Test
    public void testDeleteReward() {
        Reward reward = new Reward();
        reward.setAmount(new BigDecimal("40.00"));
        reward.setDescription("Delete Reward Test");

        Reward savedReward = rewardService.saveReward(reward);

        rewardService.deleteReward(savedReward.getId());

        Reward deletedReward = rewardService.findRewardById(savedReward.getId());
        assert deletedReward == null; // Verifica se o prêmio foi excluído com sucesso
    }

    @Test
    public void testRewardProperties() {
        Reward reward = new Reward();
        reward.setAmount(new BigDecimal("100.00"));
        reward.setDescription("Reward Properties Test");

        assert reward.getAmount().equals(new BigDecimal("100.00"));
        assert reward.getDescription().equals("Reward Properties Test");
    }
}
