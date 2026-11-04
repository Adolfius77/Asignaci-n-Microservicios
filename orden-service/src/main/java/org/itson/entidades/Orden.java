package org.itson.entidades;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "ordenes")
public class Orden extends PanacheEntity {

    @Column(nullable = false)
    public Long productoId;

    public String nombreProducto;
    public double precioUnitario;

    @Column(nullable = false)
    public int cantidad;

    public double total;

    public LocalDateTime fecha;
}
