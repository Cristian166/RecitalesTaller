package com.tallerwebi.dominio;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPerfil")
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {
    @Override
    public void editarPreferencias() {

    }
}
