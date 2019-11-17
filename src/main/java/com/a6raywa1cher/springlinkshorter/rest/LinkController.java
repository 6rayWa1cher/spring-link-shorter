package com.a6raywa1cher.springlinkshorter.rest;

import com.a6raywa1cher.springlinkshorter.models.Link;
import com.a6raywa1cher.springlinkshorter.rest.request.CreateLinkRequest;
import com.a6raywa1cher.springlinkshorter.services.interfaces.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		Link link = linkService.uploadLink(dto.getName(), dto.getForwardTo());
		return ResponseEntity.ok(link);
	}

	@GetMapping("/")
	@Transactional
	public ResponseEntity<List<Link>> getPage(Pageable pageable) {
		Page<Link> linkPage = linkService.getPage(pageable);
		return ResponseEntity.ok(linkPage.get().collect(Collectors.toList()));
	}

	@GetMapping("/{name}")
	public ResponseEntity<Link> get(@PathVariable String name) {
		Optional<Link> optionalLink = linkService.getByReq(name);
		if (optionalLink.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(optionalLink.get());
	}
}
