package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.DuplicateUserPostException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserAlreadyExistsException;

@RestController
public class UserController {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return userDaoService.findAll();
	}
	
	@GetMapping(path = "/users/{userId}")
	public User retrieveUser(@PathVariable String userId){
		return userDaoService.findOne(Integer.valueOf(userId));
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		try {
			User savedUser = userDaoService.save(user);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
			return ResponseEntity.created(location).build();
		}catch(UserAlreadyExistsException uae) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@DeleteMapping(path = "/users/{userId}")
	public void removeUser(@PathVariable String userId) {
		userDaoService.removeUser(Integer.valueOf(userId));
	}
	
	//Get All posts for user
	@GetMapping(path = "/users/{userId}/posts")
	public List<Post> getAllUserPosts(@PathVariable String userId){
		return userDaoService.findUserPosts(Integer.valueOf(userId));
	}
	
	@PostMapping(path = "/users/{userId}/posts")
	public ResponseEntity<Object> createUserPost(@PathVariable String userId, @RequestBody Post post) { 
		try {
			Post savedPost = userDaoService.savePost(Integer.valueOf(userId), post);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
							.buildAndExpand(savedPost.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch(DuplicateUserPostException dupe) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping(path = "/users/{userId}/posts/{postId}")
	public Post getUserPostById(@PathVariable String userId, @PathVariable String postId){
		return userDaoService.findUserPost(Integer.valueOf(userId), Integer.valueOf(postId));
	}

}
