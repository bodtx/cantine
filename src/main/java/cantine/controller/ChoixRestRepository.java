package cantine.controller;

import cantine.db.Choix;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "choix", path = "choix")
public interface ChoixRestRepository extends PagingAndSortingRepository<Choix, Long> {

}
