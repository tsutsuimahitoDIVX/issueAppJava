package in.techcamp.issueAppJava;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "issue",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private List<CommentEntity> comments;
}
