package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private String nombre;

    private Integer edadPreferida;

    @Enumerated(EnumType.STRING)
    private Tipo tipoPreferido;

    @Enumerated(EnumType.STRING)
    private Tamano tamanoPreferido;

    @Enumerated(EnumType.STRING)
    private NivelEnergia nivelEnergiaPreferido;

    @Enumerated(EnumType.STRING)
    private Sexo sexoPreferido;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }
    public void activar() {
        activo = true;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdadPreferida() {
        return edadPreferida;
    }

    public void setEdadPreferida(Integer edadPreferida) {
        this.edadPreferida = edadPreferida;
    }

    public Tipo getTipoPreferido() {
        return tipoPreferido;
    }

    public void setTipoPreferido(Tipo tipoPreferido) {
        this.tipoPreferido = tipoPreferido;
    }

    public Tamano getTamanoPreferido() {
        return tamanoPreferido;
    }

    public void setTamanoPreferido(Tamano tamanoPreferido) {
        this.tamanoPreferido = tamanoPreferido;
    }

    public NivelEnergia getNivelEnergiaPreferido() {
        return nivelEnergiaPreferido;
    }

    public void setNivelEnergiaPreferido(NivelEnergia nivelEnergiaPreferido) {
        this.nivelEnergiaPreferido = nivelEnergiaPreferido;
    }

    public Sexo getSexoPreferido() {
        return sexoPreferido;
    }

    public void setSexoPreferido(Sexo sexoPreferido) {
        this.sexoPreferido = sexoPreferido;
    }
}
