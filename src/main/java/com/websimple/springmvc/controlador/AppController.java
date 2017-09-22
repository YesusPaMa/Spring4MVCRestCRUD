package com.websimple.springmvc.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.websimple.springmvc.modelo.Usuario;
import com.websimple.springmvc.servicio.UsuarioService;


@RestController
public class AppController {
	
	@Autowired
	UsuarioService usuarioService; 
	
	//-------------------Mensaje de Bienvenida--------------------------------------------------------
	
	@RequestMapping("/")
	public String saludo() {
		return "<h2>Bienvenido este es un ejemplo REST con un CRUD muy simple.</h2>";
	}
	
	//-------------------Recuperar todos los usuarios--------------------------------------------------------
	
	@RequestMapping(value = "/usuario/", method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
		List<Usuario> users = usuarioService.buscarTodosUsuarios();
		if(users.isEmpty()){
			return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
	}
	
	//-------------------Recuperar un solo usuario--------------------------------------------------------
	
	@RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") long id) {
		System.out.println("Recuperar usuario con id " + id);
		Usuario usuario = usuarioService.buscarId(id);
		if (usuario == null) {
			System.out.println("Usuario con id " + id + " no encontrado.");
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	//-------------------Crear un usuario--------------------------------------------------------
	
	@RequestMapping(value = "/usuario/", method = RequestMethod.POST)
	public ResponseEntity<Void> crearUsuario(@RequestBody Usuario usuario, 	UriComponentsBuilder ucBuilder) {
		System.out.println("Crear usuario " + usuario.getNombre());

		if (usuarioService.usuarioExiste(usuario)) {
			System.out.println("Un usuario con nombre" + usuario.getNombre() + " ya existe.");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		usuarioService.guardarUsuario(usuario);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	//-------------------Actualizar un usuario--------------------------------------------------------
	
	@RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
		System.out.println("Actualizar usuario " + id);
		
		Usuario currentUser = usuarioService.buscarId(id);
		
		if (currentUser==null) {
			System.out.println("Usuario con id " + id + " no encontrado.");
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}

		currentUser.setNombre(usuario.getNombre());
		currentUser.setEdad(usuario.getEdad());
		currentUser.setSalario(usuario.getSalario());
		
		usuarioService.actualizarUsuario(currentUser);
		return new ResponseEntity<Usuario>(currentUser, HttpStatus.OK);
	}

	//-------------------Eliminar un usuario--------------------------------------------------------
	
	@RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Usuario> borrarUsuario(@PathVariable("id") long id) {
		System.out.println("Recuperar y eliminar usuario con id " + id);

		Usuario usuario = usuarioService.buscarId(id);
		if (usuario == null) {
			System.out.println("No se puede eliminar. Usuario con id " + id + " no encontrado.");
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}

		usuarioService.borrarUsuarioId(id);
		return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
	}
	
	//-------------------Borrar todos los usuarios-----------------------------------------------------
	
	@RequestMapping(value = "/usuario/", method = RequestMethod.DELETE)
	public ResponseEntity<Usuario> borrarTodosUsuarios() {
		System.out.println("Eliminar todos los usuarios");

		usuarioService.borrarTodosUsuarios();
		return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
	}
		
}
