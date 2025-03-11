package com.example.lab6.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

/// ///////////recheck here
    @NotEmpty(message = "The must not be empty")
    @Size(min = 3, message = "The length of the id must be more than 2")
    private String id;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 5, message = "The name must be more than 4 character")
    private String name;

    @Email
    @NotEmpty(message = "The email must not be empty")
    private String email;

    @NotEmpty(message = "The phone number must not be empty")
    @Pattern(regexp = "^05\\d{8}$", message = "The phone number must start with 05 and contain 10 numbers")
    private String phoneNumber;

    @NotNull(message = "must add your age")
    @Min(value = 26,message = "you must be older than 25")
    @Max(value = 65,message = "you must be younger than 65")
    private int age;

    @NotEmpty(message = "position can not be empty")
    @Pattern(regexp = "supervisor|coordinator")
    private String position;


    private boolean onLeave =false;

    @NotNull(message = "you mus add you hire date")
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull(message = "the annual leave must not be null")
    @PositiveOrZero(message = "must be a positive number")
    private int annualLeave;


}
