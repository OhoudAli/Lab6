package com.example.lab6.Controller;


import com.example.lab6.Api.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();


    @GetMapping("/get")
    public ArrayList<Employee> getEmployee(){
        return employees;
    }


    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors){

        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        employees.add(employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("add new employee successfully"));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index){
        if( index <0 || index>= employees.size()){
            return ResponseEntity.status(400).body(new ApiResponse("Not existing index"));

        }
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("remove employee successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@RequestBody @Valid Employee employee,@PathVariable String id, Errors errors){

        if(employee.getId().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("id is not found"));
        }
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        for(Employee e : employees){
            if(e.getId().equals(id));
            employees.set(employees.indexOf(e), employee);
        }
       // employees.set(employees.indexOf(id),employee);
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

    }

    @GetMapping("/search/{position}")
    public ArrayList<Employee> searchEmployee(@PathVariable String position) {
        ArrayList<Employee> searchByPosition = new ArrayList<>();
        if (!position.equalsIgnoreCase("supervisor") && !position.equalsIgnoreCase("coordinator")) {
            return null;
        }
          for (Employee e : employees) {
              if(e.getPosition().equals(position)) {
                  searchByPosition.add(e);
              }
          }
        return searchByPosition;

    }
     @GetMapping("/getByAge/{age}")
    public ResponseEntity getEmployeeByAge(@PathVariable int age,Errors errors ){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        ArrayList<Employee> employees1 = new ArrayList<>();
        for(Employee e: employees){
            if(e.getAge()==age){
                employees1.add(e);
            }
        }
        return ResponseEntity.status(200).body(employees1);


    }

    @GetMapping("/noAnnualLeave")
    public ArrayList<Employee> noAnnualLeave(){
        ArrayList<Employee> employeeNoAnnualLeave = new ArrayList<>();
        for(Employee e: employees){
            if(e.getAnnualLeave() == 0){
                employeeNoAnnualLeave.add(e);
            }
            return employeeNoAnnualLeave;
        }
        return null;
    }

    @PutMapping("/applyAnnualLeave/{id}")
    public ResponseEntity applyAnnualLeave(@RequestBody Employee employee, @PathVariable String id){

        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                if (e.isOnLeave()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Employee is already on leave"));
                }
                if (e.getAnnualLeave() <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No remaining annual leave"));
                }

                e.setOnLeave(true);
                e.setAnnualLeave(e.getAnnualLeave() - 1);
                return ResponseEntity.ok(new ApiResponse("Annual leave applied successfully"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found"));
    }

//    public ResponseEntity promoteEmployee(){
//
//    }






}
