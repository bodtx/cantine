package cantine.controller;

import cantine.db.Fayot;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "fayot", path = "fayot")
public interface FayotRestRepository extends PagingAndSortingRepository<Fayot, String> {

}
