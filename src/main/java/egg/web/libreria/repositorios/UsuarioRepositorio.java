
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    @Query("SELECT p FROM Usuario p WHERE p.username LIKE :username")
    public List<Usuario> buscarUsuarioPorNombre(@Param("username")String username);
}
