package com.example.theatre.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theatre/login")
public class LoginController {

	@PostMapping("signIn")
	public Map<String, Object> signIn(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "rememberMe", required = true) boolean rememeberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememeberMe);
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		try {
			SecurityUtils.getSubject().login(token);
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			message = "user does not exist...";
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			message = "user password is incorrect...";
		} catch (LockedAccountException e) {
			e.printStackTrace();
			message = "user is locked...";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			message = "failed authenticate...";
		} finally {
			token.clear();
		}
		result.put("username", username);
		result.put("password", password);
		result.put("message", message);
		return result;
	}

}
