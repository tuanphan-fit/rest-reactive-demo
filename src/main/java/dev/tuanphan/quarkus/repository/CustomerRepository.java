package dev.tuanphan.quarkus.repository;

import dev.tuanphan.quarkus.model.Customer;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
}
