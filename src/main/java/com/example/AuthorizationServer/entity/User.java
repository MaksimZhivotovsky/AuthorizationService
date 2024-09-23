package com.example.AuthorizationServer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_sc")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "keycloak_id")
    private String keycloakId;

    @Column(name = "e_mail")
    private String eMailAddress;

    @Column(name = "organization_id")
    private Long organization;

    @Column(name = "status_id")
    private Integer status;

    @Column(name = "role_id")
    private Long role;

    @Column(name = "system_name")
    private String systemName;

    @Column(name = "session_id_auth")
    private String sessionIdAuth;

    @Column(name = "date_created")
    private Date dateCreation;

    @Column(name = "date_update")
    private Date dateUpdate;

    @Column(name = "date_session_auth")
    private Date dateSessionAuth;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_update")
    private Long userUpdate;

    @Column(name = "is_archive")
    private Boolean isArchive;


}
