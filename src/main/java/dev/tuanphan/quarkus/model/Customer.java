package dev.tuanphan.quarkus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = QiosEntity.Customer.ENTITY)
@Table(name = QiosEntity.Customer.TABLE)
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = QiosEntity.Customer.CUSTOMER_NUMBER, unique = true, nullable = false)
    private String customerNumber;

    @Column(name = QiosEntity.Customer.FIRST_NAME)
    private String firstName;

    @Column(name = QiosEntity.Customer.LAST_NAME)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
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
