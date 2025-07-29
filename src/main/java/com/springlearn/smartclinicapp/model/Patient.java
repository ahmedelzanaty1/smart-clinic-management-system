package com.springlearn.smartclinicapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends User {
    private String gender;
    private LocalDate birthDate;
}
