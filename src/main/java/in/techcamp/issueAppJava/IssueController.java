package in.techcamp.issueAppJava;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/issueForm")
    public String showIssueForm(@ModelAttribute("issueForm") IssueEntity issueEntity){
        return "issueForm";
    }

    @PostMapping("/issues")
    public String createIssue(@ModelAttribute("issueForm") IssueEntity issueEntity,
                              Authentication authentication,
                              Model model) {
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        issueEntity.setUser(user);

        try {
            issueRepository.save(issueEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    @GetMapping
    public String showIssues(Model model){
        List<IssueEntity> issueList= issueRepository.findAll();
        model.addAttribute("issueList", issueList);
        return "index";
    }

    @GetMapping("/issue/{issueId}")
    public String showIssueDetail(@PathVariable("issueId") Integer issueId,
                                  @ModelAttribute("commentEntity") CommentEntity commentEntity,
                                  Model model) {
        IssueEntity issue;

        try {
            issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        List<CommentEntity> comments = commentRepository.findByIssue_id(issueId);
        model.addAttribute("comments",comments);
        model.addAttribute("issue", issue);

        return "detail";
    }

    @GetMapping("/user/{userId}/issue/{issueId}/edit")
    public String edit(@PathVariable Integer issueId, Model model) {

        IssueEntity issue;

        try {
            issue = issueRepository.findById(issueId)
                    .orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        model.addAttribute("issue", issue);

        return "edit";
    }

    @PostMapping("/user/{userId}/issue/{issueId}/update")
    public String update(Authentication authentication,
                         @ModelAttribute("issue") IssueEntity issueEntity,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("issueId") Integer issueId,
                         Model model
    ) {

        String username = authentication.getName();

        IssueEntity issue;
        UserEntity user;

        try {
            issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                issue.setTitle(issueEntity.getTitle());
                issue.setContent(issueEntity.getContent());
                issue.setPeriod(issueEntity.getPeriod());
                issue.setImportance(issueEntity.getImportance());

                issueRepository.save(issue);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/issue/" + issueId;
    }

    @PostMapping("/user/{userId}/issue/{issueId}/delete")
    public String delete(Authentication authentication,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("issueId") Integer issueId,
                         Model model) {

        String username = authentication.getName();

        UserEntity user;

        try {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        }  catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (user.getUsername().equals(username)) {
            try {
                issueRepository.deleteById(issueId);
            } catch (Exception e){
                model.addAttribute("errorMessage",e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/";
    }
}
