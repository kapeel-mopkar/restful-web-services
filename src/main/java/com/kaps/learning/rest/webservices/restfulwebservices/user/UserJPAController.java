package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.DuplicateUserPostException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserAlreadyExistsException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserNotFoundException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserPostNotFoundException;

/**
 * Controller for User resource
 * @author kapeel_mopkar
 *
 */
@RestController
@RequestMapping("jpa")
public class UserJPAController {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	/**
	 * Get all users
	 * @return
	 */
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		//return userDaoService.findAll();
		return userRepository.findAll();
	}
	
	/**
	 * Get user by Id
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/users/{userId}")
	public EntityModel<User> retrieveUser(@PathVariable String userId){
		//User user = userDaoService.findOne(Integer.valueOf(userId));
		Optional<User> user = userRepository.findById(Integer.valueOf(userId));
		/*
		 * EntityModel<User> model = new EntityModel<User>(user); WebMvcLinkBuilder
		 * linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 * model.add(linkTo.withRel("all-users"));
		 */
		if(!user.isPresent()) {
			throw new UserNotFoundException("USER_NOT_FOUND");
		}
		
		EntityModel<User> userResource = new EntityModel<User>(user.get());
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		userResource.add(linkTo.withRel("all-users"));
		return userResource;
	}
	
	/**
	 * Create user
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		try {
			//User savedUser = userDaoService.save(user);
			User savedUser = userRepository.save(user);
			
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
		//userDaoService.removeUser(Integer.valueOf(userId));
		userRepository.deleteById(Integer.valueOf(userId));
	}
	
	/**
	 * Get All posts for user
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/users/{userId}/posts")
	public List<Post> getAllUserPosts(@PathVariable String userId){
		
		Optional<User> user = userRepository.findById(Integer.valueOf(userId));
		if(!user.isPresent()) {
			throw new UserNotFoundException("USER_NOT_FOUND");
		}
		return user.get().getPosts();

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
			Optional<User> userOptional = userRepository.findById(Integer.valueOf(userId));
			if(!userOptional.isPresent()) {
				throw new UserNotFoundException("USER_NOT_FOUND");
			}
			User user = userOptional.get();
			post.setAuthor(user);
			post.setPostedDate(new Date());
			//Post savedPost = userDaoService.savePost(Integer.valueOf(userId), post);
			Post savedPost = postRepository.save(post);
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
		Optional<User> optionalUser = userRepository.findById(Integer.valueOf(userId));
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("USER_NOT_FOUND");
		}
		Optional<Post> optionalPost = postRepository.findById(Integer.valueOf(postId));
		Post post = optionalPost.get();
		
		// Check so that users don't access other users posts
		if(post.getAuthor().equals(optionalUser.get())) {
			return post;
		}
		throw new UserPostNotFoundException("USER_POST_NOT_FOUND");
	}

}
