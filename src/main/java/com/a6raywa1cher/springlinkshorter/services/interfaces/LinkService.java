package com.a6raywa1cher.springlinkshorter.services.interfaces;

import com.a6raywa1cher.springlinkshorter.models.Link;

import java.util.Optional;

public interface LinkService {
	Optional<Link> getByReq(String req);

	Link uploadLink(String name, String forwardTo);
}
