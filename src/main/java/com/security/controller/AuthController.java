package com.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.service.UserDetailService;

@Controller
public class AuthController {
	
	@Autowired
	UserDetailService userDetailService;

	@RequestMapping("/auth/login/form")
	public String form() {
		return "/auth/login";
	}

	@RequestMapping("/auth/login/success")
	public String success(ModelMap model) {
		model.addAttribute("message", "Login success!");
		return "redirect:/home/index";
	}

	@RequestMapping("/auth/login")
	public String error(ModelMap model, @RequestParam("error") Optional<Boolean> error) {
		Boolean check = error.orElse(false);
		if (check) {
			model.addAttribute("message", "Login Fail!");
		}
		return "/auth/login";
	}

	@RequestMapping("/auth/login/error")
	public String errorSocial(ModelMap model) {
		model.addAttribute("message", "Login Fail!");
		return "/auth/login";
	}

//	@RequestMapping("/logout")
//	public String logout() {
//		return "";
//	}

	@RequestMapping("/auth/logout/success")
	public String logoutSuccess(ModelMap model) {
//		model.addAttribute("message", "Logout success!");
		return "redirect:/home/index";
	}

	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth2) {
		userDetailService.loginFromOAuth2(oauth2);
		return "redirect:/home/index";
	}
}
