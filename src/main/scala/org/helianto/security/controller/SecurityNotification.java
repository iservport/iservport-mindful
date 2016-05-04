package org.helianto.security.controller;

import org.helianto.core.domain.Signup;
import org.helianto.user.domain.UserToken;
import org.helianto.user.internal.UserEmailAdapter;

/**
 * Security notification interface
 */
public interface SecurityNotification {

    Boolean sendSignUp(Signup signup);

    Boolean sendWelcome(Signup signup);

    Boolean sendRecovery(UserToken userToken);

    String sendConfirmation(UserEmailAdapter userConfirmation);

}
