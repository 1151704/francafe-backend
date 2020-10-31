package com.app.francafebackend.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.francafebackend.api.data.UsuarioApi;
import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Rol;
import com.app.francafebackend.api.model.TipoIdentificacion;
import com.app.francafebackend.api.model.Usuario;
import com.app.francafebackend.api.service.EmpresaService;
import com.app.francafebackend.api.service.RolService;
import com.app.francafebackend.api.service.TipoIdentificacionService;
import com.app.francafebackend.api.service.UsuarioService;
import com.app.francafebackend.api.utils.ValidationException;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private RolService rolService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private TipoIdentificacionService tipoIdService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${francafe.empresa}")
	private String nitEmpresa;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("all")
	public List<Usuario> getListUsuarios() {
		return service.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_PROV" })
	@PostMapping("")
	public ResponseEntity<Usuario> save(@RequestBody(required = false) UsuarioApi entrada) {

		System.out.println("save: " + entrada.getIdentificacion());

		Usuario usuario = service.findByIdentificacion(entrada.getIdentificacion());

		if (usuario == null) {
			try {
				Usuario usuarioSave;

				Rol rol = rolService.buscarPorRol(entrada.getRol());
				TipoIdentificacion tipoId = tipoIdService.buscarPorTipo(entrada.getTipoId());
				Empresa empresa = entrada.getRol().equals("ROLE_ADMIN") ? null
						: empresaService.buscarPorNit(nitEmpresa);

				usuario = new Usuario();
				usuario.setIdentificacion(entrada.getIdentificacion());
				usuario.setNombres(entrada.getNombres());
				usuario.setApellidos(entrada.getApellidos());

				usuario.setEmail(entrada.getEmail());
				usuario.setEnable(entrada.getEnable());
				usuario.setFechaActualizacion(new Date());

				usuario.setRol(rol);
				usuario.setEmpresa(empresa);
				usuario.setTipoId(tipoId);

				usuario.setPassword(passwordEncoder.encode(usuario.getIdentificacion()));
				usuario.setUsername(entrada.getIdentificacion());

				usuarioSave = service.save(usuario);
				return new ResponseEntity<>(usuarioSave, HttpStatus.OK);
			} catch (Exception e) {
				throw new ValidationException("Error de formulario", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException(
					"El usuario con identificación '" + entrada.getIdentificacion() + "' ya existe",
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("update")
	public ResponseEntity<Usuario> update(@RequestBody(required = false) UsuarioApi entrada) {

		if (entrada != null && entrada.getIdentificacion() != null && !entrada.getIdentificacion().isEmpty()
				&& entrada.getIdentificacion().length() > 5) {

			Usuario usuario = service.findByIdentificacion(entrada.getIdentificacion());

			if (usuario != null) {

				Rol rol = usuario.getRol();
				try {
					if (rol == null || !rol.getRol().equals(entrada.getRol())) {
						rol = rolService.buscarPorRol(entrada.getRol());
					}
				} catch (Exception e) {
				}

				Usuario usuarioEdit = new Usuario();

				// VALORES NO CAMBIANTES
				usuarioEdit.setId(usuario.getId());
				usuarioEdit.setIdentificacion(usuario.getIdentificacion());
				usuarioEdit.setTipoId(usuario.getTipoId());
				usuarioEdit.setNombres(usuario.getNombres());
				usuarioEdit.setApellidos(usuario.getApellidos());
				usuarioEdit.setPassword(usuario.getPassword());
				usuarioEdit.setUsername(usuario.getUsername());
				usuarioEdit.setFechaRegistro(usuario.getFechaRegistro());

				// VALORES CAMBIANTES
				usuarioEdit.setEmail(entrada.getEmail());
				usuarioEdit.setEnable(entrada.getEnable());
				usuarioEdit.setFechaActualizacion(new Date());
				usuarioEdit.setRol(rol);

				try {
					usuarioEdit = service.save(usuarioEdit);
				} catch (Exception e) {
					throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(usuarioEdit, HttpStatus.OK);
			} else {
				throw new ValidationException(
						"El usuario con identificación '" + entrada.getIdentificacion() + "' no existe",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("La identificación '" + entrada.getIdentificacion() + "' es invalida",
					HttpStatus.BAD_REQUEST);
		}

	}

	@Secured({ "ROLE_ADMIN", "ROLE_EPS" })
	@GetMapping("byUsuario/{identificacion}")
	public Usuario getUsuario(@PathVariable String identificacion) {
		return service.findByIdentificacion(identificacion);
	}

	@PostMapping("passwordEdit")
	public ResponseEntity<Boolean> editPassword(@RequestBody String passwordNew) {
		Boolean hecho = false;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Usuario usuarioActual = service.findByUsername(authentication.getName());

		if (usuarioActual != null) {

			if (passwordNew != null && passwordNew.trim().length() > 4) {

				usuarioActual.setPassword(passwordEncoder.encode(passwordNew));
				try {
					service.save(usuarioActual);
					hecho = true;
				} catch (Exception e) {
					throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
				}

			} else {
				throw new ValidationException("La contraseña no cumple con los criterios de seguiridad",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("Usuario inexistente", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(hecho, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_EPS" })
	@PostMapping("enableEdit/{identificacion}/{enableNew}")
	public ResponseEntity<Boolean> editEstado(@PathVariable String identificacion, @PathVariable Boolean enableNew) {
		Boolean hecho = false;

		Usuario usuario = service.findByIdentificacion(identificacion);

		if (usuario != null) {

			if (enableNew != null && enableNew != usuario.getEnable()) {

				usuario.setEnable(enableNew);
				try {
					service.save(usuario);
					hecho = true;
				} catch (Exception e) {
					throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
				}

			} else {
				throw new ValidationException("El estado no ha cambiado", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("Usuario inexistente", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(hecho, HttpStatus.OK);
	}

}
