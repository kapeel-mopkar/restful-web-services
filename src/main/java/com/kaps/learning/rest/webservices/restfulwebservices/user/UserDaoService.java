package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserAlreadyExistsException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserNotFoundException;
import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.UserPostNotFoundException;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();

	private static int userCount = 4;
	private static int userPostCount = 2;

	static {
		User userWithPosts = new User(1, "Adam", new Date());
		userWithPosts.addPost(new Post(1, "Good Morning!", "What a nice day it is. Have a great morning."));
		userWithPosts.addPost(new Post(2, "Good Evening!", "What a pleasant evening. Have a great evening."));
		users.add(userWithPosts);
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
		users.add(new User(4, "Jill", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null || !users.contains(user)) {
			user.setId(++userCount);
			users.add(user);
			return user;
		}
		throw new UserAlreadyExistsException("USER_ALREADY_EXISTS");
	}

	public User findOne(int id) {
		Optional<User> firstMatch = users.stream().filter(user -> user.getId().equals(id)).findAny();
		if (firstMatch != null && firstMatch.isPresent()) {
			return firstMatch.get();
		}
		throw new UserNotFoundException("USER_NOT_FOUND - "+id);
	}

	public void removeUser(int id) {
		List<User> markedUsers = users.stream()
				.filter(user -> user.getId().equals(id))
				.collect(Collectors.toList());
		users.removeAll(markedUsers);
	}
	
	public List<Post> findUserPosts(int id){
		Optional<User> firstMatch = users.stream().filter(user -> user.getId().equals(id)).findAny();
		if (firstMatch != null && firstMatch.isPresent()) {
			return firstMatch.get().getPosts();
		}
		return Collections.emptyList();
	}
	
	public Post savePost(int userId, Post post) {
		Optional<User> firstMatch = users.stream().filter(user -> user.getId().equals(userId)).findAny();
		if (firstMatch != null && firstMatch.isPresent()) {
			post.setId(++userPostCount);
			firstMatch.get().addPost(post);
		}
		return post;
	}
	
	public Post findUserPost(int userId, int postId) {
		Optional<User> firstMatch = users.stream().filter(user -> user.getId().equals(userId)).findAny();
		if (firstMatch != null && firstMatch.isPresent()) {
			User user = firstMatch.get();
			List<Post> userPosts = user.getPosts();
			if(userPosts != null && !userPosts.isEmpty()) {
				Optional<Post> findFirstPost = userPosts.stream().filter(post -> post.getId().equals(postId)).findFirst();
				if (findFirstPost != null && findFirstPost.isPresent()) {
					return findFirstPost.get();
				}
			}
		}
		throw new UserPostNotFoundException("USER_POST_NOT_FOUND - "+postId);
	}
}
