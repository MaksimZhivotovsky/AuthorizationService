package com.example.AuthorizationServer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "top"})
public final class LdapUser {

    @Id
    private Name dn;
    @Attribute(name = "cn")
    @DnAttribute(value = "cn")
    private String fullName;
    @Attribute(name = "sn")
    private String lastName;
    @Attribute(name = "givenname")
    private String givenName;
    @Attribute(name = "mail")
    private String email;
    @Attribute(name = "description")
    private String description;
    @Attribute(name = "uid")
    private String uid;
    @Attribute(name = "userPassword")
    private String password;

}
