package me.seungwoo.domain.timeResponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.timepoll.TimePoll;
import me.seungwoo.domain.user.User;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_response")
@Getter
@NoArgsConstructor
public class TimeResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responsePk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_pk", nullable = false)
    private TimePoll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime startTimeUtc;

    @Column(nullable = false)
    private LocalDateTime endTimeUtc;
}