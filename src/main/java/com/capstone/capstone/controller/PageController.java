package com.capstone.capstone.controller;

import com.capstone.capstone.service.CommentService;
import com.capstone.capstone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @GetMapping("/posts/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("comments", commentService.findByPostId(id));
        return "post/detail";
    }

    @GetMapping("/posts/create")
    public String postCreate() {
        return "post/create";
    }

    @GetMapping("/posts/{id}/edit")
    public String postEdit(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "post/edit";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/profile/password")
    public String password() {
        return "user/password";
    }

    @GetMapping("/find-password")
    public String findPassword() {
        return "user/find-password";
    }
}
