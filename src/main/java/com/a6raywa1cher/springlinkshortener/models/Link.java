package com.a6raywa1cher.springlinkshortener.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Link {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String req;

	@Column(length = 1025, nullable = false)
	private String forwardingUrl;
}
