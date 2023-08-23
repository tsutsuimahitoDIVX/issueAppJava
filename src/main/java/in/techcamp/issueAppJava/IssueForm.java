package in.techcamp.issueAppJava;

import lombok.Data;

@Data
public class IssueForm {
    private String title;
    private String content;
    private String period;
    private Character importance;
}
