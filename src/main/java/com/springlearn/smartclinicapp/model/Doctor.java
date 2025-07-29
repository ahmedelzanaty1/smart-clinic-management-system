package com.springlearn.smartclinicapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends User {

    // The 'id' field, correctly annotated as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The 'availableTimes' field, correctly annotated for collections
    @ElementCollection
    private List<String> availableTimes;

    private String specialty;

    // This method seems like a placeholder; in a real application,
    // you'd typically handle password securely (e.g., in the User class or
    // through a dedicated authentication service).
    public Object getPassword() {
        return null;
    }
}