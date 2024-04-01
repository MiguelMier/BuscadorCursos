# Buscador de cursos

Somos una empresa que provee de cursos a través de nuestra página web. Tecnológicamente se requiere un cambio importante, ya que la página web se actualiza a una arquitectura desacoplada con un frontal en Angular y un back-end en Java Spring. La comunicación entre capas será vía API.

Adicionalmente se querrá proveer estos cursos a terceras empresas vía API. En un primer momento, ya que no se tienen claros todos los requerimientos, se utilizará el mismo API para el buscador desde Angular que para las empresas externas.

Este API debe devolver la lista de cursos pudiendo filtrar por área, número de horas y modalidad (presencial/online/mixto). Esta lista tan solo debe devolver el id del curso, nombre, área, número de horas, modalidad y precio. Se debe poder consultar el detalle de un curso. En el detalle, además, debemos poder conocer: el periodo de matriculación, si tiene una certificación asociada, en caso de presencial o mixto la localidad y dirección, la lista de formadores (nombre, apellidos y link a linkedn), las fecha de inicio y final de la formación.

Nota: la modalidad mixta indica que parte del curso es presencial y parte online.

El api debe proveer de la posibilidad de matriculación de un alumno. Para matricularse en un curso se debe indicar el nombre, apellidos y DNI del alumno.

Desde el departamento de Marketing y gestión de formación necesitan conocer el volumen y tipologías de búsquedas de cursos, independientemente del origen (empresas externas o propia web) para identificar las búsquedas de los usuarios y los cursos más interesantes. Como la explotación de esta información se realizará más adelante mediante Elastic Search, se requiere que la trazabilidad de búsquedas y acceso a cursos se guarde en ficheros de log. Es decir, debemos guardar en un fichero de log qué consultas se han realizado, y qué solicitudes se han realizado al detalle de cada curso. Cada vez que se realice una búsqueda o se solicite el detalle de un curso se guardará una línea en el fichero de log.

Aprovechando esta funcionalidad de logs, sistemas solicita que se genere un fichero de log de rendimiento en el que se registre el tiempo dedicado para realizar cada búsqueda. Nota: Este requisito es importante dejarlo para el final y buscar una solución lo más elegante posible.

Debido a que habrá dos equipos (front y backend) y a que se tienen que integrar terceras empresas, se debe realizar en una primera fase la definición de los endpoints, y una vez cerrada la definición se comenzará con su implementación.

El departamento de arquitectura nos solicita que la aplicación cumpla las especificaciones restFul

Para el ejercicio no es necesario implementar persistencia de datos, podría ser una mejora del ejercicio.

## DEFINICIÓN DE ENDPOINTS

GET: "/curso"
Devuelve todos los cursos de la BD (fakebd)

GET:"/curso/{id}"
Devuelve el curso cuyo id se especifica
Param: String id

GET: "/area/{area}"
Devuelve los cursos cuya area se especifica
Param: String area

GET: "/hora/{hora}"
Devuelve los cursos cuyas horas se especifica
Param: double hora

GET: "/modalidad/{modalidad}"
Devuelve los cursos cuya modalidad se especifica
Param: String modalidad --> luego se comprueba la modalidad del enum

POST: "/matricular"
Matricula al alumno deseado en el curso deseado
Body: Alumno alumno, String idCurso

