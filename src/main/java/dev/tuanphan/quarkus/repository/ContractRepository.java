package dev.tuanphan.quarkus.repository;

import dev.tuanphan.quarkus.model.Contract;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContractRepository implements PanacheRepository<Contract> {
}
