package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		model.addAttribute("message", "This is home page!");
		return "/home/index";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model) {
		model.addAttribute("message", "This is introduction page!");
		return "/home/index";
	}
	
	@RequestMapping("/admin")
	public String admin(ModelMap model) {
		model.addAttribute("message", "Hello admin!");
		return "/home/index";
	}
	
	@RequestMapping("/user")
	public String user(ModelMap model) {
		model.addAttribute("message", "Hello staff!");
		return "/home/index";
	}
	
	@RequestMapping("/authenticated")
	public String authenticated(ModelMap model) {
		model.addAttribute("message", "Hello authenticated user!");
		return "/home/index";
	}
	
	@RequestMapping("/access/denied")
	public String error(ModelMap model) {
		model.addAttribute("message", "May` cut'!");
		return "/home/index";
	}
}
