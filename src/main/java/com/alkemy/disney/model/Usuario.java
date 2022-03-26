package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = "Usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "usuario_sequence_generator", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence_generator")
    private Long id;

    @NotNull
    @Column(length = 63)
    private String email;

    @NotNull
    @Column(length = 127)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Usuario(String email, String contrasena, Rol rol)
    {
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // @JsonIgnore
    // @JsonDeserialize(contentAs = SimpleGrantedAuthority.class)     // Para que jackson pueda deserializarlo (no puede deserializar clases abstractas/interfaces)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(rol.getNombre());
        return Collections.singletonList(grantedAuthority);
        // Jackson no puede deserializar la lista de arriba, hay que usar una distinta
        /* ArrayList<SimpleGrantedAuthority> l = new ArrayList<SimpleGrantedAuthority>();
        l.add(grantedAuthority);
        return l; */
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
