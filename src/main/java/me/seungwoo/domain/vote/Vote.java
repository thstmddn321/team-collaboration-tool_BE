package me.seungwoo.domain.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.post.Post;
import me.seungwoo.domain.voteOption.VoteOption;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "votes")
@Getter
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votePk;

    @OneToOne
    @JoinColumn(name = "post_pk", nullable = false, unique = true)
    private Post post;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private Boolean allowMultipleChoices;
    private Boolean isAnonymous;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<VoteOption> options;
}