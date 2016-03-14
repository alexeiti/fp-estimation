package com.ati.common.data;

import com.ati.common.data.impl.DummyEmployeeRepository;

public class CommonRepositoryFactory {

    private static EmployeeRepository employeeRepository = new DummyEmployeeRepository();

    public static final EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }
}
