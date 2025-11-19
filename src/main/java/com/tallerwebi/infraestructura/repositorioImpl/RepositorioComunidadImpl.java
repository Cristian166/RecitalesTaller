package com.tallerwebi.infraestructura.repositorioImpl;

import com.tallerwebi.dominio.entidades.Comunidad;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioComunidad;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("repositorioComunidad")
public class RepositorioComunidadImpl implements RepositorioComunidad{

    private SessionFactory sessionFactory;

    public RepositorioComunidadImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    //metodo y dentro sessionFactory.getCurrentSession().*VERBO A HACER*
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Comunidad guardarUnaComunidad(Comunidad comunidad){
        getCurrentSession().saveOrUpdate(comunidad);
        return comunidad;
    }

    @Override
    public Comunidad obtenerComunidadPorId(Long id) {

        return getCurrentSession().get(Comunidad.class, id);
    }

    @Override
    public void borrarComunidad(Long id){
        Comunidad comunidad = obtenerComunidadPorId(id);
        if(comunidad != null){
            getCurrentSession().delete(comunidad);
        }
    }

    @Override
    public Set<Comunidad> obtenerComunidadesUnidas(Long usuarioId) {
        List<Comunidad> comunidadList = getCurrentSession()
                .createQuery("SELECT c FROM Comunidad c JOIN c.usuarios u WHERE u.id = :usuarioId", Comunidad.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        return new HashSet<>(comunidadList);
    }

    @Override
    public Set<Comunidad> obtenerComunidadesSugeridas(Long usuarioId) {
        List<Comunidad> comunidadList = getCurrentSession()
                .createQuery("SELECT c FROM Comunidad c WHERE c.id NOT IN " +
                        "(SELECT c2.id FROM Comunidad c2 JOIN c2.usuarios u WHERE u.id = :usuarioId)", Comunidad.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        return new HashSet<>(comunidadList);
    }

    @Override
    public void abandonarComunidad(Usuario usuario, Long comunidadId) {
        Comunidad comunidad = obtenerComunidadPorId(comunidadId);

        if (comunidad != null) {
            Usuario session = getCurrentSession().get(Usuario.class, usuario.getId());
            comunidad.getUsuarios().remove(session);
            getCurrentSession().update(comunidad);
        }
    }
    @Override
    public Set<Usuario> obtenerMiembros(Long comunidadId) {
       List<Usuario> miembros = getCurrentSession()
                .createQuery("SELECT u FROM Comunidad c JOIN c.usuarios u WHERE c.id= :comunidadId", Usuario.class)
                .setParameter("comunidadId", comunidadId)
                .list();    
        return new HashSet<>(miembros);
    }

    @Override
    public long contarMiembrosDeComunidad(Long comunidadId){
        Long contador = (Long) getCurrentSession()
                .createQuery("SELECT COUNT(u) FROM Comunidad c JOIN c.usuarios u WHERE c.id = :comunidadId")
                .setParameter("comunidadId", comunidadId)
                .uniqueResult();
        return contador != null ? contador : 0;
    }
    @Override
    public Comunidad obtenerComunidadPorNombre(String nombre){
        return getCurrentSession()
                .createQuery("FROM Comunidad c WHERE c.nombre = :nombre", Comunidad.class)
                .setParameter("nombre",nombre)
                .uniqueResult();
    }
    @Override
    public Set<Comunidad> buscarComunidadesPorNombreYNoUnidas(String nombre, Usuario usuario) {
        return new HashSet<>(getCurrentSession()
                .createQuery("FROM Comunidad c WHERE c.nombre LIKE :nombre AND :usuario NOT IN (SELECT u FROM c.usuarios u)")
                .setParameter("nombre", "%" + nombre + "%")
                .setParameter("usuario", usuario)
                .list());
    }

}
