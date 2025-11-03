package me.seungwoo.domain.shortcut;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.user.User;

@Entity
@Table(name = "shortcuts")
@Getter
@NoArgsConstructor
public class Shortcut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shortcutPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;
}