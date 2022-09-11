package dev.tuanphan.quarkus.boundary;

import dev.tuanphan.quarkus.model.Address;
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
import javax.transaction.Transactional;
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
        return customerRepository.streamAll(Sort.by(QiosEntity.Customer.CUSTOMER_NUMBER));
    }

    @GET
    @Path("/{customerNumber}")
    public Uni<Customer> getCustomerByNumber(@PathParam("customerNumber") String customerNumber){
        return customerRepository
                .find(QiosEntity.Customer.CUSTOMER_NUMBER, customerNumber)
                .firstResult();
    }

    @POST
    @Transactional
    public Uni<Customer> createCustomer(
            @RequestBody Customer customer
    ){
        return Panache.withTransaction(()->
                customerRepository.persist(customer));
    }

    @POST
    @Path("/{customerNumber}/add-address")
    public Uni<Customer> addAddess(@PathParam("customerNumber") String customerNumber,
                                   @RequestBody Address address){
        Uni<Customer> customerUni = customerRepository.find(QiosEntity.Customer.CUSTOMER_NUMBER, customerNumber)
                .firstResult();

        return customerUni
                .call(customer -> Mutiny.fetch(customer.getAddresses()))
                .onItem().invoke(customer -> customer.addAddress(address))
                .onItem().transformToUni(customer -> Panache.withTransaction(
                        () -> customerRepository.persist(customer)
                ));
    }
}
