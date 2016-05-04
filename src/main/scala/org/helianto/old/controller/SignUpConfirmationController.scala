package org.helianto.old.controller

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import org.helianto.core.domain.Signup
import org.helianto.old.service.{ResponseService, SignUpService}
import org.helianto.security.controller.SecurityNotification
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}

/**
  * Confirmation controller.
  *
  * <p>
  *   Envia um e-mail de confirmação para o cliente. As condições são as seguintes:
  * </p>
  * <ol>
  *   <li>Verifica se não é uma submissão dupla e notifica adequadamente.</li>
  *   <li>Caso não exista a entidade, cria e convida o solicitante a tornar-se o administrador.</li>
  *   <li>Caso exista a entidade, verifica se o solicitante já não é usuário;</li>
  *   <li>caso não seja, convida-o para participar da entidade,</li>
  *   <li>caso seja e esteja ativo, notifica-o e redireciona para o login,</li>
  *   <li>caso inativo, informa-o para solicitar readmissão junto ao administrador.</li>
  * </ol>
  *
  * @author mauriciofernandesdecastro
  */
//@Controller
//@RequestMapping(value = Array("/signup/confirm"))
class SignUpConfirmationController //@Inject()
(signUpService:SignUpService, notificationService:SecurityNotification, responseService:ResponseService)
{

  /**
    * Signup submission
    *
    * @param model
    * @param command
    * @param error
    * @param request
    */
  @RequestMapping(method = Array(RequestMethod.POST))
  def confirm(model: Model, @Valid command: Signup, error: BindingResult, request: HttpServletRequest): String = {
    val signup = Option(request.getHeader("X-FORWARDED-FOR")) match {
      case Some(ip) => signUpService.saveSignup(command, ip)
      case None => signUpService.saveSignup(command, request.getRemoteAddr)
    }
    // TODO-SGNUP prevent double signup submission
    val userExists: Boolean = signUpService.allUsersForIdentityAreValid(signup)
    model.addAttribute("userExists", userExists)
    if (userExists) {
      if(notificationService.sendSignUp(signup))
          model.addAttribute("emailSent", "true")
      else
          model.addAttribute("emailSent", "false")
    }
    model.addAllAttributes(signup.createMapFromForm)
    responseService.confirmationResponse(model, request.getLocale)
  }

}
