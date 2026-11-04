package org.itson.services;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.itson.entidades.Producto;


@ApplicationScoped
public class DatosIniciales {

    @Transactional
    void alIniciar(@Observes StartupEvent evento) {
        if (Producto.count() == 0) {
            new Producto("Laptop", 15999.00).persist();
            new Producto("Mouse", 299.50).persist();
            new Producto("Teclado", 749.99).persist();
        }
    }
}
