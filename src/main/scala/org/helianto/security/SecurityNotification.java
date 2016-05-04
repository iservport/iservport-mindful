package org.helianto.security;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.user.domain.UserToken;
import org.helianto.user.internal.UserEmailAdapter;

/**
 * Security notification interface
 */
public interface SecurityNotification {

    Boolean sendSignUp(UserToken userToken);

    Boolean sendWelcome(UserToken userToken);

    Boolean sendRecovery(UserToken userToken);

    String sendConfirmation(UserEmailAdapter userConfirmation);

    Boolean sendAdminNotify(Entity entity, Identity identity);

}
