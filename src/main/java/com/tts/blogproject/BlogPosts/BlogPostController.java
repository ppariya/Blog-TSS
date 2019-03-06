package com.tts.blogproject.BlogPosts;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogPostController {

    private ArrayList<BlogPost> posts;

//    @Autowired
    private BlogPostRepository blogPostRepository;

    public BlogPostController(ArrayList<BlogPost> posts, BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
        this.posts = posts;
    }

    private void mirrorDB(){
        Iterable<BlogPost> blogList = blogPostRepository.findAll();
        posts.clear();
        for(BlogPost blog: blogList) {
            posts.add(blog);
            }
        }
    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {
        mirrorDB();
        model.addAttribute("posts", posts);
        return "mainBlog";
    }

    @GetMapping(value = "/blog_posts/{id}/edit")
    public String editBlogEntryView(@PathVariable("id") Long id, Model model){
        Optional<BlogPost>op = blogPostRepository.findById(id);
        System.out.println(op.get().toString());
        model.addAttribute("blogPost", op.get());
        return "edit";
    }
    @PutMapping("/blog_posts/{id}/edit")
    public String editBlogEntryView(BlogPost blogPost, Model model){
        blogPostRepository.save(blogPost);
        mirrorDB();
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "result";
    }

    @GetMapping(value = "/blog_posts/new")
    public String newBlog (BlogPost blogPost) {
        return "new";

    }
    //	private BlogPost blogPost;
    @PostMapping(value = "/blog_posts/new")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        // Shortcut (remains to be seen if works):
        blogPostRepository.save(blogPost);
        posts.add(blogPost);
        //Longer version:
        //blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "result";
    }

    @DeleteMapping(value = "/blog_posts/{id}/delete")
    public String deleteBlogPost (@PathVariable("id")Long id){
        blogPostRepository.deleteById(id);
        mirrorDB();
        return "result";
    }
}
