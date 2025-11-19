package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioEncuesta;
import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Encuesta;
import com.tallerwebi.dominio.entidades.Publicacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.DTOs.PublicacionDTO;

public class ControladorComunidadTest {

    @Mock
    private ServicioComunidad servicioComunidad;

    @Mock
    private ServicioPublicacion servicioPublicacion;

    @Mock
    private ServicioEncuesta servicioEncuesta;

    @Mock
    private HttpSession sessionMock;

    @Mock
    private Model modelMock;

    @InjectMocks
    private ControladorComunidad controladorComunidad; 

    @Mock
    private Usuario usuarioMock;

    @Mock
    private Comunidad comunidadMock;

    @BeforeEach
    public void init(){
        
        MockitoAnnotations.openMocks(this); //inicializo mock
   
        when(usuarioMock.getId()).thenReturn(1L);
        when(comunidadMock.getId()).thenReturn(1L);
        when(comunidadMock.getUsuarioCreador()).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        when(servicioEncuesta.obtenerEncuestaActiva(anyLong())).thenReturn(new Encuesta());
    }

    @Test
    public void debeMostrarComunidadesUnidasYSugeridas(){
        Set<Comunidad> comunidadesUnidas = new HashSet<>();
        Set<Comunidad> comunidadesSugeridas = new HashSet<>();
        
        comunidadesUnidas.add(comunidadMock);
        comunidadesSugeridas.add(comunidadMock);

        when(servicioComunidad.listarComunidadesUnidas(1L)).thenReturn(comunidadesUnidas);
        when(servicioComunidad.listarComunidadesSugeridas(1L)).thenReturn(comunidadesSugeridas);

        String vista = controladorComunidad.mostrarComunidades(modelMock, sessionMock);

        verify(modelMock).addAttribute("comunidadesUnidas", comunidadesUnidas);
        verify(modelMock).addAttribute("comunidadesSugeridas", comunidadesSugeridas);
        assertEquals("comunidades", vista);
    }

    @Test
    public void debeRedirigirCuandoLaComunidadNoExiste(){
        Long comunidadId = 1L;

        when(servicioComunidad.obtenerComunidad(comunidadId)).thenReturn(null);

        String vista = controladorComunidad.verComunidad(comunidadId, modelMock);

        assertEquals("redirect:/comunidades", vista);
    }
    
    @Test
    public void debeRedirigirYmostrarUnaComunidadSeleccionadaSinEncuesta(){
        Long comunidadId = 1L;
        
        Comunidad comunidad = new Comunidad();

        comunidad.setId(comunidadId);
        comunidad.setNombre("Comunidad Prueba");
        comunidad.setDescripcion("Descripcion Prueba");

        List<PublicacionDTO> publicacionesDTO = Arrays.asList(new PublicacionDTO(), new PublicacionDTO());

        when(servicioComunidad.obtenerComunidad(comunidadId)).thenReturn(comunidad);
        when(servicioPublicacion.listarPublicacionesDTOPorComunidad(comunidadId)).thenReturn(publicacionesDTO);
        when(servicioEncuesta.obtenerEncuestaActiva(comunidadId)).thenReturn(null);

        String vista = controladorComunidad.verComunidad(comunidadId, modelMock);

        verify(modelMock).addAttribute(eq("usuario"), eq(usuarioMock)); // Usamos eq() para los valores literales
        verify(modelMock).addAttribute(eq("comunidad"), eq(comunidad));
        verify(modelMock).addAttribute(eq("publicaciones"), eq(publicacionesDTO));
        verify(modelMock).addAttribute(eq("nuevaPublicacion"), any(Publicacion.class)); // Usamos any() para la clase Publicacion

        verify(modelMock, never()).addAttribute(eq("encuesta"), any(Encuesta.class));

        assertEquals("comunidad", vista);
    }

    @Test
    public void debePoderIngresarAUnaComunidad(){

        String vistaEsperada = "redirect:/comunidad/1";
        Long comunidadId = 1L;
        
        String vista = controladorComunidad.ingresarAUnaComunidad(comunidadId);

        verify(servicioComunidad).unirseAComunidad(usuarioMock, comunidadId);

        assertEquals(vistaEsperada, vista);
    }

