package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.kaps.learning.rest.webservices.restfulwebservices.user.exception.DuplicateUserPostException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about User resource")
public class User {
	
	private Integer id;
	@Size(min = 2, message = "Name should have at least 2 characters")
	@ApiModelProperty(notes = "Name should have at least 2 characters")
	private String name;
	@Past(message = "Enter valid birthDate")
	@ApiModelProperty(notes = "Birthdate should be in past")
	private Date birthDate;
	private List<Post> posts;
	
	protected User() {}
	
	public User(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Post> getPosts() {
		if(this.posts==null) {
			this.posts = new ArrayList<Post>();
		}
		return posts;
	}

	public void setPosts(List<Post> posts) {
		if(this.posts==null) {
			this.posts = new ArrayList<Post>();
		}
		this.posts.forEach(post -> {
			post.setAuthor(this);
		});
		;
		this.posts.addAll(posts.stream()
				.filter(post -> !this.posts.contains(post))
				.collect(Collectors.toList()));
	}
	
	public void addPost(Post post) {
		if(this.posts==null) {
			this.posts = new ArrayList<Post>();
		}
		if(post != null) {
			if(!this.posts.contains(post)) {
				post.setAuthor(this);
				post.setPostedDate(new Date());
				this.posts.add(post);
			}else {
				throw new DuplicateUserPostException("DUPLICATE_USER_POST");
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
