package tdc.fit.bookingHotel.testUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getAll(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "posts";
    }

    @GetMapping("/posts/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "new-post";
    }

    @PostMapping("/posts")
    public String create(@ModelAttribute Post post, Authentication auth) {
        postService.createPost(post, auth.getName());
        return "redirect:/posts";
    }

    @GetMapping("/posts/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
