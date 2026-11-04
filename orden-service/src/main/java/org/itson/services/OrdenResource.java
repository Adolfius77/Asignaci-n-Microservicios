package org.itson.services;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.itson.dtos.OrdenRequest;
import org.itson.dtos.ProductoDTO;
import org.itson.entidades.Orden;

@Path("/ordenes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdenResource {

    @Inject
    @RestClient
    IProductoServiceClient productoService;

    @GET
    public List<Orden> listar() {
        return Orden.listAll();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Orden orden = Orden.findById(id);
        if (orden == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(orden).build();
    }

    @POST
    @Transactional
    public Response crearOrden(OrdenRequest request) {
        if (request == null || request.productoId == null || request.cantidad <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Se requiere productoId y una cantidad mayor a 0"))
                    .build();
        }

        ProductoDTO producto;
        try {
            producto = productoService.getById(request.productoId);
        } catch (WebApplicationException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "No existe el producto con id " + request.productoId))
                    .build();
        } catch (ProcessingException e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(Map.of("error", "El servicio de productos no esta disponible"))
                    .build();
        }

        Orden orden = new Orden();
        orden.productoId = producto.id;
        orden.nombreProducto = producto.nombre;
        orden.precioUnitario = producto.precio;
        orden.cantidad = request.cantidad;
        orden.total = producto.precio * request.cantidad;
        orden.fecha = LocalDateTime.now();
        orden.persist();

        return Response.status(Response.Status.CREATED).entity(orden).build();
    }
}
