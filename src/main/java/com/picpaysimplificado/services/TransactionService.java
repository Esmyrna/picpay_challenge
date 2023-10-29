package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private  UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    private void createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value())
        if(!this.authorizeTransaction(sender, transactionDTO.value())){
            throw new Exception("Transação não autorizada");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transaction.value());
        transaction.setSender(sender);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setReceiver(receiver);

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
       ResponseEntity<Map> authorizaionResponse = restTemplate.getForEntity("", Map.class);

       if(authorizaionResponse.getStatusCode() == HttpStatus.OK){
           String message = (String) authorizaionResponse.getBody().get("message");
           return "Autorizado".equalsIgnoreCase(message);
       } else return false;
    }

}
