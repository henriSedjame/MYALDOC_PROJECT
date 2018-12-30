package org.myaldoc.gestion.documents.service.models.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
@Data
@Builder
public class FichierDto {
    private String id;
    private String name;
    private String type;
    private String content;
}
