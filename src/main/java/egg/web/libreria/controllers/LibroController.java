package egg.web.libreria.controllers;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.servicios.AutorService;
import egg.web.libreria.servicios.EditorialService;
import egg.web.libreria.servicios.LibroService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    LibroService libroService;

    @Autowired
    AutorService autorService;

    @Autowired
    EditorialService editorialService;

    @GetMapping("/list")
    public String listarLibros(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("libros", libroService.listarLibrosByQ(q));
        } else {
            model.addAttribute("libros", libroService.listarLibros());
        }
        return "libro-list";
    }

    @GetMapping("/form")  //ESTO ME VA A CARGAR LA VISTA DEL FORMULARIO.
    public String crearLibro(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Libro> optional = libroService.listarPorId(id);
            if (optional.isPresent()) {
                model.addAttribute("libro", optional.get());
            } else {
                return "redirect:/libro/list";
            }
        } else {
            model.addAttribute("libro", new Libro());
        }
        model.addAttribute("autores", autorService.listarAutor());
        model.addAttribute("editoriales", editorialService.listarEditorial());
        return "libro-form";
    }

    @PostMapping("/save")
    public String guardarLibro(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Libro libro) {
        try {
            libroService.saveLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Libro guardado con éxito");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/libro/list";
    }

    @GetMapping("/delete")
    public String eliminarLibro(@RequestParam(required = true) String id) {
        libroService.deleteById(id);
        return "redirect:/libro/list";
    }

}
