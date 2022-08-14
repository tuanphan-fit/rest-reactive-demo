package dev.tuanphan.quarkus.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String customerNumber;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Contract> contracts = new ArrayList<>();

    public Customer addAddress(Address address){
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public Customer removeAddress(Address address){
        address.setCustomer(null);
        this.addresses.remove(address);
        return this;
    }

    public Customer addContract(Contract contract){
        contract.setCustomer(this);
        this.contracts.add(contract);
        return this;
    }

    public Customer removeContract(Contract contract){
        contract.setCustomer(null);
        this.contracts.remove(contract);
        return this;
    }
}
