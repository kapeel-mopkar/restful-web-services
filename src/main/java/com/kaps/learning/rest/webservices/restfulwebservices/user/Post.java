package com.kaps.learning.rest.webservices.restfulwebservices.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Post {
	private Integer id;
	private String title;
	private String description;
	private Date postedDate;
	@JsonIgnore // in order to avoid cyclic reference to user -> posts -> user
	private User author;
	
	protected Post() {}
	
	public Post(Integer id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.postedDate = new Date();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Post other = (Post) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
