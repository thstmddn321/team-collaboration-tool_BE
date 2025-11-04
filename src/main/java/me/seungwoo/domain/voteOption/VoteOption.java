package me.seungwoo.domain.voteOption;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.vote.Vote;
import me.seungwoo.domain.voteResponse.VoteResponse;

import java.util.List;

@Entity
@Table(name = "vote_options")
@Getter
@NoArgsConstructor
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_pk", nullable = false)
    private Vote vote;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private List<VoteResponse> responses;
}