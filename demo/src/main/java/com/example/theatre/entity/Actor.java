package com.example.theatre.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Actor implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer actorId;
	private String firstName;
	private String lastName;
	private Timestamp lastUpdate;

	public Integer getActorId() {
		return actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public Integer getId() {
		return actorId;
	}

	public void setId(Integer id) {
		this.actorId = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
