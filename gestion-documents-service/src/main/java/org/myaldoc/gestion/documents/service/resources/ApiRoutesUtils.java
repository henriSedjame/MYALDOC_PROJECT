package org.myaldoc.gestion.documents.service.resources;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 05/01/2019
 * @Class purposes : .......
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ApiRoutesUtils {
    static final String FICHIER_SAVE = "/fichier/save";
    static final String FICHIER_RETRIEVE = "/fichier/retrieve/{id}";
    static final String FICHIER_DELETE = "/fichier/delete/{id}";
}
