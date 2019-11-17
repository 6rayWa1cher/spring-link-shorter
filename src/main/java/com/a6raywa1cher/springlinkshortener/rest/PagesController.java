package com.a6raywa1cher.springlinkshortener.rest;

import com.a6raywa1cher.springlinkshortener.models.Link;
import com.a6raywa1cher.springlinkshortener.rest.request.CreateLinkRequest;
import com.a6raywa1cher.springlinkshortener.services.interfaces.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@GetMapping("/list")
	@Transactional
	public String getPage(@RequestParam(defaultValue = "0") int page, Model model) {
		System.out.println(page);
		Page<Link> linkPage = linkService.getPage(PageRequest.of(page, 10, Sort.Direction.DESC, "id"));
		model.addAttribute("links", linkPage.stream().collect(Collectors.toList()));
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("pages", linkPage.getTotalPages());
		model.addAttribute("pageNumber", page);
		return "list";
	}

	@PostMapping(value = "/upload_link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String uploadLink(@ModelAttribute("req") @Valid CreateLinkRequest dto, Model model) {
		Link out = linkService.uploadLink(dto.getName(), dto.getForwardTo());
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("res", out);
		return "uploadSuccess";
	}
}
