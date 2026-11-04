package org.itson.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.itson.dtos.ProductoDTO;


@RegisterRestClient(configKey = "producto-api")
@Path("/productos")
public interface IProductoServiceClient {

    @GET
    @Path("/{id}")
    ProductoDTO getById(@PathParam("id") Long id);
}
