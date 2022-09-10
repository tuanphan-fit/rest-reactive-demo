package dev.tuanphan.quarkus.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QiosEntity {
    @UtilityClass
    public static class Customer {
        public static final String ENTITY = "Customer";
        public static final String TABLE = "customers";
        public static final String CUSTOMER_NUMBER = "customer_number";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
    }
}
