
package egg.web.libreria.controllers;

import egg.web.libreria.excepciones.ErrorServicio;
import egg.web.libreria.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroController {
    
    @Autowired
    private UsuarioService usuarioService;
 
    @GetMapping("") //Las comillas significa que va a ir a /registro
    public String registro() {
        return "registro";
    }

    @PostMapping("")
    public String registroSave(Model model, @RequestParam String username,@RequestParam String password,@RequestParam String password2) {
        
        try {
            usuarioService.save(username, password, password2);
            return "redirect:/"; //me redirecciona a lo que pongo despues de los : 
        } catch (ErrorServicio ex) {
            model.addAttribute("error", ex.getMessage());
            return "registro";
        } 
        
    }
    
    
}
