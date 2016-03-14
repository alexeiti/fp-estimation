package com.ati.common.data;

import com.ati.common.domain.EmployeeInfo;

import java.util.Collection;

public interface EmployeeRepository {
    Collection<EmployeeInfo> getAllEmployees();

}
