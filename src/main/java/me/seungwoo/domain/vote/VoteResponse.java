package me.seungwoo.domain.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.user.User;

@Entity
@Table(name = "vote_response")
@Getter
@NoArgsConstructor
public class VoteResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responsePk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_pk", nullable = false)
    private VoteOption option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", nullable = false)
    private User user;
}