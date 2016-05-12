package org.helianto.security.service

import javax.inject.Inject

import org.helianto.core.social.SignupForm
import org.helianto.security.internal.UserDetailsAdapter
import org.helianto.security.util.SignInUtils
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.context.request.WebRequest

/**
  * Provider sign-up service.
  */
@Service
class ProviderSignUpService @Inject()
(userDetailsService: UserDetailsService, providerSignInUtils: ProviderSignInUtils, responseService: ResponseService) {

  private val logger: Logger = LoggerFactory.getLogger(classOf[ProviderSignUpService])

  /**
    * Sign up or register.
    *
    * @param request
    * @param model
    */
  def signUpOrRegister(request: WebRequest, model: Model): String = {
    logger.info("signUpOrRegister")
    Option(providerSignInUtils.getConnectionFromSession(request)) match {
      case Some(connection) =>
        // social provider (likely Facebook) authorized
        val form = SignupForm.fromProviderUser(connection)
        model.addAttribute("form", form)
        Option(form.getPrincipal) match {
          case Some(principal) if (principal.indexOf('@') > 0) =>{
            // an existing user is trying to sign-in via provider
            try {
              Option(userDetailsService.loadUserByUsername(principal)) match {
                case Some(userDetails) => {
                  SignInUtils.signin(userDetails)
                  providerSignInUtils.doPostSignUp(userDetails.asInstanceOf[UserDetailsAdapter].getUserId + "", request)
                  "redirect:/home"
                }
                case None | _ => {
                  // and we have e-mail, but no user, go to register
                  responseService.registerResponse(model, request.getLocale)
                }
              }
            } catch {
              case e:Exception  => {
                responseService.registerResponse(model, request.getLocale)
              }
            }
          }
          case None => {
            logger.info("None email in SignupForm")
            // we still need to ask for e-mail
            model.addAttribute("hasPrincipal", false)
            responseService.registerResponse(model, request.getLocale)
          }
        }
      case None => {
        logger.info("No provider connection from session")
        // not a provider authorization, proceed to e-mail confirmation
        responseService.signUpResponse(model, request.getLocale)
      }
    }
  }

}
