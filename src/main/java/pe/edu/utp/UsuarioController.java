package pe.edu.utp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.FirestoreService;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private FirestoreService firestoreService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String id) {
        try {
            Usuario usuario = firestoreService.getUsuario(id);
            return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
        } catch (ExecutionException e) {
            logger.error("Error al obtener el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            logger.error("La operación fue interrumpida al obtener el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addUsuario(@RequestBody Usuario usuario) {
        // Validar que el ID no sea nulo o vacío
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            logger.error("El ID del usuario no puede ser nulo o vacío");
            return ResponseEntity.status(400).build(); // 400 Bad Request
        }

        try {
            firestoreService.addUsuario(usuario.getId(), usuario);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (ExecutionException e) {
            logger.error("Error al agregar el usuario: {}", e.getMessage());
            return ResponseEntity.status(500).build(); // 500 Internal Server Error
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            logger.error("La operación fue interrumpida al agregar el usuario: {}", e.getMessage());
            return ResponseEntity.status(500).build(); // 500 Internal Server Error
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        try {
            firestoreService.updateUsuario(id, usuario);
            return ResponseEntity.ok().build();
        } catch (ExecutionException e) {
            logger.error("Error al actualizar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            logger.error("La operación fue interrumpida al actualizar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        try {
            firestoreService.deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (ExecutionException e) {
            logger.error("Error al eliminar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            logger.error("La operación fue interrumpida al eliminar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
