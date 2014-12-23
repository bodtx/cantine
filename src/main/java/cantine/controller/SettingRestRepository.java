package cantine.controller;

import cantine.db.Setting;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "setting", path = "setting")
public interface SettingRestRepository extends PagingAndSortingRepository<Setting, String> {

}
