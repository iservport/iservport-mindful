//package org.helianto.old.controller;
//
//import org.helianto.core.domain.Identity;
//import org.helianto.core.repository.IdentityRepository;
//import org.helianto.security.domain.IdentitySecret;
//import org.helianto.security.internal.UserAuthentication;
//import org.helianto.security.repository.IdentitySecretRepository;
//import org.helianto.security.service.IdentityCryptoService;
//import org.helianto.old.service.ResponseService;
//import org.helianto.user.domain.User;
//import org.helianto.user.repository.UserRepository;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.inject.Inject;
//import java.util.Locale;
//
///**
// * Spring MVC password recovery controller.
// *
// * @author mauriciofernandesdecastro
// */
////@Controller
////@RequestMapping(value="/change")
//public class PasswordChangeController {
//
////    @Inject
//    private IdentityRepository identityRepository;
//
////    @Inject
//    private UserRepository userRepository;
//
////    @Inject
//    private IdentityCryptoService identityCrypto;
//
////    @Inject
//    private ResponseService responseService;
//
////    @Inject
//    private IdentitySecretRepository identitySecretRepository;
//
//    /**
//     * Password change form (when user is already registered).
//     *
//     * @param userAuthentication
//     * @param model
//     */
//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value={"/self", "self"}, method= {RequestMethod.POST, RequestMethod.GET })
//    public String change(UserAuthentication userAuthentication, Model model, Locale locale) {
//        try {
//            User user = userRepository.findOne(userAuthentication.getUserId());
//            Identity identity = user.getIdentity();
//            if (identity!=null) {
//                return responseService.changeResponse(model, locale);
//            }
//            model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1");
//            model.addAttribute("recoverFail", "true");
//        } catch (Exception e) {
//            model.addAttribute("recoverFailMsg", e.getMessage());
//            model.addAttribute("recoverFail", "true");
//        }
//
//        return "redirect:/login/";
//
//    }
//
//    /**
//     * Password change submission.
//     *
//     * @param model
//     * @param email
//     * @param password
//     */
//    @RequestMapping(value="/submit", method= RequestMethod.POST)
//    public String recover(Model model, @RequestParam String email,
//                          @RequestParam String password,
//                          @RequestParam(required=false) String oldP,
//                          @RequestParam(required=false, defaultValue="0") int type,
//                          Locale locale) {
//
//        Identity identity = identityRepository.findByPrincipal(email);
//        if (identity!=null) {
//            if(type == 1){
//                return ckeckPassword(model, oldP, identity, locale);
//            }else{
//                return ckeckPassword(model, password, identity, locale);
//            }
//        }
//        model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1");
//        model.addAttribute("recoverFail", "true");
//
//        return responseService.changeResponse(model, locale);
//    }
//
//    private String ckeckPassword(Model model, String pass, Identity identity, Locale locale) {
//        IdentitySecret identitySecret = identitySecretRepository.findByIdentityKey(identity.getPrincipal());
//        model.addAttribute("email", identity.getPrincipal());
//
//        if(BCrypt.checkpw(pass, identitySecret.getIdentitySecret())){
//            model.addAttribute("recoverFail", "false");
//            model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.0");
//            return responseService.changeResponse(model, locale);
//        }
//        identityCrypto.changeIdentitySecret(identity.getPrincipal(), pass);
//        model.addAttribute("recoverFail", "false");
//        return "redirect:/app/home/";
//    }
//
//    /**
//     * Receive e-mail confirmation and respond with form (unauthenticated users).
//     *
//     * @param model
//     * @param token
//     */
//    @RequestMapping(value="/return/{token}", method=RequestMethod.GET)
//    public String mail(Model model, @PathVariable String token, Locale locale) {
//
//        int identityId = identityCrypto.decriptAndValidateToken(token);
//        Identity identity = identityRepository.findOne(identityId);
//        if (identity!=null) {
//            model.addAttribute("email", identity.getPrincipal());
//            return responseService.changeResponse(model, locale);
//
//        }
//        model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1");
//        model.addAttribute("recoverFail", "true");
//
//        return responseService.changeResponse(model, locale);
//    }
//
//}
