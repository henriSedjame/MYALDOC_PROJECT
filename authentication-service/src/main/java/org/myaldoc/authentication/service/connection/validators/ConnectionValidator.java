package org.myaldoc.authentication.service.connection.validators;

import org.myaldoc.authentication.service.connection.models.User;

import java.util.function.Predicate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/11/2018
 * @Class purposes : .......
 */

public interface ConnectionValidator extends Predicate<User> {

}
