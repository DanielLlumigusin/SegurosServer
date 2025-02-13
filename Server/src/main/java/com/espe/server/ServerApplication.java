package com.espe.server;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import com.espe.server.persistence.entity.*;
import com.espe.server.persistence.repository.PermissionRepository;
import com.espe.server.persistence.repository.RoleRepository;
import com.espe.server.persistence.repository.UsuarioRepository;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
