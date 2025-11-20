package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;


	@BeforeEach
	public void init(){
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}
	
	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
		
		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	@Test
	public void registrarUsuarioSiNoExisteDeberiaCrearUsuarioYRedirigirAlLogin() throws UsuarioExistente {
		
		// preparacion
		Usuario usuarioMock = new Usuario();
		usuarioMock.setEmail("test@correo.com");
		usuarioMock.setPassword("123456");
		usuarioMock.setTelefono("1123456789");

		MockMultipartFile imagenArchivo = new MockMultipartFile(
            "imagenArchivo", 
            "testimagen.jpg", 
            "image/jpeg", 
            "contenido de imagen".getBytes()
        );

		when(servicioLoginMock.registrar(usuarioMock)).thenReturn(usuarioMock);

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Model model = new ExtendedModelMap();

		// ejecucion
		String vista = controladorLogin.registrarUsuario(usuarioMock, "123456", imagenArchivo, "+54", model, bindingResult);

		// validacion
		assertThat(vista, equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(usuarioMock);
	}


	@Test
	public void registrarUsuarioSiUsuarioExisteDeberiaVolverAlFormularioConError() throws UsuarioExistente {
		Usuario usuarioMock = new Usuario();
		usuarioMock.setEmail("test@correo.com");
		usuarioMock.setPassword("123456");
		usuarioMock.setTelefono("1123456789");
	
		doThrow(new UsuarioExistente("El usuario con este correo electrónico ya está registrado"))
			.when(servicioLoginMock).registrar(usuarioMock);

		BindingResult bindingResult = mock(BindingResult.class);
    	when(bindingResult.hasErrors()).thenReturn(false);
		
		Model model = mock(Model.class);

		String vista = controladorLogin.registrarUsuario(usuarioMock, "123456", null, "+54", model, bindingResult);

		assertThat(vista, equalToIgnoringCase("nuevo-usuario")); // Debe devolver la vista de nuevo-usuario
    	verify(model).addAttribute("errorEmail", "El usuario con este correo electrónico ya está registrado"); // Verifica que se agregue el mensaje de error
    	verify(servicioLoginMock, times(1)).registrar(usuarioMock); // Verifica que se haya intentado registrar el usuario
	}
}
