package org.itson.entidades;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "productos")
public class Producto extends PanacheEntity {

    @Column(nullable = false, length = 100)
    public String nombre;

    @Column(nullable = false)
    public double precio;

    public Producto() {
    }

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
