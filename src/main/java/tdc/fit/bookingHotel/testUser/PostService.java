package tdc.fit.bookingHotel.testUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post, String ownerUsername) {
        post.setOwnerUsername(ownerUsername);
        return postRepository.save(post);
    }

    @PreAuthorize("hasPermission(#postId, 'delete')")
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    @PreAuthorize("hasPermission(#postId, 'edit')")
    public Post editPost(Long postId, Post updatedPost) {
        Post existingPost = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        return postRepository.save(existingPost);
    }
}

