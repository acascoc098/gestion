# Gestión Académica

Nos piden hacer un sistema simple de gestión para un centro educativo con MVC Spring, Thymeleaf como motor de plantillas y MySQL con Docker. Hay que crear la base de datos al crear el contenedor Docker.

Se necesita almacenar información de:

* Profesores 
  * usuario 
  * password
  * nombre
  * apellidos
  * email

* Alumnos 
  * usuario
  * password
  * nombre
  * apellido 
  * teléfono
  * email

* Gestores 
  * usuario 
  * password

* Asignaturas 
  * nombre
  * curso
  * ciclo

`Teniendo en cuenta que una asignatura la pueden impartir varios profesores (máximo dos) y se pueden matricular hasta hasta 32 alumnos.`

Se debe implementar un sistema de login y proteger la sesión con él. El usuario que hace login puede ser gestor, profesorado o alumnado (`exclusivo`). Los usuarios con perfil de gestor (como mínimo hay que implementar este rol) pueden ver alumnos, profesores y asignaturas, hacer el CRUD de dichas tablas, así como matricular a alumnos de asignaturas y asignar profesores a las mismas.

Hay que implementar tres maestros detalle: 
* Seleccionando un profesor, sus asignaturas
* Seleccionando un alumno sus asignaturas
* Seleccionando una asignatura el profesorado que la imparte y el alumnado matriculado

Los alumnos pueden ver sus asignaturas así como el profesorado que las imparte. El profesorado puede ver las asignaturas que imparte así como la lista de alumnos matriculados.

Hay que hacer un menú en BootStrap 5.3.3 desplegable y que funcione y que, según el rol se muestran u ocultan partes. Hay que usar WebJars como hemos visto en clase.

Recuerda usar la nueva nomenclatura en Thymeleaf.

_Sube el proyecto como un archivo ZIP._

## Proyecto Maven

**Spring starters**:
* **Spring Web**: Soporte para API REST básico (no HATEOAS) y Web
* **Spring Data JPA**: Soporte JPA (persistencia clases)
* **Lombok**: Para los getters, setters, etc. automágicos
* **MySQL Driver**: Driver de esa BBDDR
* **DevTools**: Para desarrollo más fácil
* **Thymeleaf**: Motor de plantillas HTML

**En el `.gitignore` deberíamos incluir el archivo `.env` de la carpeta de la pila de contenedores así como el `application.properties` del proyecto Spring.**

## Clases entidad (POJO) y JPA

**Anotaciones Lombok**:

* `@Data`: Genera todos los getters y setters, toString, hashCode y compare. Genera el constructor con todos los atributos.
* `@NoArgsConstructor`: Genera el constructor vacío que hace falta para JAXB.


**Anotaciones jakarta.persistence** (antiguamente javax.persistence):

* `@Entity`: El objeto (clase) marcado con esta anotación será una tabla en la base de datos.
* `@Id`: El atributo marcado con esta anotación será la clave primaria de la tabla correspondiente al objeto o clase.
* `@ManytoOne`: Marcamos así atributos que son a su vez entidades (clases modelo) con los que tenemos una relación de muchos a uno. Lo ponemos en atributos que son a su vez clases entidad que yo he definido (para indicar clave foránea)
* `@ManyToAny`: Igual que anterior pero cuando puede ser NULL la clave foránea referenciada
* `@ManyToMany`: Muchos a muchos, se creará una entidad intermedia como cuando en un modelo entidad-relación tenemos una relación de muchos a muchos.
* `@OnetoMany`: Marcamos así atributos que son a su vez entidades (clases modelo) con los que tenemos una relación de uno a muchos (será una lista o similar este atributo).
* `@OneToOne`: Relación de uno-a-uno.
* `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Unido a `@Id`, será el *AUTO_INCREMENT* de MySQL (en la tabla asiciada a ese objeto).
* `@Column(length = 25)`: En un atributo de una clase entidad fija la longitud del VARCHAR, por ejemplo.

## Controlador en Spring

* **@Controller**: La clase etiquetada con esto será un controlador que gestiona rutas.
* **@RequestMapping(/ruta)**: La ruta base para el controlador o clase etiquetada con él será *ruta*.
* **@GetMapping(/ruta)**: Encima de un método, le dice al controlador que ejecute dicho método al alcanzar ese endpoint o ruta mediante un verbo HTTP GET.

## GIT
Enlace al repositorio:
[Gestión](https://github.com/acascoc098/gestion.git)
`Rama dev`

Autora: **Andrea Castilla Cocera**