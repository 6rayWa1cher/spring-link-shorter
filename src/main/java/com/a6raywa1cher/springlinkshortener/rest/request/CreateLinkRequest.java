package com.a6raywa1cher.springlinkshortener.rest.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class CreateLinkRequest {
	@NotBlank
	@Size(max = 100)
	private String name = UUID.randomUUID().toString();

	@NotBlank
	@Size(max = 1024)
	@URL()
	private String forwardTo;
}
