package org.myaldoc.reactive.authentication.service.resource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 01/01/2019
 * @Class purposes : .......
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiRoutesUtils {

    public static final String USER_UPDATE = "/user/update";
    public static final String LOGIN = "/login";
    public static final String USER_CREATE = "/user/create";
    public static final String USER_ACTIVATE_ID = "/user/activate/{id}";
    public static final String USER_DELETE_ID = "/user/delete/{id}";
    public static final String USER_UPDATE_PASSWORD = "/user/updatePassword";
}
