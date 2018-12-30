package org.myaldoc.gestion.documents.service.repositories;

import org.myaldoc.gestion.documents.service.models.Fichier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
public interface FichierRepository extends ReactiveMongoRepository<Fichier, String> {

}
