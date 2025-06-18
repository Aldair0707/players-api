package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentUpdateRequest {

    @NotBlank(message = "EL CONTENIDO NO PUEDE ESTAR VACIO")
    @Size(max = 500, message = "EL COMENTARIO NO PUEDE SUPERAR LOS 500 CARACTERES")
    private String content;

    public CommentUpdateRequest() {
    }

    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}