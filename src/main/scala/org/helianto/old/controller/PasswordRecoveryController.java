/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.helianto.old.controller;


import org.helianto.core.domain.Signup;
import org.helianto.old.service.ResponseService;
import org.helianto.old.service.SignUpService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.Locale;


/**
 * Password recovery controller.
 * 
 * @author mauriciofernandesdecastro
 */
//@Controller
//@RequestMapping(value="/recovery")
public class PasswordRecoveryController {

//	@Inject
	private ResponseService responseService;

//	@Inject
	private SignUpService signUpService;

//	@Inject
	private PasswordRecoverySender passwordRecoverySender;

//	@Inject
	private NotificationService notificationService;


	/**
	 * Password recovery e-mail.
	 */
	@RequestMapping(value={"/recovery", ""}, method={ RequestMethod.GET })
	public String recovery(String error, Model model, Locale locale) {
		if (error!=null && error.equals("1")) {
			model.addAttribute("error", "1");
		}
		return responseService.recoveryResponse(model, locale);
	}


	/**
	 * Send password retrieval e-mail.
	 * 
	 * @param model
	 * @param principal
	 */
	@RequestMapping(value="/send", method= {RequestMethod.POST, RequestMethod.GET })
	public String send(Model model, @RequestParam(required=false) String principal, Locale locale) {
		if (principal==null) {
			model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1");
			model.addAttribute("recoverFail", "true");
            return responseService.recoveryResponse(model, locale);
		}

		try {

            Signup signup = signUpService.createSignup(principal);

			if (signup!=null) {

				if(notificationService.sendRecovery(signup)) {
					model.addAttribute("emailRecoverySent", true);
				}
				else {
					// Caso falhe o envio, retorna ao formulário de e-mail
					model.addAttribute("emailRecoveryFailed", true);
                    return responseService.recoveryResponse(model, locale);
				}	
				return "redirect:/login/";
			}

			model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1");
			model.addAttribute("recoverFail", "true");

		} catch (Exception e) {

			model.addAttribute("recoverFailMsg", e.getMessage());
			model.addAttribute("recoverFail", "true");

		}

		return "redirect:/login/";

	}

	private class PasswordRecoverySender {
	}

	private class NotificationService {

		public boolean sendRecovery(Signup signup) {
			return false;
		}
	}
}
