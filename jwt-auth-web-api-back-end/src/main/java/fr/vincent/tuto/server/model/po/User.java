/*
 * ----------------------------------------------
 * Projet ou Module : jwt-auth-web-api-back-end
 * Nom de la classe : User.java
 * Date de création : 18 janv. 2021
 * Heure de création : 20:17:45
 * Package : fr.vincent.tuto.server.model.po
 * Auteur : Vincent Otchoun
 * Copyright © 2021 - All rights reserved.
 * ----------------------------------------------
 */
package fr.vincent.tuto.server.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.vincent.tuto.server.enumeration.RoleEnum;
import fr.vincent.tuto.server.util.ServerUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Mapping objet des informations des utilisateurs en base de données dans la table T_USERS. Les informations de mapping
 * sont pour la
 * plupart au sens Spring Security {@link UserDetails}. Hormis : email,createdTime, updatedTime).
 * 
 * @author Vincent Otchoun
 */
@Entity
@Table(name = "T_USERS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
// @SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
@Getter // génère tous les getters sur les champs.
@Setter // génère tous les setters sur les champs.
@NoArgsConstructor // génère le constructeur sans arguments.
@EqualsAndHashCode(callSuper = false, of = "id") // génère equals et hashCode (et d'autres méthodes) sur les champs
                                                 // donnés.
@FieldDefaults(level = AccessLevel.PRIVATE) // Passe tous les champs en private.
@AllArgsConstructor(access = AccessLevel.PRIVATE) // le constructeur avec tous les arguments est nécessaire au Builder,
                                                  // mais pour le rendre
                                                  // inaccessible depuis un autre
// package, mais toujours depuis le Builder, je le place ici en protected
@Builder // génère une classe interne de type « Builder »
public class User extends AbstractPersistable<Long> implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7689968200438820488L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    Long id; // identifiant technique auto-généré de l'objet en base.

    @NotNull(message = ServerUtil.USERNAME_VALIDATION_MSG)
    @Size(min = 3, max = 80, message = ServerUtil.USERNAME_VALIDATION_MSG)
    @Pattern(regexp = ServerUtil.LOGIN_REGEX)
    @Column(name = "USER_NAME", nullable = false, unique = true)
    String username; // le login utilisé pour authentifier l'utilisateur (non null et unique).

    @JsonIgnore
    @NotNull(message = ServerUtil.PWD_VALIDATION_MSG)
    @Pattern(regexp = ServerUtil.PASSWORD_REGEX, message = ServerUtil.PWD_VALIDATION_MSG)
    @Size(min = 60, max = 60)
    @Column(name = "USER_PASSWORD", length = 60, nullable = false)
    String password; // le mot de passe utilisé pour authentifier l'utilisateur(non null).

    @Email(message = ServerUtil.EMAIL_VALIDATION_MSG)
    @Size(min = 8, max = 254, message = ServerUtil.EMAIL_VALIDATION_MSG)
    @Column(name = "EMAIL", unique = true, length = 254, nullable = false)
    String email; // adresse mail de l'utilisateur.

    @Column(name = "ACCOUNT_EXPIRED", nullable = false)
    Boolean accountExpired; // Indique si le compte de l'utilisateur a expiré. Un compte expiré ne peut pas être
                            // authentifié.

    @Column(name = "ACCOUNT_LOCKED", nullable = false)
    Boolean accountLocked;// Indique si l'utilisateur est verrouillé ou déverrouillé. Un utilisateur verrouillé
                          // ne peut pas être authentifié.

    @Column(name = "CREDENTIALS_EXPIRED", nullable = false)
    Boolean credentialsExpired; // Indique si les informations d'identification de l'utilisateur (mot de
                                // passe)ont expiré. Les informations d'identification expirées empêchent
                                // l'authentification.

    @Column(name = "ENABLED", nullable = false)
    Boolean enabled; // Indique si l'utilisateur est activé ou désactivé. Un utilisateur désactivé ne peut pas
                     // être authentifié.

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderBy
    private Set<RoleEnum> roles;

    @Column(name = " CREATED_TIME", insertable = true, updatable = false)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime createdTime; // horodatage pour la création de l'objet en base.

    @Column(name = "UPDATED_TIME", insertable = false, updatable = true)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime updatedTime; // horodatage pour la modification de l'objet en base.

    @JsonIgnore
    @Version
    @Column(name = "OPTLOCK", nullable = false)
    Integer version; // Gestion de l'optimistic lock (lock optimiste).

    @PrePersist
    protected void onCreate()
    {
        this.accountExpired = Boolean.FALSE;
        this.accountLocked = Boolean.FALSE;
        this.credentialsExpired = Boolean.FALSE;
        this.enabled = Boolean.TRUE;
        this.createdTime = LocalDateTime.now(ZoneId.systemDefault());
        this.updatedTime = LocalDateTime.now(ZoneId.systemDefault());
        this.version = Integer.valueOf(0);
    }

    @PreUpdate
    protected void onUpdate()
    {
        this.updatedTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    /**
     * Vérifier si l'utilisateur est administrateur.
     * 
     * @return true si oui, false sinon.
     */
    public boolean isAdmin()
    {
        return this.roles.contains(RoleEnum.ROLE_ADMIN);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
