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
class ApiRoutesUtils {

     static final String USER_UPDATE = "/user/update";
     static final String LOGIN = "/login";
     static final String USER_CREATE = "/user/create";
     static final String USER_RETRIEVE = "/user/retrieve/{username}";
     static final String USER_ACTIVATE_ID = "/user/activate/{id}";
     static final String USER_DELETE_ID = "/user/delete/{id}";
     static final String USER_UPDATE_PASSWORD = "/user/updatePassword";
     static final String AUTHENTICATE = "/user/authenticate";
}
