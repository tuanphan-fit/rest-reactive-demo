package dev.tuanphan.quarkus.boundary;

import dev.tuanphan.quarkus.model.Customer;
import dev.tuanphan.quarkus.repository.CustomerRepository;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
@ApplicationScoped
public class CustomerResource {
    @Inject
    CustomerRepository customerRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Customer>> getAllCustomers() {
        return customerRepository.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<Customer> createCustomer(
            @RequestBody Customer customer
    ){
        var result = customerRepository.persist(customer);
        customerRepository.flush();
        return result;
    }
}
