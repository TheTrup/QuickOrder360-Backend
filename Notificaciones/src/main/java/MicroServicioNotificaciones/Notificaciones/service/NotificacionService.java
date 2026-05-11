package MicroServicioNotificaciones.Notificaciones.service;

import MicroServicioNotificaciones.Notificaciones.dto.UsuarioDTO;
import MicroServicioNotificaciones.Notificaciones.model.Notificaciones;
import MicroServicioNotificaciones.Notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Notificaciones enviarNotificacion(Notificaciones notificacion) {
        
        // 1. Validar que el Usuario exista (Microservicio Usuarios - 8081)
        UsuarioDTO usuario;
        try {
            usuario = restTemplate.getForObject("http://localhost:8081/api/v1/usuarios/" + notificacion.getUsuarioId(), UsuarioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: No se puede enviar la notificación porque el usuario " + notificacion.getUsuarioId() + " no existe.");
        }

        if (usuario == null) {
            throw new RuntimeException("Error: No se obtuvieron datos del usuario.");
        }

        // 2. Aquí iría la lógica real para conectarse a un servidor SMTP (Email) o API de SMS (Twilio).
        // Por ahora, simplemente lo guardamos en la base de datos simulando el envío.
        
        System.out.println("Enviando " + notificacion.getTipo() + " a " + usuario.getEmail() + "...");

        return repository.save(notificacion);
    }

}
