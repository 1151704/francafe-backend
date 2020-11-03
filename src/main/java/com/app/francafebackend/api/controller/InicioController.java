package com.app.francafebackend.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {

	@GetMapping("/")
	public String inicio(HttpServletRequest request) {

		String ip = request.getRemoteAddr();

		return "API REST FRANCAFE -> " + ip;
	}

}
