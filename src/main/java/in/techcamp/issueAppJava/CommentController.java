package in.techcamp.issueAppJava;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("issue/{issueId}/comment")
    public String postComment(Authentication authentication,
                              @ModelAttribute CommentEntity commentEntity,
                              @PathVariable("issueId") Integer issueId,
                              Model model )
    {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));

        commentEntity.setUser(user);
        commentEntity.setIssue(issue);

        try {
            commentRepository.save(commentEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "redirect:/issue/" + issueId;
    }
}
