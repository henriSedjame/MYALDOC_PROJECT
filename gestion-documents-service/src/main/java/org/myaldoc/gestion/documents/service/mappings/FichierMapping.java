package org.myaldoc.gestion.documents.service.mappings;

import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.models.dto.FichierDto;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
public interface FichierMapping {

    /**
     * MAPPING FICHIER TO FICHIERDTO
     * @param fichier
     * @return
     */
    FichierDto toDTO(Fichier fichier);
}
