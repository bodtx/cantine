package cantine.controller;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cantine.db.Choix;

@RepositoryRestResource(collectionResourceRel = "choix", path = "choix")
public interface ChoixRestRepository extends PagingAndSortingRepository<Choix, Long> {

}
