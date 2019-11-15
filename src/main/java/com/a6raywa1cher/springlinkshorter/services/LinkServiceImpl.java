package com.a6raywa1cher.springlinkshorter.services;

import com.a6raywa1cher.springlinkshorter.models.Link;
import com.a6raywa1cher.springlinkshorter.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Link save(Link link) {
		return linkRepository.save(link);
	}
}
