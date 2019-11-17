package com.a6raywa1cher.springlinkshortener.repositories;

import com.a6raywa1cher.springlinkshortener.models.Link;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {
	Optional<Link> getByReq(String req);
}
