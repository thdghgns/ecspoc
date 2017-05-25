package com.thdghgns.ecspoc.service.composite;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thdghgns.ecspoc.entity.primary.Post;
import com.thdghgns.ecspoc.entity.secondary.User;
import com.thdghgns.ecspoc.repository.primary.PostRepository;
import com.thdghgns.ecspoc.repository.secondary.UserRepository;

@Service
public class CompositeServiceImpl implements CompositeService{
	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;
	
	@Override
	@Transactional
	public void findAllUserAndPost() {
		userRepository.findAll();
		postRepository.findAll();
	}

	@Override
	@Transactional
	public void saveUserAndPost(User user, Post post) {
		userRepository.saveAndFlush(user);
		postRepository.saveAndFlush(post);
	}
}
