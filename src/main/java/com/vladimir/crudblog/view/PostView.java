package com.vladimir.crudblog.view;

import com.vladimir.crudblog.controller.PostController;
import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.service.ServiceException;

import java.util.List;
import java.util.Scanner;

public class PostView implements View{
    private final Scanner SCANNER = new Scanner(System.in);
    PostController postController = new PostController();

    @Override
    public void create() {
        System.out.print("Type post, in the end type '%end' to save it:");
        String post = "";
        String line;
        while(!(line = SCANNER.nextLine().trim()).equals("%end"))
            post += line + "\n";
        if (post.length() == 0) post += " ";
        System.out.println("Added new Post :"  + "\n"
                + postController.addPost(post.trim()));

    }

    @Override
    public void readAll() {
        List<Post> posts = postController.getAll();

        if(posts.size() == 0){
            System.out.println("No posts in repository");
            return;
        }
        System.out.println("List of all posts:");
        posts.stream().map(Post::toString)
                .map(s -> s + "\n")
                .forEachOrdered(System.out::println);
    }

    @Override
    public void read(Long id) {
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo((long)0) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }

        Post post = null;
        try {
            post = postController.getByID(id);
            System.out.println(post);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id) {
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo((long)0) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }
        System.out.println("Type new post, in the end type '%end' to save it:");
        String content = "";
        String line;
        while(!(line = SCANNER.nextLine().trim()).equals("%end"))
            content += line + "\n";
        if (content.length() == 0) content += " ";
        Post post = new Post(id, content);
        try{
            post = postController.update(post);
        } catch (ServiceException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Post with ID " + id + " has been changed successfully");
        System.out.println(post);

    }

    @Override
    public void delete(Long id) {
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo((long)0) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }

        try{
            postController.deleteByID(id);
        } catch (ServiceException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Post with id " + id + " has been deleted successfully");
    }
}
