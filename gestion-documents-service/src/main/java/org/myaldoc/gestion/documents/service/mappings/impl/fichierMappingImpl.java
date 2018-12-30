package org.myaldoc.gestion.documents.service.mappings.impl;

import org.myaldoc.gestion.documents.service.mappings.FichierMapping;
import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.models.dto.FichierDto;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
@Component
public class fichierMappingImpl implements FichierMapping {
    /**
     * MAPPING FICHIER TO FICHIERDTO
     *
     * @param fichier
     * @return
     */
    @Override
    public FichierDto toDTO(Fichier fichier) {
        return FichierDto.builder()
                .id(fichier.getId())
                .name(fichier.getName())
                .content(fichier.getContent())
                .build();
    }
}
