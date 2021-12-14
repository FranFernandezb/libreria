
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT p FROM Editorial p WHERE p.nombre LIKE :nombre")
    public List<Editorial> buscarEdPorNombre(@Param("nombre")String edi);
    
   
    
    
    
    
    
    
    
}
