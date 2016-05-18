package org.helianto.security.controller

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import org.helianto.install.service.SecurityNotification
import org.helianto.security.service._
import org.helianto.user.domain.UserToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import org.springframework.web.context.request.WebRequest

/**
  * Base classe to SignUpController.
  *
  * @author mauriciofernandesdecastro
  */
@Controller
@RequestMapping(value = Array("/signup"))
class SignUpController @Inject()
(queryService:UserTokenQueryService
 , commandService:UserTokenCommandService
 , notificationService:SecurityNotification
 , responseService:ResponseService
 , providerSignUpService: ProviderSignUpService
) {

  /**
    * Signup request page.
    *
    * @param model
    * @param contextId
    * @param principal
    * @param request
    */
  @RequestMapping(method = Array(RequestMethod.GET))
  def getSignupPage(model: Model, @RequestParam(defaultValue = "1") contextId: Integer, @RequestParam(required = false) principal: String, request: WebRequest):String =
    providerSignUpService.signUpOrRegister(request,model)

  /**
    * Quando o usuário confirma o pedido de inclusão no cadastro.
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
    * @param model
    * @param command
    * @param error
    * @param request
    */
  @RequestMapping(method = Array(RequestMethod.POST))
  def submitSignupPage(model: Model, @Valid command: UserToken, error: BindingResult, request: HttpServletRequest) = {
    val userToken = Option(request.getHeader("X-FORWARDED-FOR")) match {
      case Some(ip) => commandService.saveOrUpdate(command, ip)
      case None => commandService.saveOrUpdate(command, request.getRemoteAddr)
    }
    // TODO-SGNUP prevent double signup submission
    val userExists: Boolean = queryService.allUsersForIdentityAreValid(userToken)
    val emailSent: Boolean = userExists && notificationService.sendSignUp(queryService.createOrRefreshToken(userToken.getPrincipal, UserToken.TokenSources.SIGNUP.name()))
    model.addAttribute("emailSent", emailSent)
    responseService.confirmationResponse(model, request.getLocale)
  }

}