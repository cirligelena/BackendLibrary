package com.stefanini.librarybackend.domain;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "profile")
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileId_generator")
    @SequenceGenerator(name = "profileId_generator", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName ;
    @Column(name = "phoneNumber")
    private String  phoneNumber;

    public Profile(String firstName, String lastName, String phoneNumber){
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
    }

    @OneToMany
    private List<UserRole> userRole;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}