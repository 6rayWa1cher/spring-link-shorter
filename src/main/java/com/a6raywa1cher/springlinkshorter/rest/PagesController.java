package com.a6raywa1cher.springlinkshorter.rest;

import com.a6raywa1cher.springlinkshorter.models.Link;
import com.a6raywa1cher.springlinkshorter.rest.request.CreateLinkRequest;
import com.a6raywa1cher.springlinkshorter.services.interfaces.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PagesController {
	@Value("${app.base-url}")
	private String baseUrl;
	private LinkService linkService;

	@Autowired
	public PagesController(LinkService linkService) {
		this.linkService = linkService;
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

	@GetMapping("")
	public String getForm(Model model) {
		model.addAttribute("req", new CreateLinkRequest());
		return "index";
	}

	@PostMapping(value = "/upload_link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String uploadLink(@ModelAttribute("req") @Valid CreateLinkRequest dto, Model model) {
		Link out = linkService.uploadLink(dto.getName(), dto.getForwardTo());
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("res", out);
		return "uploadSuccess";
	}
}
