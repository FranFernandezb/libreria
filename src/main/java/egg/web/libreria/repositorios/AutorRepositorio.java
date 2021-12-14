
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT p FROM Autor p WHERE p.nombre LIKE :nombre")
   public List<Autor> buscarAutorPorNombre(@Param("nombre")String nombre);
    
}
