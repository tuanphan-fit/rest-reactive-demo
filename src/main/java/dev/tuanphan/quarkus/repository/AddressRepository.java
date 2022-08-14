package dev.tuanphan.quarkus.repository;

import dev.tuanphan.quarkus.model.Address;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {
}
