package egg.web.libreria.controllers;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.servicios.AutorService;
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
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    AutorService autorService;

    @GetMapping("/list")
    public String listarAutores(Model model) {
        model.addAttribute("autores", autorService.listarAutor());
        return "autor-list";
    }

    @GetMapping("/form") //ESTO CARGA LA VISTA DEL FORM 
    public String crearAutor(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Autor> optional = autorService.listById(id);
            if (optional.isPresent()) {
                model.addAttribute("autor", optional.get());
            } else {
                return "redirect:/autor/list";
            }
        }else{
            model.addAttribute("autor", new Autor());
        }
        return "autor-form";
    }

    //ACA VIENE CUANDO EL TIPO APRETA CREAR AUTOR.
    @PostMapping("/save")
    public String guardarAutor(RedirectAttributes redirectAttributes,@ModelAttribute Autor autor) {
        try {
            autorService.saveAutor(autor);
            redirectAttributes.addFlashAttribute("success", "Autor guardado con éxito");
        } catch (ErrorServicio ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage()); //para enviar el error al redirect de abajo
        }
        return "redirect:/autor/list";
    }

    @GetMapping("/delete")
    public String eliminarAutor(@RequestParam(required = true) String id) {
        autorService.deleteById(id);
        return "redirect:/autor/list";
    }
}
