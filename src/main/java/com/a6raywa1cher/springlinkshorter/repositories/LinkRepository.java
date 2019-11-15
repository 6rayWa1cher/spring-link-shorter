package com.a6raywa1cher.springlinkshorter.repositories;

import com.a6raywa1cher.springlinkshorter.models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
	Optional<Link> getByReq(String req);
}
