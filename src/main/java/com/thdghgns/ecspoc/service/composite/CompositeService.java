package com.thdghgns.ecspoc.service.composite;

import com.thdghgns.ecspoc.entity.primary.Post;
import com.thdghgns.ecspoc.entity.secondary.User;

public interface CompositeService {
	public void findAllUserAndPost();
	public void saveUserAndPost(User user, Post post);
}
