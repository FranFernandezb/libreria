package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;
    
    @Transactional
    public Libro saveLibro(Libro libro) throws ErrorServicio {
        validar(libro);
        libro.setAlta(true);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        return libroRepositorio.save(libro);
    }

    private void validar(Libro libro) throws ErrorServicio {
        if (libro.getISBN() == null) {
            throw new ErrorServicio("El ISBN debe contener 10 o 13 numeros.");
        }
        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo.");
        }
        if (libro.getAnio() == null || Integer.toString(libro.getAnio()).length() != 4) {
            throw new ErrorServicio("El año no puede ser nulo y debe contener 4 digitos.");
        }
        if (libro.getEjemplares() == null) {
            throw new ErrorServicio("Debe haber minimo un ejemplar.");
        }
        if (libro.getEjemplaresPrestados() == null) {
            throw new ErrorServicio("Debe haber un numero, si no hay poner 0.");
        }
        if (libro.getAutor() == null) {
            throw new ErrorServicio("El libro debe tener un autor.");
        } else {
            libro.setAutor(autorService.findById(libro.getAutor()));
        }
        if (libro.getEditorial() == null) {
            throw new ErrorServicio("El libro debe tener una editorial.");
        } else {
            libro.setEditorial(editorialService.findById(libro.getEditorial()));
        }
        if (libro.getEjemplares() < libro.getEjemplaresPrestados()) {
            throw new ErrorServicio("No puede haber mas libros prestados que los ejemplares totales.");
        }
    }

    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    public List<Libro> listarLibrosByQ(String q) {
        return libroRepositorio.findAllByQ("%" + q + "%");
    }

    public Optional<Libro> listarPorId(String id) {
        return libroRepositorio.findById(id);

    }

    @Transactional
    public void deleteById(String id) {
        Optional<Libro> optional = libroRepositorio.findById(id);
        if (optional.isPresent()) {
            libroRepositorio.delete(optional.get());
        }
    }

}
