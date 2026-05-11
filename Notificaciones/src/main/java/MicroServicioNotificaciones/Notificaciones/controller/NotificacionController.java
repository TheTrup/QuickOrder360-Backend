package MicroServicioNotificaciones.Notificaciones.controller;

import MicroServicioNotificaciones.Notificaciones.model.Notificaciones;
import MicroServicioNotificaciones.Notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @PostMapping
    public ResponseEntity<?> enviarNotificacion(@RequestBody Notificaciones notificacion) {
        try {
            Notificaciones nuevaNotificacion = service.enviarNotificacion(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNotificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
