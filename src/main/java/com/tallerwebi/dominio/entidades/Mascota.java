package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean adoptado = false;
    private String img;
    private String salud;
    private String comportamiento;
    private Integer edad;
    private String estado = "Pendiente"; // Pendiente, Aprobada, Rechazada
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    @Enumerated(EnumType.STRING)
    private NivelEnergia nivelEnergia;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAdoptado() {
        return adoptado;
    }
    public void setAdoptado(Boolean adoptado) {
        this.adoptado = adoptado;
    }

    public String getImg() { return img; }
    public void setImg(String img) {
        this.img = img;
    }

    public String getSalud() { return salud; }
    public void setSalud(String salud) {this.salud = salud;}

    public String getComportamiento() { return comportamiento; }
    public void setComportamiento(String comportamiento) {this.comportamiento = comportamiento;}

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public Tamano getTamano() { return tamano; }
    public void setTamano(Tamano tamano) { this.tamano = tamano; }

    public NivelEnergia getNivelEnergia() { return nivelEnergia; }
    public void setNivelEnergia(NivelEnergia nivelEnergia) { this.nivelEnergia = nivelEnergia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(id, mascota.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
