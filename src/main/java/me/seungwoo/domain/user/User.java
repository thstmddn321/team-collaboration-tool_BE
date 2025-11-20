package me.seungwoo.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

import lombok.Setter;
import me.seungwoo.domain.project.Project;
import me.seungwoo.domain.projectUser.ProjectUser;
import me.seungwoo.domain.shortcut.Shortcut;
import me.seungwoo.domain.post.Post;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPk;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String field;

    // 회원 탈퇴 관련 필드
    @Column(nullable = false)
    private Boolean isDeleted = false;  // 탈퇴 여부

    private LocalDateTime deletedAt;    // 탈퇴 시간


    public User(String password, String name, String email, String phone, String field) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.field = field;
    }

    // 연결 관계
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> ownedProjects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectUser> projectUsers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shortcut> shortcuts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
}