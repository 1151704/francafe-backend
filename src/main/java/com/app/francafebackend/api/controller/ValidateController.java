package com.app.francafebackend.api.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateController {

	@GetMapping("validate/")
	public boolean validate(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		
		if (principal != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null) {
				return true;
			}

		}
		return false;
	}

}
