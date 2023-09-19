package in.techcamp.issueAppJava;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "issues")
@Entity
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;
    private String period;
    private Character importance;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "issue",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;
}