    @Test
    public void debePoderAbandonarUnaComunidad(){
        
        String vistaEsperada = "redirect:/comunidades";
        Long comunidadId = 1L;

        String vista = controladorComunidad.abandonarUnaComunidad(comunidadId);

        verify(servicioComunidad).abandonarComunidad(usuarioMock, comunidadId);
        
        assertEquals(vistaEsperada, vista);
    }

    @Test
    public void debePoderEliminarUnaComunidad(){
        
        String vistaEsperada = "redirect:/comunidades";
        Long comunidadId=1l;

        String vista = controladorComunidad.eliminarUnaComunidad(comunidadId);

        verify(servicioComunidad).eliminarComunidad(comunidadId);

        assertEquals(vistaEsperada, vista);
    }

    @Test
    public void debeMostrarElFormularioParaCrearUnaComunidad(){
 
    Usuario usuarioPremium = mock(Usuario.class);
    when(usuarioPremium.getEsPremium()).thenReturn(true);
    when(sessionMock.getAttribute("usuario")).thenReturn(usuarioPremium);
 
    String vista = controladorComunidad.mostrarFormularioCrearComunidad(modelMock);

    verify(modelMock).addAttribute("usuario", usuarioPremium);  
    verify(modelMock).addAttribute(eq("nuevaComunidad"), any(Comunidad.class)); 
    
    assertEquals("crear-comunidad", vista);
    }

    @Test
    public void debeCrearUnaComunidadCorrectamente() throws IOException{

        Usuario usuarioPremium = mock(Usuario.class);
        when(usuarioPremium.getEsPremium()).thenReturn(true);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioPremium);

        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("Comunidad creada test");

        MultipartFile imagenArchivoMock = mock(MultipartFile.class);
        when(imagenArchivoMock.isEmpty()).thenReturn(false);
        when(imagenArchivoMock.getOriginalFilename()).thenReturn("imagen.jpg");
        when(imagenArchivoMock.getBytes()).thenReturn(new byte[]{});

        when(servicioComunidad.existeComunidadPorNombre(comunidad.getNombre())).thenReturn(false);

        String vista = controladorComunidad.crearUnaComunidad(comunidad, imagenArchivoMock, modelMock);

        verify(servicioComunidad).crearComunidad(comunidad, usuarioPremium);

        assertEquals("redirect:/comunidades", vista);
    }

    @Test
    public void debeMostrarErrorSiComunidadYaExiste() throws Exception {
        
        Usuario usuarioPremium = mock(Usuario.class);
        
        when(usuarioPremium.getEsPremium()).thenReturn(true);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioPremium);
        
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("Comunidad Test");

        MultipartFile imagenArchivoMock = mock(MultipartFile.class);
        when(imagenArchivoMock.isEmpty()).thenReturn(true);

        when(servicioComunidad.existeComunidadPorNombre(comunidad.getNombre())).thenReturn(true);

        String vista = controladorComunidad.crearUnaComunidad(comunidad, imagenArchivoMock, modelMock);

        verify(servicioComunidad, never()).crearComunidad(any(), any());  // No debería llamar al servicio porque hubo un error

        // Paso 4: Verificar que se añadió el error al modelo
        verify(modelMock).addAttribute("error", "Ya existe una comunidad con este nombre.");
        verify(modelMock).addAttribute("nuevaComunidad", comunidad);

        // Paso 5: Verificar que la vista es "crear-comunidad"
        assertEquals("crear-comunidad", vista);
    }

    @Test
    public void debeCrearComunidadConImagen() throws Exception {
        
        Usuario usuarioPremium = mock(Usuario.class);
        
        when(usuarioPremium.getEsPremium()).thenReturn(true);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioPremium);

        
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("Comunidad con Imagen");
        
        
        MultipartFile imagenArchivo = mock(MultipartFile.class);
        when(imagenArchivo.isEmpty()).thenReturn(false);  
        when(imagenArchivo.getOriginalFilename()).thenReturn("imagen.jpg");
        when(imagenArchivo.getBytes()).thenReturn(new byte[]{1, 2, 3});

        when(servicioComunidad.existeComunidadPorNombre(comunidad.getNombre())).thenReturn(false); 

        String vista = controladorComunidad.crearUnaComunidad(comunidad, imagenArchivo, modelMock);

        assertNotNull(comunidad.getImagen());

        verify(servicioComunidad).crearComunidad(comunidad, usuarioPremium);

        assertEquals("redirect:/comunidades", vista);
    }

}
