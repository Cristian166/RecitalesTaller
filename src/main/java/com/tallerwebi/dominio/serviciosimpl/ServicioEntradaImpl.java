package com.tallerwebi.dominio.serviciosimpl;

import com.tallerwebi.dominio.ServicioEntrada;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.entidades.Entrada;
import com.tallerwebi.dominio.entidades.Notificacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioEntrada;
import com.tallerwebi.infraestructura.DTOs.EntradaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioEntradaImpl implements ServicioEntrada {

    @Autowired
    private RepositorioEntrada repositorioEntrada;
    @Autowired
    private ServicioNotificacion servicioNotificacion;


    public ServicioEntradaImpl(RepositorioEntrada repositorioEntrada,
                                ServicioNotificacion servicioNotificacion){
        this.repositorioEntrada = repositorioEntrada;
        this.servicioNotificacion=servicioNotificacion;
    }

    @Override
    public void crearEntrada(Entrada entrada, Usuario usuario) {
        repositorioEntrada.guardarEntradaPorUsuario(entrada, usuario);
        Notificacion notificacion = new Notificacion();
        notificacion.setNombreNotificacion("¡Creaste una nueva entrada!");
        notificacion.setLink("/mis-entradas");
        notificacion.setDescripcionNotificacion("Creaste una nueva entrada, vas a poder ver esta y todas las que tengas en:");
        notificacion.setUsuario(usuario);
        
        servicioNotificacion.agregarNotificacion(usuario, notificacion);
    }

    @Override
    public List<Entrada> obtenerTodasMisEntradas() {
        return repositorioEntrada.obtenerEntradas();
    }

    

    @Override
    public void eliminarEntrada(Long id) {
        repositorioEntrada.eliminarPorId(id);
    }

    @Override
    public void validarEntrada(Long id, Usuario usuario) {
        validarEntrada(id, usuario, (int) (Math.random() * 10));
        
    }



    public void validarEntrada(Long id, Usuario usuario, int random) {
        Entrada entradaEncontrada = repositorioEntrada.buscarPorId(id);
        

        if (entradaEncontrada != null && random >= 4) {
            entradaEncontrada.setValidada(true);
            repositorioEntrada.guardarEntradaPorUsuario(entradaEncontrada, usuario);
        }

        List<Entrada> entradasValidadas = repositorioEntrada.obtenerEntradasValidadas();
        Integer contadorAsistenciaALugar=0;
        for(Entrada entradaValidada : entradasValidadas){

            if (entradaValidada.getUsuario().getId().equals(usuario.getId())) {

                if (entradaValidada.getLugar().equals(entradaEncontrada.getLugar())) {
                    contadorAsistenciaALugar++;
                }
            }
        }

        // Si asistió más de 3 veces
        if (contadorAsistenciaALugar % 3 == 0){
            Notificacion notificacion = new Notificacion();
            notificacion.setNombreNotificacion("¡Nuevo codigo de descuento!");
            notificacion.setDescripcionNotificacion("Asististe " + contadorAsistenciaALugar +" veces a " + entradaEncontrada.getLugar()+" .Tu codigo de descuento es: " + codigoRandom()+ " podes ingresarlo en tu próxima compra en cualquier ticketera.");
            notificacion.setLink("/mis-entradas");
            notificacion.setUsuario(usuario);
            
            servicioNotificacion.agregarNotificacion(usuario, notificacion);
        }


    }

    @Override
    public Entrada buscarPorId(Long id) {
       return repositorioEntrada.buscarPorId(id);
    }

    @Override
    public List<EntradaDTO> obtenerEntradasPorUsuario(Usuario usuario) {
       List<Entrada> entradas = repositorioEntrada.obtenerEntradasPorUsuario(usuario);
        return entradas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EntradaDTO convertirADTO(Entrada entrada) {
        boolean validada = entrada.getValidada() != null ? entrada.getValidada() : false;
        return new EntradaDTO(
                entrada.getId(),
                entrada.getNombreRecital(),
                entrada.getLugar(),
                entrada.getFecha(),
                entrada.getHorario(),
                entrada.getSeccion(),
                entrada.getImagen(),
                validada
        );
    }

    public String codigoRandom() {
    String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    char[] codigo = new char[6];

        for (int i = 0; i < 6; i++) {
            codigo[i] = caracteres.charAt(random.nextInt(caracteres.length()));
        }

    return new String(codigo);
    }

    
}
