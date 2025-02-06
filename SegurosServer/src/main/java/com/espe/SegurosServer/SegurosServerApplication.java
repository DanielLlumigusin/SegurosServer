package com.espe.SegurosServer;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.espe.SegurosServer.entities.PermissionEntity;
import com.espe.SegurosServer.entities.RoleEntity;
import com.espe.SegurosServer.entities.RoleEnum;
import com.espe.SegurosServer.entities.Usuario;
import com.espe.SegurosServer.repository.IUsuarioRepository;

@SpringBootApplication
public class SegurosServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegurosServerApplication.class, args);
	}
//
//	@Bean
//	CommandLineRunner init(IUsuarioRepository usuarioRepository) {
//		return args -> {
//			/*Crear Permisos*/
//			PermissionEntity createPermission = PermissionEntity.builder()
//					.name("CREATE")
//					.build();
//			PermissionEntity readPermission = PermissionEntity.builder()
//					.name("READ")
//					.build();
//			PermissionEntity updatePermission = PermissionEntity.builder()
//					.name("UPDATE")
//					.build();
//			PermissionEntity deletePermission = PermissionEntity.builder()
//					.name("DELETE")
//					.build();
//			PermissionEntity refactorPermission = PermissionEntity.builder()
//					.name("REFACTOR")
//					.build();
//			
//			/*Crear Roles*/
//			RoleEntity roleAdmin = RoleEntity.builder()
//                    .roleEnum(RoleEnum.ADMIN)
//                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
//                    .build();
//
//            RoleEntity roleUser = RoleEntity.builder()
//                    .roleEnum(RoleEnum.USER)
//                    .permissionList(Set.of(createPermission, readPermission))
//                    .build();
//
//            RoleEntity roleInvited = RoleEntity.builder()
//                    .roleEnum(RoleEnum.INVITED)
//                    .permissionList(Set.of(readPermission))
//                    .build();
//
//            RoleEntity roleDeveloper = RoleEntity.builder()
//                    .roleEnum(RoleEnum.DEVELOPER)
//                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
//                    .build();
//
//            /* CREATE USERS */
//            Usuario userSantiago = Usuario.builder()
//                    .username("santiago")
//                    .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleAdmin))
//                    .build();
//
//            Usuario userDaniel = Usuario.builder()
//                    .username("daniel")
//                    .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleUser))
//                    .build();
//
//            Usuario userAndrea = Usuario.builder()
//                    .username("andrea")
//                    .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleInvited))
//                    .build();
//
//            Usuario userAnyi = Usuario.builder()
//                    .username("anyi")
//                    .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleDeveloper))
//                    .build();
//
//            usuarioRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
//        };
//	}
	
}
