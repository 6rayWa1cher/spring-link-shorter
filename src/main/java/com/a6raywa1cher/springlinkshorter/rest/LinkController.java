package com.a6raywa1cher.springlinkshorter.rest;

import com.a6raywa1cher.springlinkshorter.models.Link;
import com.a6raywa1cher.springlinkshorter.rest.request.CreateLinkRequest;
import com.a6raywa1cher.springlinkshorter.rest.response.NameAlreadyTakenException;
import com.a6raywa1cher.springlinkshorter.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/link")
public class LinkController {
	private LinkService linkService;

	@Autowired
	public LinkController(LinkService linkService) {
		this.linkService = linkService;
	}

	@PostMapping("/")
	public ResponseEntity<Link> create(@RequestBody @Valid CreateLinkRequest dto) {
		Link link = new Link();
		if (linkService.getByReq(dto.getName()).isPresent()) {
			throw new NameAlreadyTakenException();
		}
		link.setReq(dto.getName());
		link.setForwardingUrl(dto.getForwardTo());
		Link out = linkService.save(link);
		return ResponseEntity.ok(out);
	}

	@GetMapping("/r/{name}")
	public ResponseEntity<?> get(@PathVariable String name) {
		Optional<Link> optionalLink = linkService.getByReq(name);
		if (optionalLink.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
				.location(URI.create(optionalLink.get().getForwardingUrl()))
				.build();
	}
}
