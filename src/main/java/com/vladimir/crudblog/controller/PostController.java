package com.vladimir.crudblog.controller;

import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.repository.PostRepository;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.repository.SQL.SQLPostRepositoryImpl;

import java.util.List;

public class PostController {
    private final PostRepository repository = SQLPostRepositoryImpl.getInstance();

    public List<Post> getAll() {
        return repository.getAll();
    }

    public Post addPost(String content){
        Post post = new Post(null, content.trim());
        repository.save(post);
        return post;
    }

    public Post getByID(Long id) throws ServiceException {
        return repository.getById(id);
    }

    public void deleteByID(Long id) throws ServiceException {
        repository.deleteById(id);
    }

    public Post update(Post post) throws ServiceException {
        repository.update(post);
        return post;
    }
}
