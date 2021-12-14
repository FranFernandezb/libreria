
package egg.web.libreria.controllers;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.servicios.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    EditorialService editorialService;

    @GetMapping("/list")
    public String listarEditorial(Model model) {
        model.addAttribute("editoriales", editorialService.listarEditorial());
        return "editorial-list";
    }

    @GetMapping("/form")  //ESTO ME VA A CARGAR LA VISTA DEL FORMULARIO.
    public String crearEditorial(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Editorial> optional = editorialService.listById(id);
            if (optional.isPresent()) {
                model.addAttribute("editorial", optional.get());
            } else {
                return "redirect:/editorial/list";
            }
        } else {
            model.addAttribute("editorial", new Editorial());
        }
        return "editorial-form";
    }

    //CUANDO DE CLICK A CREAR, DEBE VENIR A LA URL DE ACA:
    @PostMapping("/save")
    public String guardarEditorial(RedirectAttributes redirectAttributes, @ModelAttribute Editorial editorial) throws ErrorServicio {
        try {
            editorialService.saveEditorial(editorial);
             redirectAttributes.addFlashAttribute("success", "Editorial guardada con éxito");
        } catch (ErrorServicio ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage()); //para enviar el error al redirect de abajo
        }
        return "redirect:/editorial/list";
    }

    @GetMapping("/delete")
    public String eliminarAutor(@RequestParam(required = true) String id) {
        editorialService.deleteById(id);
        return "redirect:/editorial/list";
    }
    
    
    
}
