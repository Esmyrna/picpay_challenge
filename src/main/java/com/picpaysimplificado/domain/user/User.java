package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.domain.reward.Reward;
import com.picpaysimplificado.domain.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private BigDecimal balance;
    @OneToMany(mappedBy = "user")
    private List<Reward> rewards;
    @Enumerated(EnumType.STRING)
    private UserType userType;


}
