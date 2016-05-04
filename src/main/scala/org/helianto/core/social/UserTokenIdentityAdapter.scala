package org.helianto.core.social

import org.helianto.core.domain.Identity
import org.helianto.user.domain.UserToken

/**
  * Created by Eldevan Nery Junior on 02/05/16.
  */
case class UserTokenIdentityAdapter(userToken: UserToken) {

  def getIdentityFromUserToken: Identity = {
    val identity: Identity =  new Identity(this.userToken.getPrincipal)
    identity.setDisplayName(s"${this.userToken.getFirstName} ${this.userToken.getLastName}")
    identity.getPersonalData.setFirstName(this.userToken.getFirstName)
    identity.getPersonalData.setLastName(this.userToken.getLastName)
    identity
  }

}
