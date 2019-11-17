package com.a6raywa1cher.springlinkshorter.repositories;

import com.a6raywa1cher.springlinkshorter.models.Link;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {
	Optional<Link> getByReq(String req);
}
