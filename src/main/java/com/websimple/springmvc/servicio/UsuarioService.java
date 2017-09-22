package com.websimple.springmvc.servicio;

import java.util.List;

import com.websimple.springmvc.modelo.Usuario;

public interface UsuarioService {

	Usuario buscarId(long id);
	
	Usuario buscarNombre(String nombre);
	
	void guardarUsuario(Usuario usuario);
	
	void actualizarUsuario(Usuario usuario);
	
	void borrarUsuarioId(long id);

	List<Usuario> buscarTodosUsuarios(); 
	
	void borrarTodosUsuarios();
	
	public boolean usuarioExiste(Usuario usuario);
}
