# Changelog
Todos los cambios de este proyecto estarán documentados en este archivo.

El formato está basado en [SemVer](https://semver.org/spec/v2.0.0.html).

## [UNRELEASED]

### Added

#194691 Se a�ade una configuraci�n en el portlet de activitiesList para mostrar modulos espec�ficos.

### Fixed

#192412 Se comprueba si el LearningActivityResult es nulo a la hora de obtener la puntuación del usuario en la vista de resultados de la actividad de tipo test
#194142 Valores mayores que 100. Corregido que cuando el tiempo del player es mayor que la duración deje el valor 100. 
### Updated

#192169 Se mete un id a los input de tipo radio de la actividad de encuesta
#192172 Se añade un enlace al curso en el portlet de estadísticas. Si el curso tiene ediciones se muestra un enlace al lado del título
#192171 Se actualiza el título y la descripción del assetEntry de los learningActivity cuando se actualizan éstas
#191782 Se cambian los div del progreso en la actividad de tipo test por una etiqueta progress ya que en ocasiones la barra tenía la anchura mal


## [4.3.3](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.3)

### Fixed

#193502: Corregido el portlets no disponible cuando el curso está aprobado o suspensoen el progreso del usuario.



## [4.3.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.2)

### Fixed

#187773: Se manda a la creación de la edición el nombre de la edición (se mandaba el del curso concatenado) para evitar DuplicateName con el nombre del grupo.

## [4.3.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.1)

### Fixed
- #188308 Corrección sobre los la visualización de los tests con paginación de preguntas


## [4.3.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.0)

### Fixed

- #183348 Las ediciones no heredan el tipo de inscripción ni el diploma del curso padre (faltan diplomas)
- #186955 Visibilidad o no visibilidad de las actividades no se hereda del curso a las ediciones
- #186905 URLs de ediciones borradas, ya no salen url que ya existan para las ediciones

### Added

- #184759 En las estadítsticas, se puede pinchar sobre los cursos con ediciones para ver los datos concretos de esa edición
- #185783 Se muestra la nota del curso en el portlet de notas

### Updated

- #184759 Se modifican las estadísticas para que saquen la suma de todas las ediciones.
- #182359 Se modifica el editor de fechas para tener en cuenta todas las fechas nulas tanto de módulos como de actividades

### Fixed

- #182374 Se modifica cuándo se pone la fecha fin de un módulo porque cuando suspendías las actividades no te la rellenaba. 
- #182374 Modificación en los métodos de evaluación para que no compruebe si quedan intentos, solo mire la fecha de fin de la actividad.

## [4.2.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.2.1)

### Fixed

- #168477: Corrección sobre los cursos finalizados de wemooc.


## [4.3.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.0)

### Fixed

- #179229 Se corrigen las comprobaciones de url y nombre al crear ediciones

## [4.2.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.2.0)

### Added

- #167661 Se añade la posiblidad de publicar notas individuales después de publicar la general en las actividades de evaluación
- #176051 Se añade modo de visualización para columna grande en el portlet de inscripción
- #176473 Se exportan todos los resultados de los test de un usuario independientemente del tipo de pregunta

### Deleted

- #176473 En la exportación de resultados del usuario para los test se elimina el id de las preguntas y respuestas

### Updated

- #167661 Cambio de literal en actividad de evaluación
- #177082 Permiso de módulo por defecto al scope group y no se ponen fechas al módulo por defecto

### Fixed

- #176579 Corrección al eliminar turores y editores de un curso
- #175947 Corrección en tabla de procesos asíncronos cuando configuras solo un tipo
- #176560 Si la respuesta de las preguntas en la importación son númericos se importan
- #168038 Ya no se sobreescribe el título del curso en el idioma por defecto cuando estás en otro idioma
- #177114 Los módulos aparecían bloqueados cuando no tenáin fecha
- #181637 Corrección al editar preguntas de los recursos externos.


## [4.1.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.2)

### Fixed

- #177149 Corregido el modo observador en los tests, solo muestra el feedback de lo que has realizado.


## [4.1.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.1)

### Fixed

- #178231 Se comprueba dentro del método de evaluación ponderado si la actividad ha sido marcada como requerida de aprobar en la configuración de las ponderaciones.


## [4.1.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.0)

### Added

- #170072 Se añaden mensajes asíncronos al finalizar el clonado de curso y la creación de la edición para acciones a posteriori desde proyectos.
- #168113 Se añaden parámetros para embeber las acciones específicas
- #173883 Se añade un mensaje cuando estás en las actividades en modo lectura
- #158614 Parámetro rol en los mensajes de bienvenido y baja del curso

### Updated

- #172729 Se cambia el orden de asociación a curso para añadir primero el rol y de esta forma no se envíe el correo de bienvenida o despedida a los tutores y editores
- #166015 Se muestra la actividad de evaluación en modo consulta
- #174290 Se añade por interfaz el jsp para mostrar los resultados de una actividad en el gradebook

### Fixed

- #170041 Cambiado el formato de fechas al de liferay debido a un bug en el SimpleDateFormat.
- #170849 Se mantiene en la vista de ediciones cuando abres cieras o eliminas una
- #172627 No se pone fecha fin a los resultados de las actividades de evaluación hasta que no se publica
- #163474 Cuando el test es la última actividad y al corregirlo ya estás en modo observador no se mostraba la nota

### Deleted

- #161554 Se elimina el hook del search container

## [4.0.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.0.1)

### Fixed

- #172634 Se heredan los campos personalizados de las actividades al duplicar curso.
- #172535 Corrección de las preguntas tipo encuesta al exportar, duplicar y crear ediciones

## [4.0.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.0.0)

### Added

- #167833 Añadidos los servicios countActiveByCompanyIdClassNameIdClassPK y getActiveByCompanyIdClassNameIdClassPK para ver los procesos que están actualmente en curso para una acción.
- #167536 Añadida configuración para ocultar las opciones dinámicas. Se pasan las preferencias al portlet destino para poder utilizarlas.
- #168060 Ampliación del portlet de "mis cursos" de wecorp. Nuevos servicios
- #165787 En la gestión de cursos en Admin->Cursos se ha añadido en la configuración la posibilidad de habilitar los filtros de fechas de ejecución  para realizar las búsquedas de cursos, buscará los cursos cuya fecha de inicio de ejecución sea mayor que la fecha de inicio seleccionada y/o cuya fecha de fin de ejecución sea menor a la fecha de fin seleccionada.

### Updated

- #167994 Unificación del servicio de mis cursos para usar el mismo finder para mis cursos y mis cursos finalizados. Se modifica el resgistry de los métodos de calificación para que busque siempre los métodos de calificación que están dentro del LMS en su contexto, independientemente de donde se llamen.
- #167833 Añadido cache a false para los servicios de los procesos asíncronos.
- #167536 Se mandan las preferencias, el cur y el delta al portlet embebido en las acciones de administración dinámicas
- #168795 Protegido para evitar nullpointerexception en los tipos de cursos.

### Fixed

- #163617 Se elimina la barra después de web en la acción de ir al curso
- #164214 Se establece correctamente el texto en la importación de preguntas. 
- #162383 Se trunca el nombre del grupo a 150 caracteres para que no de problemas en la creación de la edición.
- #166052 Protegido cuando la celda es nula. Eliminación de segundo mensaje de error. Establecidos a xls y xlsx los tipos aceptados.
- #154930 Se modifica la actividad de desarrollo para que audite la corrección y se obligue a meter nota con los comentarios para evitar fallos.
- #161331 Se actualiza el número de usuarios para solo tener en cuenta a los estudiantes al aceptar una petición de registro.
- #163768 Se establecen las categorías de las actividades al duplicar antes de ser creadas para evitar errores con categorias obligatorias.163768
- #167390 Se copian los campos personalizados de las actividades al crear la edición.
- #163688 Se actualizan los estilos para los tipos de cursos sin el tema de wecorp.
- #167409 Creación de un nuevo método para duplicar lo específico de cada actividad. Implementación para los recursos externos.
- #167628 Cambiada la visualización de los comentarios de la actividad de desarrollo en el gradebook.
- #107687 Se ordena por peso en la visualización de resultados de los tests y en las correcciones.
- #160565 Se visualiza correctamente la pregunta combo en las encuestas.
- #167642 Se establece el literal estudiantes en vez de site member.
- #164518 Se deja de concatenar la URL en el borrado de preguntas. Se establece el mensaje success.
- #167845 Se establece el literal y la descripción de la actividad en la previsualización
- #160872 Se exportan e importan correctamente los permisos de módulos y actividades en la importación de cursos.
- #169128 Corrección al duplicar curso, se pasa el visible a false en el momento de la reindexación para que no aparezca en catálogo.

## [3.9.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.9.1)

### Fixed

- #166068 Se conserva el id del tipo de curso cuando se actualiza un curso.

## [3.9.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.9.0)

### Added

- #162986 Se ha añadido una configuración nueva al portlet de inscripción para que tenga en cuenta solo las ediciones cuyo tiempo de ejecución sea posterior a la fecha actual. 
  Si está marcado este check, no se tendrán en cuenta estos cursos y no se le mostrarán al usuario aunque está inscrito, permitiéndole matricularse a otras ediciones que estén disponibles.
- #159785 Nueva funcionalidad Limitacion del tiempo de estudio, se incluye en el metodo de inscripcion un nuevo campo a mostrar de días/horas para poder finalizar el curso.
- #164258 Respuestas ordenadas aleatoriamente en las actividades tipo test. Se incluye la opción en las actividades de Tipo Test que las preguntas de tipo Opciones/Multiple se puedan desordenar las respuestas.

### Updated

- #162450 Modificaciones gestión de ediciones Wecorp: Cambios en la navegación
	- En el botón "Acciones" que se muestra al editar un curso padre se ha añadido la opción de "Ver ediciones"
	- En el botón de acciones de la vista de "Editar una edición" se ha añadido "Editar curso padre"
	- Se muestra el botón de "Acciones" que aparece al editar un curso padre en la vista de editar una edición
	- Al editar una edición, debajo del nombre de la edición, se muestra: "Curso padre: Nombre del curso padre".
	- En el listado de ediciones de un curso se ha añadido el botón "Editar curso padre".
	- Se añaden nuevos campos a mostrar en los listados de cursos y ediciones
- #160445 Cambios para la auditoría, añadidas trazas y capturas de excepciones y quitada la auditoría de asociar usuario ya que está duplicada.
- #160557 Cambio en el informe de resultados de las encuestas. El funcionamiento ahora es una fila por respuesta de usuario, dejando blancos si no ha contestado.
- #160445 Añadido enlace para ir a las ediciones desde la lista de cursos. Añadido el tipo de curso para la edición.
- #147332 Cambiado literal de la ayuda de elegir equipo.
- #147332 Cambiado el literal de ayuda de los equipos en editar actividad
- #159623 [Portlet inscripción] - No permitir desinscribrir a los usuarios cuando han finalizado el curso
	- Se incluye la condición de finalización del curso.
	- Se actualizan idiomas para no perder codificaciones necesarias para catalán.
	- Se incluye mediante configuración del portlet, estando por defecto permitido.
- #162986 La funcionalidad de filtrar las ediciones cuyo tiempo de ejecución sea posterior a la fecha actual se configura desde la configuración del LMS en vez de la configuración del  portlet de inscripción.
- #159785 Se comprueba también que el usuario esté registrado en el curso a la hora de mostrar el texto de los días/horas para poder finalizar el curso.

### Fixed

- #162450 Modificaciones gestión de ediciones Wecorp: Cambios en la navegación. Comprobación de permisos en acciones
- #162971 Visualización incorrecta del listado de ediciones de un curso cuando el theme del portal no es Wecorp. Añadidos los estilos de generic-pop-up al propio portlet
- #159903 Corregido que al cambiar de tipo de inscripción no recuperaba bien el valor (Cambio get por fetch para que lo recoja de la BBDD). 
Eliminados imports no usados. Protegidos los expandos por si hay algún error.
- #162986 Corregido error en el que se podia desinscribir en el curso de un itinerario a traves del curso padre, aunque estuviera deshabilitada la desinscripcion.
- #163914 Corregido desfase horario de las ediciones al ver el listado de cursos.
- #165220 Resultados de las Búsquedas en editar actividade. Problema de paginación cuando se hace búsqueda por palabras clave en la búsqueda de recursos en la actividad Recurso mediateca.

### Refactor
 
- #159623 Meter inicialización courseResult en if para mejora de rendimiento

## [3.8.3](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.3)

### Fixed 

- #163615 Se filtran los tipos de curso por companyId

## [3.8.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.2)

### Fixed 

- #161546 Corrección en la importación masiva de ediciones, había un error al guardar el groupId de las ediciones.

## [3.8.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.1)

### Added

- #158723 Accesibilidad, añadidos estilos en el portlet para linkar correctamente imagen cambiada por accesibilidad que estaba en el tema.

### Fixed 

- #157508 Link correcto al css del mediaelementplayer.

## [3.8.0]

### Added(https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.0)

- #157779 Añadidos expandos para actividades
- #157161 y #157162 Añadir columna opcional de email a los portlets de gestión de inscripciones y gestión de estudiantes
- #154289 Añadidos campos y configuración al portlet de procesos asíncronos para los informes por batch
- #151700 Añadida traducción cuando no tienes permisos para inscribirte en el curso
- #153195 Nueva funcionalidad en Actividad Tipo Test de exportar preguntas en formato Excel no contempla todos los formatos de preguntas.
- #138277 El alumno y el tutor deben poder eliminar el archivo de la actividad de desarrollo
- #153714 Servicio borrado learningactivityresult NullPointer. Se incluye catch para el nullPointer. Además, se incluye funcionalidad para no permitir mover actividad de módulo si hay intentos sobre la misma.
- #154291 Añadida interfaz y desarrollo para realizar acciones genéricas para la administración de cursos
- #155181 Añadido servicio para añadir curso con fechas de ejecución
- #153200 Añadido el mecanismo para inscorporar portlet propios al método de inscripción.

### Changed

- #158014 Se controla que si es en las activdiades P2P el valor numValidations del extraData es vacío no intente convertirlo a número.
- #151694 Cambios para que no guarde la búsqueda del usuario anterior. Corregidos fallos de sas en el css del mediaelement
- #154429 Quitamos el texto de descargar documetno en los adjuntos de las actividades de recurso externo
- #157865 Se actualiza el orden de las preguntas en el duplicado de cursos y en la importación
- #152030 Se comprueba si el curso está cerrado cuando se inscribe por servicio
- #157265 Al editar la ficha del curso miramos el número de inscritos sin contar los tutores y editores.
- #157804  No se van a mostrar las preguntas en el modo observador en la actividad de recurso externo
- #155029 Se modifica la importación para que las actividades y los módulos se importen con el mismo uuid, de esta forma, cuando se importan varias veces, no se duplican los módulos y las actividades si no que se sobreescriben.
- #157195 No se muestra el icono de borrar el lar en la exportación si no tienes permiso para borrar cursos, ya que es el que se controla en back cuando le das.
- #154985 En la edición de recurso interno se pasan los parámetros de descripción y título por post. Queda pendiente que en la búsqueda de recursos al paginar se pierden.
- #152552 Establecido el role a los formularios en todo el componente para accesibilidad.
- #153797 Se mueve la clase ExportUtil al service.
- #139242 Modificar la url para que desde la lista de actividades salga el preview de la actividad test.
- #154058 Cambio para que cuando no hay actividades obligatorias en un módulo se recálcule con el 100%
- #151340 Refactorización de todo el portlet de estadísticas generales
- #137425 Modificaciones buscador facetas para el buscador de cursos
- #102776 No se puede borrar un curso si hay estudiantes que lo han terminado
- #154466 Se modifica para que siempre se llame a los diplomas externos, no sólo con el CourseResult pasado
- #153457 Cambios para maquetación: añadida clase genérica a portlet de inscripción y  Span para separar fechas
- #144540 Idioma en la creación de cursos, cambio para que coja por defecto el del usuario.

### Fixed

- #140958 Corregidos problemas en auditoría cuando no encuentra un componente en el Registro de las clases de log
            Descomentada línea de auditoría que se comentó por error. No se estaba auditando en el lms_auditentry
- #158119 Cambio para resolver la incidencia con la valoración de P2P que no actualiza bien el popup
- #157814 Si antes se había cerrado un curso y volvias a reabrir, al terminar volvía a hacer la acción anterior de cerrar por el parámetro redirect que se ha eliminado.
- #157886 Enlace a edición de curso corregido desde el curso padre
- #158463 No se guardan los expandos de los módulos
- #157958 Corrección al exportar estadísticas generales
- #157508 Corrección para visualizar los videos de vimeo sin controles.
- #157748 Corrección sobre las tests con contraseña
- #157169 La columna de actividad precedente salía vacía en las estadísticas del curso.
- #157581 Desde la ficha del curso no se estaba pasando el courseId al include de los diplomas, por lo que no le estaba llegando y no aparecían seleccionados.
- #147490 Solucionado el problema al corregir las preguntas de rellenar huecos y arrastrar
- #157185 Modificación del javascript de buscar recurso en actividad de recurso interno para que funcione en ie
- #146325 Cambiado el componente de navegación por módulos para que se comporte correctamente.
- #157265 El número máximo de usuarios en un curso sólo debe contar a los estudiantes
- #155029 Corregido error al importar un lar de un curso sin borrar lo anterior, resuelta la excepción DuplicateFileException
- #153797 Corrección de la incidencia cuando los archivos que se exportan llevaban un punto al final.
- #143338 Corrección de preview test y asset de módulo al crear curso.
- #151635 Actividad de tipo test, corrección al exportar respuestas de usuarios a CSV
