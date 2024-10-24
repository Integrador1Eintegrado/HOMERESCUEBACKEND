package pe.edu.utp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Autoincrementable
    private Long idAuto;

    private String id;  // ID del servicio, tipo VARCHAR

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    @NotNull
    private Proveedor proveedor;  // Relación con Proveedor

    @NotBlank
    @Size(max = 255)
    private String descripcion;  // Descripción del servicio

    @NotBlank
    @Size(max = 20)
    private String estado = "disponible";  // Estado del servicio

    // Constructor vacío
    public Servicio() {
    }

    public Servicio(String id, Proveedor proveedor, String descripcion) {
        this.id = id;
        this.proveedor = proveedor;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id='" + id + '\'' +
                ", proveedor=" + proveedor +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servicio)) return false;
        Servicio servicio = (Servicio) o;
        return id.equals(servicio.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}