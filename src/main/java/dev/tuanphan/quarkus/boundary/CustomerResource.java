package dev.tuanphan.quarkus.boundary;

import dev.tuanphan.quarkus.model.Customer;
import dev.tuanphan.quarkus.model.QiosEntity;
import dev.tuanphan.quarkus.repository.CustomerRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/customers")
@ApplicationScoped
public class CustomerResource {
    @Inject
    CustomerRepository customerRepository;


    @GET
    public Multi<Customer> getAllCustomers() {
        return customerRepository.streamAll(Sort.by(QiosEntity.Customer.CUSTOMER_NUMBER))
                .call(customer -> Mutiny.fetch(customer.getAddresses()))
                .call(customer -> Mutiny.fetch(customer.getContracts()));
    }

    @GET
    @Path("/{customerNumber}")
    public Uni<Customer> getCustomerByNumber(@PathParam("customerNumber") String customerNumber){
        return customerRepository
                .find(QiosEntity.Customer.CUSTOMER_NUMBER, customerNumber)
                .firstResult()
                .call(customer -> Mutiny.fetch(customer.getAddresses()))
                .call(customer -> Mutiny.fetch(customer.getContracts()));
    }

    @POST
    public Uni<Customer> createCustomer(
            @RequestBody Customer customer
    ){
        return Panache.withTransaction(()->
                customerRepository.persist(customer));
    }
}
