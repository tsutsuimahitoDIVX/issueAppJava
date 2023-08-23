package in.techcamp.issueAppJava;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IssueEntity {
    private long id;
    private String title;
    private String content;
    private String period;
    private Character importance;
}