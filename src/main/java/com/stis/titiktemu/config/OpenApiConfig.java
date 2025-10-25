package com.stis.titiktemu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Titik Temu API",
                version = "1.0.0",
                description = "REST API untuk sistem Lost and Found Politeknik Statistika STIS. " +
                        "Sistem ini memungkinkan sivitas akademika untuk melaporkan barang hilang atau ditemukan di lingkungan kampus. " +
                        "Gunakan endpoint /api/auth/register untuk membuat akun baru, lalu /api/auth/login untuk mendapatkan token JWT. " +
                        "Token tersebut digunakan untuk mengakses endpoint yang dilindungi dengan mengklik tombol 'Authorize' di kanan atas.",
                contact = @Contact(
                        name = "Tim Pengembang Titik Temu",
                        email = "support@stis.ac.id"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication. Masukkan token JWT yang didapat dari endpoint /api/auth/login. Format: cukup masukkan token tanpa kata 'Bearer'.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}