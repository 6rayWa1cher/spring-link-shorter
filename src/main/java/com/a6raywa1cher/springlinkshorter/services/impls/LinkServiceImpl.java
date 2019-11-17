package com.a6raywa1cher.springlinkshorter.services.impls;

import com.a6raywa1cher.springlinkshorter.models.Link;
import com.a6raywa1cher.springlinkshorter.repositories.LinkRepository;
import com.a6raywa1cher.springlinkshorter.services.exception.NameAlreadyTakenException;
import com.a6raywa1cher.springlinkshorter.services.interfaces.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {
	private LinkRepository linkRepository;

	@Autowired
	public LinkServiceImpl(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	@Override
	public Optional<Link> getByReq(String req) {
		return linkRepository.getByReq(req);
	}

	@Override
	public Link uploadLink(String name, String forwardTo) {
		Link link = new Link();
		if (linkRepository.getByReq(name).isPresent()) {
			throw new NameAlreadyTakenException();
		}
		link.setReq(name);
		link.setForwardingUrl(forwardTo);
		return linkRepository.save(link);
	}

	@Override
	public Page<Link> getPage(Pageable pageable) {
		return linkRepository.findAll(pageable);
	}
}
