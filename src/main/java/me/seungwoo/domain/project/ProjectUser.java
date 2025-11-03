package me.seungwoo.domain.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.user.User;

@Entity
@Table(name = "project_user")
@Getter
@NoArgsConstructor
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectUserPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_pk", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", nullable = false)
    private User user;

    private String status;
}