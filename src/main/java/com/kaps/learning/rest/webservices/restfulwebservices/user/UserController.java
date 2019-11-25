package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

/**
 * Controller for User resource
 * @author kapeel_mopkar
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserDaoService userDaoService;
	
	/**
	 * Get all users
	 * @return
	 */
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return userDaoService.findAll();
	}
	
	/**
	 * Get user by Id
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/users/{userId}")
	public User retrieveUser(@PathVariable String userId){
		User user = userDaoService.findOne(Integer.valueOf(userId));
		/*
		 * EntityModel<User> model = new EntityModel<User>(user); WebMvcLinkBuilder
		 * linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 * model.add(linkTo.withRel("all-users"));
		 */
		return user;
	}
	
	/**
	 * Create user
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		try {
			User savedUser = userDaoService.save(user);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
			return ResponseEntity.created(location).build();
		}catch(UserAlreadyExistsException uae) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	/**
	 * Delete user
	 * @param userId
	 */
	@DeleteMapping(path = "/users/{userId}")
	public void removeUser(@PathVariable String userId) {
		userDaoService.removeUser(Integer.valueOf(userId));
	}
	
	/**
	 * Get All posts for user
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/users/{userId}/posts")
	public List<Post> getAllUserPosts(@PathVariable String userId){
		return userDaoService.findUserPosts(Integer.valueOf(userId));
	}
	
	/**
	 * Create post for user
	 * @param userId
	 * @param post
	 * @return
	 */
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
	
	/**
	 * Get user post by id
	 * @param userId
	 * @param postId
	 * @return
	 */
	@GetMapping(path = "/users/{userId}/posts/{postId}")
	public Post getUserPostById(@PathVariable String userId, @PathVariable String postId){
		return userDaoService.findUserPost(Integer.valueOf(userId), Integer.valueOf(postId));
	}

}
