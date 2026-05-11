package MicroServicioNotificaciones.Notificaciones.repository;

import MicroServicioNotificaciones.Notificaciones.model.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificaciones, Long> {

    List<Notificaciones> findByUsuarioId(Long usuarioId);

}
