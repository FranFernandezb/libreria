
package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Usuario;
import egg.web.libreria.enums.Roles;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService{
    
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public Usuario save(String username, String password, String password2) throws ErrorServicio {
        Usuario usuario = new Usuario();
        
        if (username == null || username.isEmpty()) {
            throw new ErrorServicio("El nombre de usuario no puede estar vacío.");
        }
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new ErrorServicio("La contraseña no puede estar vacía.");
        }
        if (!password.equals(password2)) {
            throw new ErrorServicio("Las contraseñas deben coincidir");
        }
        if(!usuarioRepositorio.buscarUsuarioPorNombre(username).isEmpty()){
            throw new ErrorServicio("Este usuario ya está registrado.");
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        usuario.setRol(Roles.USER);
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Usuario usuario = usuarioRepositorio.buscarUsuarioPorNombre(username).get(0);
            User user;
            
            List<GrantedAuthority> authorities = new ArrayList<>();
            
            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
            
            return new User(username, usuario.getPassword(), authorities);
        }catch(Exception e){
            throw new UsernameNotFoundException("El usuario solicitado no se encuentra registrado.");
        }
        
    }
    
    
}
