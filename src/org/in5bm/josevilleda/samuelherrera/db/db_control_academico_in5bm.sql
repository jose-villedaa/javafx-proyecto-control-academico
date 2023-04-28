create database db_control_academico_in5bm;
use db_control_academico_in5bm;

/* Estudiantes:
* Jose Villeda: 2021075
* 
* Grupo (2/2) Lunes
* Codigo Tecnico : IN5BM
* Fecha: 17/06/2022
* Fecha modificacion: 17/06/2022
*
*/

DROP TABLE IF EXISTS rol;
CREATE TABLE IF NOT EXISTS rol (
id INT NOT NULL,
descripcion VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE IF NOT EXISTS usuario(
user VARCHAR(25) NOT NULL,
pass VARCHAR (255) NOT NULL,
nombre VARCHAR(50) NOT NULL,
rol_id INT NOT NULL,
PRIMARY KEY (user),
CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE IF NOT EXISTS alumnos (
	carne VARCHAR (16) NOT NULL,
	nombre1	 VARCHAR(15) NOT NULL,
    nombre2	 VARCHAR(15) NULL,
    nombre3	 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15) NULL,
	CONSTRAINT pk_alumnos PRIMARY KEY (carne)
);

CREATE TABLE IF NOT EXISTS salones (
	codigo_salon VARCHAR (5) NOT NULL,
    descripcion VARCHAR (45) NULL,
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR (15) NULL,
    nivel INT,
	CONSTRAINT pk_salones PRIMARY KEY (codigo_salon)
);

CREATE TABLE IF NOT EXISTS carreras_tecnicas (
	codigo_tecnico VARCHAR (6)  NOT NULL,
    carrera VARCHAR(45) NOT NULL,
    grado VARCHAR (10) NOT NULL,
    seccion CHAR(1) NOT NULL,
    jornada VARCHAR(10) NOT NULL,
    CONSTRAINT pk_carreras PRIMARY KEY (codigo_tecnico)
);

CREATE TABLE IF NOT EXISTS instructores (
	id INT NOT NULL AUTO_INCREMENT,
    nombre1 VARCHAR (15) NOT NULL,
    nombre2 VARCHAR (15) NULL,
    nombre3 VARCHAR (15) NULL,
    apellido1 VARCHAR (15) NOT NULL,
    apellido2 VARCHAR (15) NULL,
    direccion VARCHAR (45) NULL,
    email VARCHAR(45) NOT NULL,
    telefono VARCHAR (8) NOT NULL,
    fecha_nacimiento DATE NULL,
    CONSTRAINT pk_instructores PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS horarios (
	id INT NOT NULL AUTO_INCREMENT,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT NULL,
    martes TINYINT NULL,
    miercoles TINYINT NULL,
    jueves TINYINT NULL,
    viernes TINYINT NULL,
    CONSTRAINT pk_horarios PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cursos (
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255) NOT NULL,
    ciclo YEAR NULL,
    cupo_maximo INT NULL,
    cupo_minimo INT NULL,
    carrera_tecnica_id VARCHAR(6) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    CONSTRAINT pk_cursos PRIMARY KEY (id),
    FOREIGN KEY (carrera_tecnica_id) REFERENCES carreras_tecnicas (codigo_tecnico) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (horario_id) REFERENCES horarios (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructores (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (salon_id) REFERENCES salones (codigo_salon) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS asignaciones_alumnos;
CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
id INT NOT NULL AUTO_INCREMENT,
alumno_id VARCHAR(16) NOT NULL,
curso_id INT NOT NULL,
fecha_asignacion DATETIME NULL,
CONSTRAINT pk_asignaciones_alumnos PRIMARY KEY (id),
CONSTRAINT fk_asignaciones_alumnos_alumno
FOREIGN KEY (alumno_id) REFERENCES alumnos(carne)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_asignaciones_alumnos_cursos
FOREIGN KEY (curso_id) REFERENCES cursos(id)
ON UPDATE CASCADE ON DELETE CASCADE
);

-- CREAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
	IN _carne VARCHAR (16) ,
	IN _nombre1 VARCHAR (15),
    IN _nombre2 VARCHAR (15),
    IN _nombre3 VARCHAR (15),
    IN _apellido1 VARCHAR (15),
	IN _apellido2 VARCHAR (15)
)
BEGIN
	INSERT INTO alumnos (
		carne,
		nombre1, 
		nombre2, 
		nombre3, 
		apellido1, 
		apellido2
    ) 
    VALUES (
		_carne, 
		_nombre1, 
		_nombre2, 
		_nombre3, 
		_apellido1, 
		_apellido2
	);
END $$
DELIMITER ;

-- LISTAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
	SELECT 
		carne, 
		nombre1, 
		nombre2, 
		nombre3, 
		apellido1, 
		apellido2
	FROM
		alumnos;
	END$$
    DELIMITER ;
    
-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(_carne VARCHAR(16))
BEGIN
	SELECT 
		carne, 
		nombre1, 
		nombre2, 
		nombre3, 
		apellido1, 
		apellido2 
	FROM 
		alumnos 
	WHERE 
		carne = _carne;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_reporte$$
CREATE PROCEDURE sp_alumnos_reporte()
BEGIN 
	SELECT
		a.carne,
        CONCAT(
			a.nombre1," ",
            IF(a.nombre2 IS NULL," ",a.nombre2)," ",
            IF(a.nombre3 IS NULL," ",a.nombre3)
        )AS nombres,
        CONCAT(
			a.apellido1," ",
            IF(a.apellido2 IS NULL," ",a.apellido2)
        )AS apellidos
        
    FROM 
		alumnos AS a;
END $$
DELIMITER ;

-- ACTUALIZAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR (16) ,
	IN _nombre1 VARCHAR (15),
    IN _nombre2 VARCHAR (15),
    IN _nombre3 VARCHAR (15),
    IN _apellido1 VARCHAR (15),
	IN _apellido2 VARCHAR (15)
)
BEGIN
	UPDATE
		alumnos
	SET
		nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2
	WHERE
		carne = _carne;
END $$
DELIMITER ;

-- ELIMINAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(
	IN _carne VARCHAR (16)
)
BEGIN
	DELETE FROM
		alumnos
	WHERE
		carne = _carne;
END $$
DELIMITER ;

-- CREAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create(
	IN _codigo_salon VARCHAR (5) ,
	IN _descripcion VARCHAR (45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR (15),
    IN _nivel INT
)
BEGIN
	INSERT INTO salones (
		codigo_salon,
		descripcion, 
		capacidad_maxima, 
		edificio, 
		nivel
    ) 
    VALUES (
		_codigo_salon, 
		_descripcion, 
		_capacidad_maxima,
		_edificio, 
		_nivel
	);
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_report $$
CREATE PROCEDURE sp_salones_report()
BEGIN 
	SELECT 
		s.codigo_salon,
        IF(s.nivel IS NULL," ",s.nivel),
        IF(s.edificio IS NULL," ",s.edificio),
        s.capacidad_maxima,
        IF(s.descripcion IS NULL," ",s.descripcion)
    FROM 
		salones AS s;
END $$
DELIMITER ;

-- LISTAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
	SELECT 
		codigo_salon,
		descripcion, 
		capacidad_maxima, 
		edificio, 
		nivel
	FROM
		salones;
	END$$
    DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(_codigo_salon VARCHAR(5))
BEGIN
	SELECT 
		codigo_salon,
		descripcion, 
		capacidad_maxima, 
		edificio, 
		nivel
	FROM
		salones
	WHERE 
		codigo_salon = _codigo_salon;
END $$
DELIMITER ;
    
    -- ACTUALIZAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update(
	IN _codigo_salon VARCHAR (5) ,
	IN _descripcion VARCHAR (45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR (15),
    IN _nivel INT
)
BEGIN
	UPDATE
		salones
	SET
		descripcion = _descripcion,
        capacidad_maxima = _capacidad_maxima,
        edificio = _edificio,
        nivel = _nivel
	WHERE
		codigo_salon = _codigo_salon;
END $$
DELIMITER ;

-- ELIMINAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(
	IN _codigo_salon VARCHAR (5)
)
BEGIN
	DELETE FROM
		salones
	WHERE
		codigo_salon = _codigo_salon;
END $$
DELIMITER ;


-- CREAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create(
	IN _codigo_tecnico VARCHAR (6) ,
	IN _carrera VARCHAR (45),
    IN _grado VARCHAR (10),
    IN _seccion CHAR (1),
    IN _jornada VARCHAR (10)
)
BEGIN
	INSERT INTO carreras_tecnicas (
		codigo_tecnico,
		carrera, 
		grado,
		seccion,
		jornada 
    ) 
    VALUES (
		_codigo_tecnico,
		_carrera, 
		_grado,
		_seccion,
		_jornada 
	);
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_report$$
CREATE PROCEDURE sp_carreras_tecnicas_report()
BEGIN 
	SELECT
		c.codigo_tecnico,
        c.carrera,
        c.grado,
        c.seccion,
        c.jornada
    FROM 
		carreras_tecnicas AS c;
END $$
DELIMITER ;

-- LISTAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
	SELECT 
		codigo_tecnico,
		carrera, 
		grado,
		seccion,
		jornada 
	FROM
		carreras_tecnicas;
	END$$
    DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(_codigo_tecnico VARCHAR(6))
BEGIN
	SELECT 
		codigo_tecnico,
		carrera, 
		grado,
		seccion,
		jornada 
	FROM
		carreras_tecnicas
	WHERE 
		codigo_tecnico = _codigo_tecnico;
END $$
DELIMITER ;

-- ACTUALIZAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update(
	IN _codigo_tecnico VARCHAR (6) ,
	IN _carrera VARCHAR (45),
    IN _grado VARCHAR (10),
    IN _seccion CHAR (1),
    IN _jornada VARCHAR (10)
)
BEGIN
	UPDATE
		carreras_tecnicas
	SET
		carrera = _carrera,
        grado = _grado,
        seccion = _seccion,
        jornada = _jornada
	WHERE
		codigo_tecnico = _codigo_tecnico;
END $$
DELIMITER ;

-- ELIMINAR --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete(
	IN _codigo_tecnico VARCHAR (6)
)
BEGIN
	DELETE FROM
		carreras_tecnicas
	WHERE
		codigo_tecnico  = _codigo_tecnico ;
END $$
DELIMITER ;

-- CREATE -- 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create(
    IN _nombre1 VARCHAR (15),
    IN _nombre2 VARCHAR (15),
    IN _nombre3 VARCHAR (15),
    IN _apellido1 VARCHAR (15),
	IN _apellido2 VARCHAR (15),
    IN _direccion VARCHAR (45),
    IN _email VARCHAR (45),
    IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE
)
BEGIN 
	INSERT INTO instructores(
            nombre1,
            nombre2,
            nombre3,
            apellido1,
            apellido2,
            direccion,
            email,
            telefono,
            fecha_nacimiento
    )
    VALUES (
        _nombre1,
        _nombre2,
        _nombre3,
        _apellido1,
        _apellido2,
        _direccion,
        _email,
        _telefono,
        _fecha_nacimiento
		
    );
    END $$
    DELIMITER ;
    
DELIMITER $$
DROP PROCEDURE IF EXISTS  sp_instructores_reporte$$
CREATE PROCEDURE sp_instructores_reporte()
BEGIN 
	SELECT 
		i.id,
        CONCAT(
			i.nombre1," ",
            IF(i.nombre2 IS NULL," ",i.nombre2)," ",
            IF(i.nombre3 IS NULL," ",i.nombre3)," ",
            i.apellido1," ",
            IF(i.apellido2 IS NULL," ",i.apellido2)
        )AS nombre_completo,
        IF(i.direccion IS NULL," ",i.direccion),
        i.email,
        i.telefono,
        IF(i.fecha_nacimiento IS NULL," ",i.fecha_nacimiento)
	FROM 
		instructores AS i;
END $$
DELIMITER ;
    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read ()
BEGIN
	SELECT 
		id,
		nombre1,
		nombre2,
		nombre3,
		apellido1,
		apellido2,
        direccion,
		email,
		telefono,
		fecha_nacimiento
	FROM
		instructores;
END $$
DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(_id INT)
BEGIN
	SELECT 
		id,
		nombre1,
		nombre2,
		nombre3,
		apellido1,
		apellido2,
        direccion,
		email,
		telefono,
		fecha_nacimiento
	FROM
		instructores
	WHERE 
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update(
		IN _id INT,
		IN _nombre1 VARCHAR (15),
		IN _nombre2 VARCHAR (15),
		IN _nombre3 VARCHAR (15),
		IN _apellido1 VARCHAR (15),
		IN _apellido2 VARCHAR (15),
        IN _direccion VARCHAR (45),
		IN _email VARCHAR (45),
		IN _telefono VARCHAR(8),
		IN _fecha_nacimiento DATE
)
BEGIN
	UPDATE
		instructores
	SET
		nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2,
        direccion = _direccion,
        email = _email,
        telefono = _telefono,
        fecha_nacimiento = _fecha_nacimiento
	WHERE 
		id = _id;
END $$
    DELIMITER ;
    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		instructores
	WHERE
		id = _id;
END $$
DELIMITER ;
        
            
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create(
        IN _horario_inicio TIME,
        IN _horario_final TIME,
        IN _lunes TINYINT, 
        IN _martes TINYINT,
        IN _miercoles TINYINT,
        IN _jueves TINYINT,
        IN _viernes TINYINT
)
BEGIN
	INSERT INTO horarios(
        horario_inicio,
        horario_final,
        lunes,
        martes,
        miercoles,
        jueves,
        viernes
    )
	VALUES(
        _horario_inicio,
        _horario_final,
        _lunes,
        _martes,
        _miercoles,
        _jueves,
        _viernes
        
    );
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_report $$
CREATE PROCEDURE sp_horarios_report()
BEGIN 
	SELECT 
		h.id,
		h.horario_inicio, horario_final,
		IF(h.lunes IS NULL," ", IF(h.lunes IS TRUE,"Si", "No") ) AS lunes,
		IF(h.martes IS NULL," ", IF(h.martes IS TRUE,"Si", "No") ) AS martes,
		IF(h.miercoles IS NULL," ", IF(h.miercoles IS TRUE,"Si", "No") ) AS miercoles,
		IF(h.jueves IS NULL," ",IF(h.jueves IS TRUE,"Si", "No") ) AS jueves,
		IF(h.viernes IS NULL," ",IF(h.viernes IS TRUE,"Si", "No") )AS viernes
    FROM 
		horarios AS h;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read ()
BEGIN
	SELECT
		id,
        horario_inicio,
        horario_final,
        lunes,
        martes,
        miercoles,
        jueves,
        viernes
	FROM 
		horarios;
END $$
DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
CREATE PROCEDURE sp_horarios_read_by_id(_id INT)
BEGIN
	SELECT
		id,
        horario_inicio,
        horario_final,
        lunes,
        martes,
        miercoles,
        jueves,
        viernes
	FROM 
		horarios
	WHERE 
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update (
		IN _id INT,
        IN _horario_inicio TIME,
        IN _horario_final TIME,
        IN _lunes TINYINT, 
        IN _martes TINYINT,
        IN _miercoles TINYINT,
        IN _jueves TINYINT,
        IN _viernes TINYINT
)
BEGIN
	UPDATE
		horarios
	SET
		id = _id,
        horario_inicio = _horario_inicio,
        horario_final = _horario_final,
        lunes = _lunes,
        martes = _martes,
        miercoles = _miercoles,
        jueves = _jueves,
        viernes = _viernes
	WHERE
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		horarios
	WHERE
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create(
    IN _nombre_curso VARCHAR(255),
    IN _ciclo YEAR,
	IN _cupo_maximo INT,
    IN _cupo_minimo INT,
    IN _carrera_tecnica_id VARCHAR(6),
    IN _horario_id INT,
    IN _instructor_id INT,
    IN _salon_id VARCHAR(5)    
)
BEGIN
	INSERT INTO cursos(
        nombre_curso,
        ciclo,
        cupo_maximo,
        cupo_minimo,
        carrera_tecnica_id,
        horario_id,
        instructor_id,
        salon_id
    )
	VALUES(
        _nombre_curso,
        _ciclo,
        _cupo_maximo,
        _cupo_minimo,
        _carrera_tecnica_id,
        _horario_id,
        _instructor_id,
        _salon_id
    );
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report$$
CREATE PROCEDURE sp_cursos_report()
BEGIN 
	SELECT 
		c.id,
        c.nombre_curso,
        IF(c.ciclo IS NULL," ",c.ciclo),
        IF(c.cupo_maximo IS NULL," ",c.cupo_maximo),
        IF(c.cupo_minimo IS NULL," ",c.cupo_minimo),
        c.carrera_tecnica_id,
        ct.carrera,
        c.horario_id,
        h.horario_final,
        h.horario_inicio,
        c.instructor_id,
        CONCAT(
			i.nombre1," ",
            i.apellido1
        )AS nombre_instructor,
        c.salon_id
    FROM
		cursos AS c
        INNER JOIN carreras_tecnicas AS ct
        INNER JOIN horarios AS h
        INNER JOIN instructores AS i
        INNER JOIN salones AS s
        ON c.carrera_tecnica_id = ct.codigo_tecnico
        AND c.horario_id = h.id
        AND c.instructor_id = i.id
        AND c.salon_id = s.codigo_salon;
			
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read ()
BEGIN
	SELECT
		id,
        nombre_curso,
        ciclo,
        cupo_maximo,
        cupo_minimo,
        carrera_tecnica_id,
        horario_id,
        instructor_id,
        salon_id
	FROM 
		cursos;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_id $$
CREATE PROCEDURE sp_cursos_read_id ()
BEGIN
	SELECT
		c.id
	FROM 
		cursos as c;

END $$
DELIMITER ;

call sp_cursos_read_id();

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_validacion_existencia$$
CREATE PROCEDURE sp_validacion_existencia(IN _user VARCHAR(25), IN _pass VARCHAR(255))
    BEGIN 
        SELECT
        * 
        FROM
        usuario
        WHERE
        user =_user AND
        pass = _pass;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_validacion_rol$$
CREATE PROCEDURE sp_validacion_rol(_user VARCHAR(25))
    BEGIN 
        SELECT
        r.descripcion
        FROM
        usuario , rol AS r
        WHERE
        usuario.rol_id = r.id AND
        user =_user;
END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report_by_id$$
CREATE PROCEDURE sp_cursos_report_by_id(IN _id INT)
BEGIN
    SELECT
        c.id,
        c.nombre_curso,
        IF(c.ciclo IS NULL," ",c.ciclo),
        IF(c.cupo_maximo IS NULL," ",c.cupo_maximo),
        IF(c.cupo_minimo IS NULL," ",c.cupo_minimo),
        c.carrera_tecnica_id,
        ct.carrera,
        c.horario_id,
        h.horario_inicio,
        h.horario_final,
        c.instructor_id,
        CONCAT(
            i.nombre1," ",
            i.apellido1
        )AS nombre_instructor,
        c.salon_id
    FROM
        cursos AS c
        INNER JOIN carreras_tecnicas AS ct
        INNER JOIN horarios AS h
        INNER JOIN instructores AS i
        INNER JOIN salones AS s
        ON c.carrera_tecnica_id = ct.codigo_tecnico
        AND c.horario_id = h.id
        AND c.instructor_id = i.id
        AND c.salon_id = s.codigo_salon
    WHERE c.id=_id;

END $$
DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(_id INT)
BEGIN
	SELECT
		id,
        nombre_curso,
        ciclo,
        cupo_maximo,
        cupo_minimo,
        carrera_tecnica_id,
        horario_id,
        instructor_id,
        salon_id
	FROM 
		cursos
	WHERE 
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update (
	IN _id INT,
    IN _nombre_curso VARCHAR(255),
    IN _ciclo YEAR,
    IN _cupo_maximo INT,
    IN _cupo_minimo INT,
    IN _carrera_tecnica_id VARCHAR(6),
    IN _horario_id INT,
    IN _instructor_id INT,
    IN _salon_id VARCHAR(5)    
)
BEGIN
	UPDATE
		cursos
	SET
		id = _id,
        nombre_curso = _nombre_curso,
		ciclo = _ciclo,
        cupo_maximo = _cupo_maximo,
        cupo_minimo = _cupo_minimo,
        carrera_tecnica_id = _carrera_tecnica_id,
        horario_id = _horario_id,
        instructor_id = _instructor_id,
        salon_id = _salon_id
	WHERE
		id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		cursos
	WHERE
		id = _id;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create(
IN _alumno_id VARCHAR(16),
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
INSERT INTO asignaciones_alumnos(
alumno_id,
curso_id,
fecha_asignacion
) VALUES (
_alumno_id,
_curso_id,
_fecha_asignacion
);
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report$$
CREATE PROCEDURE sp_asignaciones_alumnos_report()
BEGIN 
	SELECT
    aa.id,
    aa.alumno_id,
    CONCAT(
		a.nombre1," ",
		IF(a.nombre2 IS NULL," ",a.nombre2)," ",
		IF(a.nombre3 IS NULL," ",a.nombre3)," ",
        a.apellido1," ",
		IF(a.apellido2 IS NULL," ",a.apellido2)
	)AS nombre_completo,
    aa.curso_id,
    c.nombre_curso,
    IF(aa.fecha_asignacion IS NULL," ",aa.fecha_asignacion)
	FROM 
		asignaciones_alumnos AS aa 
        INNER JOIN alumnos AS a
        INNER JOIN cursos AS c
	ON 
		aa.alumno_id = a.carne
	AND 
		aa.curso_id=c.id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report_by_id$$
CREATE PROCEDURE sp_asignaciones_alumnos_report_by_id(
    _id INT)
BEGIN
    SELECT
    aa.id,
    aa.alumno_id,
    CONCAT(
        a.nombre1," ",
        IF(a.nombre2 IS NULL," ",a.nombre2)," ",
        IF(a.nombre3 IS NULL," ",a.nombre3)," ",
        a.apellido1," ",
        IF(a.apellido2 IS NULL," ",a.apellido2)
    )AS nombre_completo,
    aa.curso_id,
    c.nombre_curso,
    IF(aa.fecha_asignacion IS NULL," ",aa.fecha_asignacion)
    FROM
        asignaciones_alumnos AS aa
        INNER JOIN alumnos AS a
        INNER JOIN cursos AS c
    ON
        aa.alumno_id = a.carne
    AND
        aa.curso_id=c.id
    WHERE
        aa.id=_id;
END $$
DELIMITER ;




DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN
SELECT
asignaciones_alumnos.id,
asignaciones_alumnos.alumno_id,
asignaciones_alumnos.curso_id,
asignaciones_alumnos.fecha_asignacion
FROM
asignaciones_alumnos;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_id()
BEGIN
SELECT
a.id
FROM
asignaciones_alumnos as a;
END $$
DELIMITER ;

-- LISTAR POR ID --    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(
IN _id INT
)
BEGIN
SELECT
asignaciones_alumnos.id,
asignaciones_alumnos.alumno_id,
asignaciones_alumnos.curso_id,
asignaciones_alumnos.fecha_asignacion
FROM
asignaciones_alumnos
WHERE
id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
IN _id INT,
IN _alumno_id VARCHAR(16),
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
UPDATE
asignaciones_alumnos
SET
alumno_id = _alumno_id,
curso_id = _curso_id,
fecha_asignacion = _fecha_asignacion
WHERE
id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete(
IN _id INT
)
BEGIN
DELETE FROM asignaciones_alumnos WHERE id = _id;
END $$
DELIMITER ;





-- CRUD ALUMNOS -- 
call sp_alumnos_create("2021075","Jose", "Mauricio","David", "Villeda", "Morales");
call sp_alumnos_create("2021080","Juan", "Jóse"," ", "Aguilar", "Ocampos");
call sp_alumnos_create("2021175","Byron", "Esteban","André", "González", "Benitez");
call sp_alumnos_create("2021065","Carlos", "Alejandro"," ", "Ramos", "Cortez");
call sp_alumnos_create("2021024","Oscar", "Snider","Javier", "Muralles", "Dominguez");
call sp_alumnos_create("2021022","Luis", "Lorenzo"," ", "Paredes", "Comunica");
call sp_alumnos_create("2021067","Lionel", "Andres"," ", "Messi", "Cuccittini");
call sp_alumnos_create("2021150","Juan", "Andres","Esteban", "Campos", "Olmedo");
call sp_alumnos_create("2021100","Gerson", "David","Estuardo", "Ramirez", "Orantes");
call sp_alumnos_create("2021099","Erick", "Cristobal"," ", "Monterroso", "Barrillas");

call sp_alumnos_read();

call sp_alumnos_read_by_id("2021175");

call sp_alumnos_update("2021075", "Jose", "Mauricio", "", "Villeda", "Morales");

call sp_alumnos_delete("2021075");

DELIMITER $$
CREATE PROCEDURE total_alumnos
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM alumnos;
END $$

SET @total = 0;
CALL total_alumnos( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- CRUD SALONES --
call sp_salones_create("C-29", "Salon de informatica", 30, "Norte", 2);
call sp_salones_create("D-9", "Salon de Matemáticas", 45, "Este", 1);
call sp_salones_create("E-66", "Salon de Sociales", 50, "Norte", 3);
call sp_salones_create("A-40", "Salon de Mecánica", 36, "Norte", 2);
call sp_salones_create("B-25", "Salon de Idioma Español", 40, "Norte", 2);
call sp_salones_create("J-11", "Salon de Idioma Inglés", 25, "Este", 2);
call sp_salones_create("H-32", "Salon de Electricidad", 56, "Este", 3);
call sp_salones_create("K-14", "Salon de Quimíca", 34, "Este", 2);
call sp_salones_create("S-32", "Salon de Estadística", 20, "Norte", 2);
call sp_salones_create("M-62", "Salon de Ética", 50, "Este", 3);

call sp_salones_read();

call sp_salones_read_by_id("K-14");

call sp_salones_update("C-29", "Salon de informatica para 5to perito", 30, "Norte", 2);

call sp_salones_delete("C-29");

DELIMITER $$
CREATE PROCEDURE total_salones
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM salones;
END $$

SET @total = 0;
CALL total_salones( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- CRUD CARRERAS --
call sp_carreras_tecnicas_create("IN5BM", "Informatica", "5to perito","B","Matutina");
call sp_carreras_tecnicas_create("IN4BV", "Administración de Empresas", "4to perito","B","Vespertina");
call sp_carreras_tecnicas_create("IN6DV", "Electronica Industrial", "6to perito","D","Vespertina");
call sp_carreras_tecnicas_create("IN6CM", "Mécanica", "6to perito","C","Matutina");
call sp_carreras_tecnicas_create("IN4CV", "Contabilidad", "4to perito","C","Vespetina");
call sp_carreras_tecnicas_create("IN5AV", "Diseño Gráfico", "5to perito","A","Vespertina");
call sp_carreras_tecnicas_create("IN5AM", "Electricidad", "5to perito","A","Matutina");
call sp_carreras_tecnicas_create("IN4EM", "Negocios Internacionales", "4to perito","E","Matutina");
call sp_carreras_tecnicas_create("IN6DM", "Mecatrónica", "6to perito","D","Matutina");
call sp_carreras_tecnicas_create("IN4EV", "Marketing", "4to perito","E","vespertina");

call sp_carreras_tecnicas_read();

call sp_carreras_tecnicas_read_by_id("IN5AV");

call sp_carreras_tecnicas_update("IN5AV", "Informatica", "5to perito",'A',"Vespertina");

call sp_carreras_tecnicas_delete("IN5BM");

DELIMITER $$
CREATE PROCEDURE total_carreras_tecnicas
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM carreras_tecnicas;
END $$

SET @total = 0;
CALL total_carreras_tecnicas( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- CRUD INSTRUCTORES --

call sp_instructores_create("01","Luis","Esteban","Josue","Aguilar","Valle","5tas Av Zona 12","iabarcae@yahoo.es","23856790","1980-03-04");
call sp_instructores_create("02","Angel","Ramiro","","Batres","Dominguez","Diagonal 6, 19-30 Zona 10","Sb.nashxo.sk8@hotmail.com","53205404","1992-02-19");
call sp_instructores_create("03", "José","Ramón","Alberto","Peña","Morales","Diagonal 6, 10-65 Zona 10","ikis_rojo@hotmail.com","23829100","1971-11-10");
call sp_instructores_create("Juan","Carlos","","Lopéz","Muralles","23 Calle 1-05 Zona 1","niikhox@hotmail.com","22210705","1974-06-30");
call sp_instructores_create("05", "Carlos","Leonel","","Herrera","Luna","20 Calle 7-62 Zona 7","dantekol@hotmail.com","22381381","1993-11-29");
call sp_instructores_create("06", "Guillermo","Moises","","Gómez","Monterroso","11 Av 7-38 Zona 1","pato_one@hotmail.com","53313932","1989-12-11");
call sp_instructores_create("07", "Pedro","Josue","","García","Vanegas","10 Av 3-76 Zona 4","joacocordero@gmail.com","23604503","1998-04-29");
call sp_instructores_create("08", "Wesly","Isaac","","Mendoza","Perez","11 Calle 0-65 Zona 10","pecmor63@gmail.com","23328081","1981-07-06");
call sp_instructores_create("09", "Ezequiel","Anderson","","Sosa","Franco","37 Calle A 19-09 Zona 12","aespinz@hotmail.com","24769885","1978-07-21");
call sp_instructores_create("010", "Francisco","Gabriel","","Aquino","Barrios","7a. Av. 9-63 Zona 9","filipofox@hotmail.com","23315060","1988-10-26");

call sp_instructores_read();

call sp_instructores_read_by_id("01");

call sp_instructores_update("010", "Francisco","Gabriel","Matchados","Aquino","Barrios","7a. Av. 9-63 Zona 9","filipofox@hotmail.com","23315060","1988-10-26");

call sp_instructores_delete("");

DELIMITER $$
CREATE PROCEDURE total_instructores
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM instructores;
END $$

SET @total = 0;
CALL total_instructores( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- CRUD HORARIOS --

call sp_horarios_create("07:00:00","12:00:00",1,0,1,0,0);
call sp_horarios_create("08:00:00","9:00:00",0,1,1,0,1);
call sp_horarios_create("09:00:00","10:00:00",1,1,0,0,1);
call sp_horarios_create("10:00:00","11:00:00",0,0,0,1,1);
call sp_horarios_create("11:00:00","12:00:00",0,0,1,0,1);
call sp_horarios_create("12:00:00","13:00:00",0,0,0,0,1);
call sp_horarios_create("07:00:00","8:00:00",0,0,1,0,0);
call sp_horarios_create("08:00:00","9:00:00",1,0,0,0,1);
call sp_horarios_create("09:00:00","10:00:00",1,0,0,0,0);
call sp_horarios_create("10:00:00","11:00:00",0,1,0,0,0);
call sp_horarios_create("7:30:00","11:00:00",0,1,0,0,1);

call sp_horarios_read();

call sp_horarios_read_by_id("1");

call sp_horarios_update("");

call sp_horarios_delete("5");

CALL sp_horarios_report();

DELIMITER $$
CREATE PROCEDURE total_horarios
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM horarios;
END $$

SET @total = 0;
CALL total_horarios( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- CRUD CURSOS --

call sp_cursos_create("Inglés","2022","50","40","DT4CV","1","10","B-23");
call sp_cursos_create("Estadistica","2022","25","20","DT6EM","2","13","B-25");
call sp_cursos_create("Taller","2022","25","5","DT6EV","3","15","B-25");
call sp_cursos_create("Quimica","2022","25","16","EL4AM","4","17","B-31");
call sp_cursos_create("Matematicas","2022","45","15","EL4CV","5","18","B-99");
call sp_cursos_create("Ética","2022","44","15","ELE4BM","8","19","C-19");
call sp_cursos_create("Cálculo","2022","34","29","IN4V","9","20","D-9");
call sp_cursos_create("Dibujo","2022","40","27","IN4EV","10","22","D-11");
call sp_cursos_create("Informatica 2022-2023","2022","35","15","IN4EV","1","23","C-29" );
call sp_cursos_create("Informatica 2022- 2023","2023","35","15","IN4CV","4","9","C-29" );
	
call sp_cursos_read();

call sp_cursos_read_by_id("8");

call sp_cursos_update("");

call sp_cursos_delete("");

DELIMITER $$
CREATE PROCEDURE total_cursos
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM cursos;
END $$

SET @total = 0;
CALL total_cursos( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------


-- CRUD ASIGNACIONES_ALUMNOS --
call sp_asignaciones_alumnos_create("2021099","13","2022-01-01");
call sp_asignaciones_alumnos_create("2022010","14","2022-02-01");
call sp_asignaciones_alumnos_create("2022056","15","2022-03-01");
call sp_asignaciones_alumnos_create("2022123","16","2022-04-01");
call sp_asignaciones_alumnos_create("2021078","17","2022-05-01");
call sp_asignaciones_alumnos_create("2021055","18","2022-06-01");
call sp_asignaciones_alumnos_create("2021042","19","2022-07-01");
call sp_asignaciones_alumnos_create("2021167","20","2022-08-01");
call sp_asignaciones_alumnos_create("2021099","21","2022-09-01");
call sp_asignaciones_alumnos_create("2022123","22","2022-10-01");

call sp_asignaciones_alumnos_read();

call sp_asignaciones_alumnos_read_by_id("10");

call sp_cursos_update("");

call sp_cursos_delete("");

DELIMITER $$
CREATE PROCEDURE total_asignaciones_alumnos
(OUT total INTEGER)
BEGIN
    SELECT COUNT(*) INTO total FROM asignaciones_alumnos;
END $$

SET @total = 0;
CALL total_asignaciones_alumnos( @total );
SELECT @total;

-- -------------------------------------------------------------------------------------------

-- Inserciones Usuario y Rol --

INSERT INTO rol(id, descripcion) VALUES (1, "Administrador");
INSERT INTO rol(id, descripcion) VALUES (2, "Estandar");

INSERT INTO usuario(user, pass, nombre, rol_id) VALUES("root", "admin", "Usuario Administrador", 1);
INSERT INTO usuario(user, pass, nombre, rol_id) VALUES ("kinal", "12345", "Usuario Estandar", 2);
