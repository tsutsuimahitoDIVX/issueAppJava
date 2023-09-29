package in.techcamp.issueAppJava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueControllerUnitTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private IssueController issueController = new IssueController();

    @Test
    public void testShowIssueDetail() {
        IssueEntity expectedIssue = new IssueEntity();

        List<CommentEntity> expectedComments = new ArrayList<>();

        CommentEntity comment1 = new CommentEntity();
        comment1.setId(1);
        comment1.setMessage("This is a comment.");
        expectedComments.add(comment1);

        when(issueRepository.findById(any(Integer.class))).thenReturn(Optional.of(expectedIssue));
        when(commentRepository.findByIssue_id(any(Integer.class))).thenReturn(expectedComments);

        Model model = new ExtendedModelMap();
        String result = issueController.showIssueDetail(1, new CommentEntity(), model);

        assertThat(result, is("detail"));
        assertThat(model.getAttribute("issue"), is(expectedIssue));
        assertThat(model.getAttribute("comments"), is(expectedComments));
    }
}