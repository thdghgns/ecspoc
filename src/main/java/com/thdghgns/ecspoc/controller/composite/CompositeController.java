package com.thdghgns.ecspoc.controller.composite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thdghgns.ecspoc.entity.primary.Post;
import com.thdghgns.ecspoc.entity.secondary.User;
import com.thdghgns.ecspoc.service.composite.CompositeService;

@RestController
@RequestMapping(value = "/composite")
public class CompositeController {

	@Autowired
	CompositeService sampleService;
	
	@GetMapping
	public String findAllPost() throws JsonProcessingException {
		sampleService.findAllUserAndPost();
		return "";
	}

	@PostMapping
	public String savePost() throws JsonProcessingException {
		User user = new User();
		user.setName("song_hohoon");
		
		Post post = new Post();
		post.setSubject("IT");
		post.setContent("SpringFramework");
		
		sampleService.saveUserAndPost(user, post);
		
		return "";
	}

	private String jsonConverter(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(obj);

		return result;
	}
}
