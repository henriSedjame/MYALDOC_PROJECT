package org.myaldoc.authentication.service.connection.comparators;

import org.myaldoc.authentication.service.connection.models.Role;

import java.util.Comparator;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 13/11/2018
 * @Class purposes : .......
 */
public class Comparators {
    public static final Comparator<Role> ROLE_COMPARATOR = Comparator.comparing(Role::getRoleName);
}
