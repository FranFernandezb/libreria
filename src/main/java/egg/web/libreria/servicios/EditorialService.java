package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public Editorial saveEditorial(Editorial editorial) throws ErrorServicio {

        if (editorial.getNombre().isEmpty() || editorial.getNombre() == null) {
            throw new ErrorServicio("La editorial debe tener un nombre");
        }
        editorial.setAlta(true);
        return editorialRepositorio.save(editorial);
    }

    @Transactional
    public Editorial saveEditorial(String nombre) throws ErrorServicio {

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        return editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditorial() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Editorial> optional = editorialRepositorio.findById(id);
        if (optional.isPresent()) {
            editorialRepositorio.delete(optional.get());
        }
    }

    public Optional<Editorial> listById(String id) {
        return editorialRepositorio.findById(id);
    }

    public Editorial findById(Editorial editorial) {
        Optional<Editorial> optional = editorialRepositorio.findById(editorial.getId());
        if (optional.isPresent()) {
            editorial = optional.get();
        }
        return editorial;
    }

}
