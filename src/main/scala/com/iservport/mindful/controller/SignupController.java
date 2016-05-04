package com.iservport.mindful.controller;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.helianto.core.domain.Entity;
import org.helianto.install.service.EntityInstallStrategy;
import org.helianto.install.service.UserInstallService;
import org.helianto.security.internal.UserDetailsAdapter;
import org.helianto.security.service.AuthorizationChecker;
import org.helianto.security.service.EntityInstallService;
import org.helianto.user.repository.UserReadAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.iservport.mindful.social.SignInUtils;
import com.iservport.mindful.social.SignupForm;

/**
 * Controlador para cadastro via rede social.
 * 
 * @author mauriciofernandesdecastro
 */
//@Controller
public class SignupController {

	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
	
//	private final EntityInstallStrategy entityInstallStrategy;
//	private final EntityInstallService entityInstallService;
//	private final UserInstallService userInstallService;
//	private final AuthorizationChecker authorizationChecker;
//	private final ProviderSignInUtils providerSignInUtils;
//
//	/**
//	 * Constructor.
//	 *
//	 * @param entityInstallStrategy
//	 * @param entityInstallService
//	 * @param userInstallService
//	 * @param authorizationChecker
//	 */
//	@Inject
//	public SignupController(EntityInstallStrategy entityInstallStrategy
//			, EntityInstallService entityInstallService
//			, UserInstallService userInstallService
//			, AuthorizationChecker authorizationChecker) {
//		this.entityInstallStrategy = entityInstallStrategy;
//		this.entityInstallService = entityInstallService;
//		this.userInstallService = userInstallService;
//		this.authorizationChecker = authorizationChecker;
//		this.providerSignInUtils = new ProviderSignInUtils();
//	}
//
//	@RequestMapping(value="/signup", method=RequestMethod.GET)
//	public ModelAndView signupForm(WebRequest request) {
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
//		SignupForm form = null;
//		if (connection != null) {
//			System.err.println("signup get conection != null");
//			request.setAttribute("message", "WILL_SIGN_UP", WebRequest.SCOPE_REQUEST);
//			form = SignupForm.fromProviderUser(connection.fetchUserProfile());
//		} else {
//			form = new SignupForm();
//		}
//		return new ModelAndView("security/signup", "form", form);
//	}
//
//	/**
//	 * Sign-up form submission.
//	 *
//	 * @param form
//	 * @param formBinding
//	 * @param request
//	 */
//	@RequestMapping(value="/signup", method=RequestMethod.POST)
//	public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
//		if (formBinding.hasErrors()) {
//			logger.error("Invalid sign-up form submission: {}.", formBinding.toString());
//			return null;
//		}
//		System.err.println("signup post");
//		List<Entity> prototypeList = entityInstallStrategy.generateEntityPrototypes();
//		UserReadAdapter userReadAdapter = null;
//		for (Entity prototype: prototypeList) {
//			Entity entity = entityInstallService.installEntity(prototype);
//			userReadAdapter = createUser(entity, form, formBinding);
//		}
//		if (userReadAdapter != null) {
//			UserDetailsAdapter userDetails = authorizationChecker.updateAuthorities(new UserDetailsAdapter(userReadAdapter));
//			SignInUtils.signin(userDetails);
//			providerSignInUtils.doPostSignUp(userReadAdapter.getUserId()+"", request);
//			return "redirect:/";
//		}
//		return "redirect:/";
//	}
//
//	// internal helpers
//
//	/**
//	 * Criar e persistir um novo usuário.
//	 *
//	 * @param entity
//	 * @param form
//	 * @param formBinding
//	 */
//	private UserReadAdapter createUser(Entity entity, SignupForm form, BindingResult formBinding) {
//		try {
//			String principal = form.getPrincipal();
//			logger.debug("Try to create user for {} and {}", entity, principal);
//			UserReadAdapter user = userInstallService.installUser(entity, principal);
//			System.err.println(user.toString());
//			return user;
//		} catch (Exception e) {
//			e.printStackTrace();
//			formBinding.rejectValue("principal", "user.duplicateUsername", "already in use");
//			return null;
//		}
//	}

}
