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
import com.espe.server.persistence.repository.UserRepository;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

/*    @Bean
    CommandLineRunner init(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            // Crear y guardar permisos
            PermissionEntity createPermission = new PermissionEntity("CREATE");
            PermissionEntity readPermission = new PermissionEntity("READ");
            PermissionEntity updatePermission = new PermissionEntity("UPDATE");
            PermissionEntity deletePermission = new PermissionEntity("DELETE");
            PermissionEntity refactorPermission = new PermissionEntity("REFACTOR");

            // Guardar permisos en la base de datos
            permissionRepository.saveAll(List.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

            // Crear roles
            RoleEntity roleAdmin = new RoleEntity(RoleEnum.ADMIN, Set.of(createPermission, readPermission, updatePermission, deletePermission));
            RoleEntity roleUser = new RoleEntity(RoleEnum.USER, Set.of(createPermission, readPermission));
            RoleEntity roleInvited = new RoleEntity(RoleEnum.INVITED, Set.of(readPermission));
            RoleEntity roleDeveloper = new RoleEntity(RoleEnum.DEVELOPER, Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

            // Guardar roles en la base de datos
            roleRepository.saveAll(List.of(roleAdmin, roleUser, roleInvited, roleDeveloper));

            // Crear usuarios
            UserEntity userSantiago = new UserEntity("santiago", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", true, true, true, true, Set.of(roleAdmin));
            UserEntity userDaniel = new UserEntity("daniel", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", true, true, true, true, Set.of(roleUser));
            UserEntity userAndrea = new UserEntity("andrea", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", true, true, true, true, Set.of(roleInvited));
            UserEntity userAnyi = new UserEntity("anyi", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", true, true, true, true, Set.of(roleDeveloper));

            // Guardar usuarios en la base de datos
            userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
        };*/
    
}
