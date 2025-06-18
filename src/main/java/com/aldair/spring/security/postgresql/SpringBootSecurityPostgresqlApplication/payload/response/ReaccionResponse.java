package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;

public class ReaccionResponse {
    private EReaction tipo;
    private String usuario;

    public ReaccionResponse(EReaction tipo, String usuario) {
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public EReaction getTipo() {
        return tipo;
    }

    public void setTipo(EReaction tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Getters y setters
    
}
