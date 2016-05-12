///*
// * Copyright 2010 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.helianto.old.controller;
//
//import org.helianto.core.domain.Entity;
//import org.helianto.core.domain.Identity;
//import org.helianto.core.domain.Operator;
//import org.helianto.core.domain.Signup;
//import org.helianto.core.repository.IdentityRepository;
//import org.helianto.core.repository.OperatorRepository;
//import org.helianto.core.repository.SignupRepository;
//import org.helianto.install.service.EntityInstallStrategy;
//import org.helianto.old.service.ResponseService;
//import org.helianto.security.domain.IdentitySecret;
//import org.helianto.security.internal.UserDetailsAdapter;
//import org.helianto.security.service.AuthorizationChecker;
//import org.helianto.security.service.IdentityCryptoService;
//import org.helianto.security.util.SignInUtils;
//import org.helianto.user.domain.User;
//import org.joda.time.DateMidnight;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.web.ProviderSignInUtils;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.WebRequest;
//
//import javax.inject.Inject;
//import java.util.List;
//import java.util.Locale;
//
///**
// * Base class to verify controllers.
// *
// * Subclasses must implement the prototype generation strategy accordingly.
// *
// * @author mauriciofernandesdecastro
// */
////@Controller
////@RequestMapping("/signup/verify")
//public class SignUpVerificationController {
//
//	private static final Logger logger = LoggerFactory.getLogger(SignUpVerificationController.class);
//
//	public static final String PWD_VERIFY = "/security/password";
//
////	@Inject
//	private ResponseService responseService;
//
////	@Inject
//	private OperatorRepository contextRepository;
//
////	@Inject
//	private SignupRepository signupRepository;
//
////	@Inject
//	private IdentityRepository identityRepository;
//
////	@Inject
//	private IdentityCryptoService identityCrypto;
//
////	@Inject
//	private AuthorizationChecker authorizationChecker;
//
////	@Inject
//	private UsersConnectionRepository connectionRepository;
//
////	@Inject
//	private ConnectionFactoryLocator connectionFactoryLocator;
//
////	@Inject
//	private EntityInstallStrategy entityInstallService;
//
////	@Inject
////	private NotificationService senderService;
//
//	/**
//	 * Create = true if identity not yet exists.
//	 *
//	 * @param principal
//	 */
//	@RequestMapping(method=RequestMethod.GET, params={"principal"})
//	@ResponseBody
//	public String exists(@RequestParam String principal) {
//		Identity identity = identityRepository.findByPrincipal(principal);
//		return String.format("{\"canCreate\":%b}", identity==null);
//	}
//
//	/**
//	 * Verify token received after user confirmation e-mail.
//	 *
//	 * @param model
//	 * @param confirmationToken
//     */
//	@RequestMapping(method= RequestMethod.GET, params={"confirmationToken"})
//	public String getVerificationPage(Model model, @RequestParam String confirmationToken, Locale locale) {
//		int identityId = findPreviousSignupAttempt(confirmationToken, 5);
//		if (identityId!=0) {
//			Identity  identity = identityRepository.findOne(identityId);
//			return createPassword(model, identity, locale);
//		}
//		else {
//			model.addAttribute("userConfirmed", false);
//		}
//		return "/signup";
//	}
//
//	/**
//	 * Find a valid previous signup attempt.
//	 *
//	 * @param confirmationToken
//	 * @param expirationLimit
//	 */
//	protected int findPreviousSignupAttempt(String confirmationToken, int expirationLimit) {
//		Signup signup = signupRepository.findByToken(confirmationToken);
//		if (signup!=null) {
//			if (expirationLimit>0 && signup.getIssueDate()!=null) {
//				DateMidnight expirationDate = new DateMidnight(signup.getIssueDate()).plusDays(expirationLimit + 1);
//				logger.debug("Previous signup attempt valid to {} ", expirationDate);
//				if (expirationDate.isAfterNow()) {
//					return identityRepository.findByPrincipal(signup.getPrincipal()).getId();
//				}
//			}
//		}
//		logger.debug("Unable to detect any valid previous signup attempt with token {} ", confirmationToken);
//		return 0;
//	}
//
//	/**
//	 * Authorize.
//	 *
//	 * @param user
//	 * @param request
//	 */
//	public void authorize(User user, WebRequest request) {
//		if (user != null) {
//			UserDetailsAdapter userDetails = authorizationChecker.updateAuthorities(new UserDetailsAdapter(user));
//			SignInUtils.signin(userDetails);
//			ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//			providerSignInUtils.doPostSignUp(user.getId()+"", request);
//		}
//	}
//
//	/**
//	 * Create password after successful verification.
//	 *
//	 * @param model
//	 * @param token
//	 */
//	@RequestMapping(value="/password", method= RequestMethod.GET)
//	public String createPassword(Model model, @RequestParam String token, Locale locale) {
//		int identityId = identityCrypto.decriptAndValidateToken(token);
//		Identity  identity = identityRepository.findOne(identityId);
//		return createPassword(model, identity, locale);
//	}
//
//	/**
//	 * Internal create password.
//	 *
//	 * @param model
//	 * @param identity
//	 */
//	protected String createPassword(Model model, Identity  identity, Locale locale) {
//
//		if(identity!=null){
//			model.addAttribute("email", entityInstallService.removeLead(identity.getPrincipal()));
//			// prevents duplicated submission
//			IdentitySecret identitySecret = identityCrypto.getIdentitySecretByPrincipal(identity.getPrincipal());
//			if(identitySecret != null){
//				return responseService.createResponse(model, locale);
//			}
//		}else{
//			return "redirect:/signup";
//		}
//
//        return responseService.createResponse(model, locale);
//	}
//
//	/**
//	 * Create user and password.
//	 *
//	 * @param model
//	 * @param email
//	 * @param password
//	 */
//	@RequestMapping(value="/createPass", method= RequestMethod.POST)
//	public String postVerificationPage(Model model, @RequestParam(defaultValue="1") Integer contextId
//			, @RequestParam String email, @RequestParam String password, Locale locale) {
//
//		Identity identity = identityRepository.findByPrincipal(email);
//		model.addAttribute("userExists", true);
//		logger.debug("User {} exists",identity.getPrincipal());
//		IdentitySecret identitySecret = identityCrypto.getIdentitySecretByPrincipal(identity.getPrincipal());
//		if (identitySecret==null) {
//			logger.info("Will install identity secret for {}.", identity);
//			identitySecret = identityCrypto.createIdentitySecret(identity, password, false);
//		}else{
//			logger.info("Will change identity secret for {}.", identity);
//			identitySecret = identityCrypto.changeIdentitySecret(identity.getPrincipal(),password);
//		}
//		Operator context = contextRepository.findOne(contextId);
//		Signup signup = signupRepository.findByContextIdAndPrincipal(contextId, identity.getPrincipal());
////		senderService.sendWelcome(signup);
//		List<Entity> prototypes = entityInstallService.generateEntityPrototypes(signup);
//		entityInstallService.createEntities(context, prototypes, identity);
//		model.addAttribute("passError", "false");
//		return responseService.loginResponse(model, locale);
//
//
//	}
//
//}
