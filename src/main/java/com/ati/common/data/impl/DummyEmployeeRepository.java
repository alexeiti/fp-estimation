package com.ati.common.data.impl;

import com.ati.common.data.EmployeeRepository;
import com.ati.common.domain.EmployeeInfo;

import java.util.ArrayList;
import java.util.Collection;

public class DummyEmployeeRepository implements EmployeeRepository {
    @Override
    public Collection<EmployeeInfo> getAllEmployees() {

        Collection<EmployeeInfo> employeeList = new ArrayList<>();
        employeeList.add(new EmployeeInfo("adsa","asdsf"));
        employeeList.add(new EmployeeInfo("adsa123","gdfg"));
        employeeList.add(new EmployeeInfo("dfg","asdsf"));
        employeeList.add(new EmployeeInfo("ad123sa","cvb"));
        return employeeList;
    }
}
