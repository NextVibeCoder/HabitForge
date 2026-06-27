package com.example.habitforgeapi.dto;

public class UserProfileResponseDTO {
    private String username;
    private String email;
    private int rachaMasLarga;
    private int cantidadHabitosActivos;

    public UserProfileResponseDTO() {
    }

    public UserProfileResponseDTO(String username, String email, int rachaMasLarga, int cantidadHabitosActivos) {
        this.username = username;
        this.email = email;
        this.rachaMasLarga = rachaMasLarga;
        this.cantidadHabitosActivos = cantidadHabitosActivos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRachaMasLarga() {
        return rachaMasLarga;
    }

    public void setRachaMasLarga(int rachaMasLarga) {
        this.rachaMasLarga = rachaMasLarga;
    }

    public int getCantidadHabitosActivos() {
        return cantidadHabitosActivos;
    }

    public void setCantidadHabitosActivos(int cantidadHabitosActivos) {
        this.cantidadHabitosActivos = cantidadHabitosActivos;
    }
}
