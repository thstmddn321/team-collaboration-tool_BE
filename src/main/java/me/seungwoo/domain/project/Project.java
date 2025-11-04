package me.seungwoo.domain.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungwoo.domain.projectUser.ProjectUser;
import me.seungwoo.domain.user.User;
import me.seungwoo.domain.post.Post;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectPk;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    // 프로젝트의 주인 (user)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_owner_user_pk", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectUser> members;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Post> posts;
}