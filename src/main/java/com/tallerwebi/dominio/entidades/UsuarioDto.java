package com.tallerwebi.dominio.entidades;

import java.util.Objects;

public class UsuarioDto {

    private Long id;
    private String email;
    private String rol;
    //private Boolean activo = false;
    private String nombre;
    private Integer edadPreferida;
    private Tipo tipoPreferido;
    private Tamano tamanoPreferido;
    private NivelEnergia nivelEnergiaPreferido;
    private Sexo sexoPreferido;


    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.rol = usuario.getRol();
        //this.activo = usuario.getActivo();
        this.edadPreferida = usuario.getEdadPreferida();
        this.tipoPreferido = usuario.getTipoPreferido();
        this.tamanoPreferido = usuario.getTamanoPreferido();
        this.nivelEnergiaPreferido = usuario.getNivelEnergiaPreferido();
        this.sexoPreferido = usuario.getSexoPreferido();
    }

    public UsuarioDto() {
    }

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public Usuario obtenerEntidad() {
        Usuario usuario = new Usuario();
        return this.obtenerEntidad(usuario);
    }

    public Usuario obtenerEntidad(Usuario usuario) {
        usuario.setId(this.id);
        usuario.setEmail(this.email);
        usuario.setNombre(this.nombre);
        usuario.setRol(this.rol);
        //usuario.setActivo(this.activo);
        usuario.setEdadPreferida(this.edadPreferida);
        usuario.setTipoPreferido(this.tipoPreferido);
        usuario.setTamanoPreferido(this.tamanoPreferido);
        usuario.setNivelEnergiaPreferido(this.nivelEnergiaPreferido);
        usuario.setSexoPreferido(this.sexoPreferido);
        return usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDto that = (UsuarioDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(rol, that.rol) && Objects.equals(nombre, that.nombre) && Objects.equals(edadPreferida, that.edadPreferida) && tipoPreferido == that.tipoPreferido && tamanoPreferido == that.tamanoPreferido && nivelEnergiaPreferido == that.nivelEnergiaPreferido && sexoPreferido == that.sexoPreferido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, rol, nombre, edadPreferida, tipoPreferido, tamanoPreferido, nivelEnergiaPreferido, sexoPreferido);
    }
}
