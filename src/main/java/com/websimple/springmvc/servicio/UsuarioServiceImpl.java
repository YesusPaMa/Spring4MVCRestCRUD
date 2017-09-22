package com.websimple.springmvc.servicio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websimple.springmvc.modelo.Usuario;

@Service("usuarioServices")
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Usuario> usuarios;
	
	static{
		usuarios = dummyUsuarios();
	}
	
	@Override
	public Usuario buscarId(long id) {
		for(Usuario usuario : usuarios){
			if(usuario.getId() == id){
				return usuario;
			}
		}
		return null;
	}

	@Override
	public Usuario buscarNombre(String nombre) {
		for(Usuario usuario : usuarios){
			if(usuario.getNombre().equalsIgnoreCase(nombre)){
				return usuario;
			}
		}
		return null;
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		usuario.setId(counter.incrementAndGet());
		usuarios.add(usuario);		
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		int index = usuarios.indexOf(usuario);
		usuarios.set(index, usuario);
	}

	@Override
	public void borrarUsuarioId(long id) {
		for (Iterator<Usuario> iterator = usuarios.iterator(); iterator.hasNext(); ) {
		    Usuario usuario = iterator.next();
		    if (usuario.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	@Override
	public List<Usuario> buscarTodosUsuarios() {
		return usuarios;
	}

	@Override
	public void borrarTodosUsuarios() {
		usuarios.clear();
	}

	@Override
	public boolean usuarioExiste(Usuario usuario) {
		return buscarNombre(usuario.getNombre())!=null;
	}
	
	
	private static List<Usuario> dummyUsuarios(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario(counter.incrementAndGet(),"Jesus",25, 70000));
		usuarios.add(new Usuario(counter.incrementAndGet(),"Alan",20, 50000));
		usuarios.add(new Usuario(counter.incrementAndGet(),"Jessica",24, 30000));
		usuarios.add(new Usuario(counter.incrementAndGet(),"Carolina",27, 40000));
		return usuarios;
	}
	
}
