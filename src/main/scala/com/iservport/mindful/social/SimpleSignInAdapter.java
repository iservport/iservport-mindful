package com.iservport.mindful.social;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.helianto.security.internal.UserDetailsAdapter;
import org.helianto.user.repository.UserReadAdapter;
import org.helianto.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Adaptador para sign-in.
 * 
 * @author mauriciofernandesdecastro
 */
public class SimpleSignInAdapter implements SignInAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SimpleSignInAdapter.class);
	
	private final RequestCache requestCache;
	
	private  UserRepository userRepository;

	/**
	 * Constructor.
	 * 
	 * @param requestCache
	 * @param userRepository
	 */
	public SimpleSignInAdapter(RequestCache requestCache, UserRepository userRepository) {
		this.requestCache = requestCache;
		this.userRepository = userRepository;
	}
	
	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
		int userId = 0;
		try {
			userId = Integer.parseInt(localUserId);
			UserReadAdapter user = userRepository.findAdapter(userId);
			UserDetailsAdapter details = new UserDetailsAdapter(user);
			SignInUtils.signin(details);
		}
		catch (Exception e) {
			logger.error("Unable to associate user details to id {}.", userId);
			logger.error("Error is ", e);
		}
		return extractOriginalUrl(request);
	}

	private String extractOriginalUrl(NativeWebRequest request) {
		HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAutheticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}
		 
	private void removeAutheticationAttributes(HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}