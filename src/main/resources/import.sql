
DELIMITER //

CREATE TRIGGER before_insert_matricula_alumnos
BEFORE INSERT ON matricula
FOR EACH ROW
BEGIN
    DECLARE num_alumnos INT;

    SELECT COUNT(*) INTO num_alumnos
    FROM matricula m
    JOIN usuario u ON m.usuario_id = u.id
    JOIN usuario_roles ur ON u.id = ur.usuario_id
    JOIN rol r ON ur.roles_id = r.id
    WHERE m.asignatura_id = NEW.asignatura_id
      AND r.nombre = 'alumno';

    IF num_alumnos >= 32 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se permite matricular más de 32 alumnos en una asignatura';
    END IF;
END //

CREATE TRIGGER before_insert_matricula_profesores
BEFORE INSERT ON matricula
FOR EACH ROW
BEGIN
    DECLARE num_profesores INT;

    SELECT COUNT(*) INTO num_profesores
    FROM matricula m
    JOIN usuario u ON m.usuario_id = u.id
    JOIN usuario_roles ur ON u.id = ur.usuario_id
    JOIN rol r ON ur.roles_id = r.id
    WHERE m.asignatura_id = NEW.asignatura_id
      AND r.nombre = 'profesor';

    IF num_profesores >= 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se permite matricular más de 2 profesores en una asignatura';
    END IF;
END //

DELIMITER ;


INSERT INTO `usuario` (`id`, `apellido`, `email`, `nombre`, `password`, `username`, `telefono`, `enabled`) VALUES (1,	'Martín',	'pepe@sincorreo.com',	'Pepe',	'$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO',	'pepe', 123456789,1);
INSERT INTO `usuario` (`id`, `apellido`, `email`, `nombre`, `password`, `username`, `telefono`, `enabled`) VALUES (2,	'Sin Miedo',	'obijuan@sincorreo.es',	'Juan',	'$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', 123456789,	'obijuan',1);
INSERT INTO `usuario` (`id`, `username`, `nombre`, `email`, `password`, `apellido`, `telefono`, `enabled`) VALUES (3, 'juanito', 'Juanito', 'juan.perez@email.com', '$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', 123456789, 'pulgarcito',1);


INSERT INTO `rol` (`id`, `nombre`) VALUES (1,	'gestor');
INSERT INTO `rol` (`id`, `nombre`) VALUES  (2,	'alumno');
INSERT INTO `rol` (`id`, `nombre`) VALUES  (3,	'profesor');

INSERT INTO `usuario_roles` (`usuario_id`, `roles_id`) VALUES (1,	1);
INSERT INTO `usuario_roles` (`usuario_id`, `roles_id`) VALUES (2,	2);
INSERT INTO `usuario_roles` (`usuario_id`, `roles_id`) VALUES (3,	3);


INSERT INTO `asignaturas` (`id`, `nombre`, `curso`, `ciclo`) VALUES  (1, 'Programacion Multimedia', 'Segundo', 'DAM');
INSERT INTO `asignaturas` (`id`, `nombre`, `curso`, `ciclo`) VALUES  (2, 'Acceso a datos', 'Segundo', 'DAW');

INSERT INTO `matricula` (`id`, `usuario_id`, `asignatura_id`) VALUES  (1, 3, 1);
INSERT INTO `matricula` (`id`, `usuario_id`, `asignatura_id`) VALUES  (2, 2, 2);



