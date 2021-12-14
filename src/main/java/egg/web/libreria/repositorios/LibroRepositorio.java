
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,String>{
    
    @Query("SELECT p FROM Libro p WHERE p.titulo LIKE :titulo")
    public List<Libro> buscarLibroPorNombre(@Param("titulo")String nombreN);
    
    @Query("SELECT p FROM Libro p WHERE p.titulo LIKE :q OR p.autor.nombre LIKE :q OR p.editorial.nombre LIKE :q")
    List<Libro> findAllByQ(@Param("q") String q);
    

}
