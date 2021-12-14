
package egg.web.libreria;

import egg.web.libreria.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Esto me permite autorizar las URL
public class Security extends WebSecurityConfigurerAdapter{
    
    //Acá voy a trabajar con la autenticación de los usuarios y con las autorizaciones.
    
    
    //UserDatailService --> Me va a proveer un método LoadByUsername --> Lo aplico en UsuarioService.
    @Autowired
    private UsuarioService usuarioService;
    
    
    //Metodo para configurar la autenticación
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{ //este AMB me sirve para crear mis autenticaciones
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    
    //Configuración de las peticiones http

    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http.authorizeRequests().antMatchers("/css/*","/img/*","/js/*").permitAll() //ESTO ES PARA QUE ENTRE A MI PAGINA DERECHO SIN PEDIRME PASSWORD DE CONSOLA.
               .and().formLogin()
                     .loginPage("/registro")
                     .usernameParameter("username") //esto pq en mi html le puse name=username.
                     .passwordParameter("password") //igual que arriba
                     .defaultSuccessUrl("/")
                     .loginProcessingUrl("/logincheck") //Adónde nos lleva cuando queremos loguearnos. Por default va logincheck
                     .failureUrl("/login?error=error") //Que pasa si la contraseña o algo falla cuando nos logueamos.
                     .permitAll()
                
                .and().logout()
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/login?logout")
                .and().csrf().disable(); //algo de seguridad para que no permita que mientras estamos logueados desde otra pagina nos manden request
    }
    
    
}
