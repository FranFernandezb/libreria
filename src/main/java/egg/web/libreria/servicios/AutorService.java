package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public Autor saveAutor(Autor autor) throws ErrorServicio {

        if (autor.getNombre().isEmpty() || autor.getNombre() == null || autor.getNombre().length() < 3) {
            throw new ErrorServicio("El autor debe tener un nombre de más de 3 caracteres");

        }
        return autorRepositorio.save(autor);
    }

    @Transactional
    public Autor saveAutor(String nombre) throws ErrorServicio {

        Autor autor = new Autor();
        autor.setNombre(nombre);
        return autorRepositorio.save(autor);
    }

    private void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Debe ingresar un nombre");
        }
    }

    public List<Autor> listarAutor() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Autor> optional = autorRepositorio.findById(id);
        if (optional.isPresent()) {
            autorRepositorio.delete(optional.get());
        }
    }

    public Optional<Autor> listById(String id) {
        return autorRepositorio.findById(id);
    }

    public Autor findById(Autor autor) {
        Optional<Autor> optional = autorRepositorio.findById(autor.getId());
        if (optional.isPresent()) {
            autor = optional.get();
        }
        return autor;
    }

}
