# Changelog
Todos los cambios de este proyecto estarán documentados en este archivo.

El formato está basado en [SemVer](https://semver.org/spec/v2.0.0.html).

## [4.6.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.6.0)

### Added

- #188000: Añadida nueva etiqueta del nombre de pila del usuario en los mailings del mensaje bienvenida y de baja de cursos
- #191542: Al duplicar una edición o curso hay que configurar las fechas de ejecucion además de las de inscripción
- #198292: Añadida la posibilidad de ordenar las ediciones de un curso

### Updated

- #198251: tipos de cursos, se tienen en cuenta los inactivos, se modifica para establecer el tipo de tipo de curso en la creación y en la edición.

### Fixed

- #198695: Cambiado el metodo de softInitializeCourseResult para que lo de iniciado en actividades scorm, mpc y xapi. Anteriormente
  se daba por iniciada la actividad pero no el curso
  
- #199064: Corregido el problema con la importación, estaba dando el rol 0, ahora se establece el rol correctamente


## [4.5.3](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.5.3)

### Updated

- #200139: Cambios en los metodos de obtencion de los intentos para un mejor rendimiento. Se cambiand DynamicQuerys por CustomQuerys que devuelven un unico resultado cuando solo se necesita el primero.


## [4.5.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.5.2)

### Updated

- Cambios en el registry de las actividades para mejorar rendimiento, establecidas las variables a estáticas para evitar que se lean en cada acceso.

## [4.5.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.5.0)

### Fixed

- #195910 Corrección sobre el assetRender de las actividades para evitar pantalla en blanco al ir a enlace después de editar la actividad.
- #197120 No cierra el result de la actividad en los tests cuando tiene el mejorar nota.

## [4.4.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.4.0)

### Added

- #194691 Se añade una configuración en el portlet de activitiesList para mostrar modulos específicos.
- #178357 Se muestra el sumatorio de las ponderaciones en el portlet para configurar el método de evaluación ponderado
- #173313 Se añade la posibilidad de enviar adjunto un evento de calendario con la fecha de inicio del curso en el mail de bienvenida.
- #194593 Se devuelve un informe de resultados en la inscripción masiva de usuarios a un curso.
- #194984 Tipos de cursos fijos

### Fixed

- #192412 Se comprueba si el LearningActivityResult es nulo a la hora de obtener la puntuación del usuario en la vista de resultados de la actividad de tipo test
- #194142 Valores mayores que 100. Corregido que cuando el tiempo del player es mayor que la duración deje el valor 100.
- #182977 Se clona el campo improve de las actividades cuado se clona un curso
### Updated

- #191383 Se modifica el texto que se muestra a los usuarios en la actividad de tipo test
- #191384 Se añade una configuración para mostrar los cursos completados que sigan en periodo de ejecución como "En curso" en vez de finalizados
- #191384 Se añaden nuevos métodos para buscar los cursos de un usuario que estén finalizados (ya ha pasado su tiempo de ejecución).
- #192169 Se mete un id a los input de tipo radio de la actividad de encuesta
- #192172 Se añade un enlace al curso en el portlet de estadísticas. Si el curso tiene ediciones se muestra un enlace al lado del título
- #192171 Se actualiza el título y la descripción del assetEntry de los learningActivity cuando se actualizan éstas
- #191782 Se cambian los div del progreso en la actividad de tipo test por una etiqueta progress ya que en ocasiones la barra tenía la anchura mal
- #192653 Se añade un segundo de retardo al refrescar los portlets activity navigator y activities list al completar un video
- #193973 No se muestra un enlace para ir a las ediciones en lo cursos en los que no se pueden crear/editar ediciones en la columna "Nº edicionesª de la administración de cursos
- #182977 Cuando un usuario tiene la nota máxima no le sale el confirm de mejorar nota
- #194392 Se permiten actividades obligatorias con peso 0 en el método de evaluación ponderado dependiendo de una configuración del lms
- #178357 Se añaden validaciones javacript a la configuración del método de evaluación ponderado para controlar que la suma de las ponderaciones es 100
- #178357 Se pueden ponderar con números decimales hasta dos dígitos
- #178357 Se copia la ponderación  de las actividades cuando se clona un curso con método de evaluación ponderado
- #178357 Se clona el check de requerido cuando se clona un curso con el método de evaluación ponderado

## [4.3.3](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.3)

### Fixed

- #193502: Corregido el portlets no disponible cuando el curso está aprobado o suspensoen el progreso del usuario.

## [4.3.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.2)

### Fixed

- #187773: Se manda a la creación de la edición el nombre de la edición (se mandaba el del curso concatenado) para evitar DuplicateName con el nombre del grupo.

## [4.3.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.1)

### Fixed
- #188308 CorrecciÃ³n sobre los la visualizaciÃ³n de los tests con paginaciÃ³n de preguntas


## [4.3.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.0)

### Fixed

- #183348 Las ediciones no heredan el tipo de inscripciÃ³n ni el diploma del curso padre (faltan diplomas)
- #186955 Visibilidad o no visibilidad de las actividades no se hereda del curso a las ediciones
- #186905 URLs de ediciones borradas, ya no salen url que ya existan para las ediciones

### Added

- #184759 En las estadÃ­tsticas, se puede pinchar sobre los cursos con ediciones para ver los datos concretos de esa ediciÃ³n
- #185783 Se muestra la nota del curso en el portlet de notas

### Updated

- #184759 Se modifican las estadÃ­sticas para que saquen la suma de todas las ediciones.
- #182359 Se modifica el editor de fechas para tener en cuenta todas las fechas nulas tanto de mÃ³dulos como de actividades

### Fixed

- #182374 Se modifica cuÃ¡ndo se pone la fecha fin de un mÃ³dulo porque cuando suspendÃ­as las actividades no te la rellenaba. 
- #182374 ModificaciÃ³n en los mÃ©todos de evaluaciÃ³n para que no compruebe si quedan intentos, solo mire la fecha de fin de la actividad.

## [4.2.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.2.1)

### Fixed

- #168477: CorrecciÃ³n sobre los cursos finalizados de wemooc.


## [4.3.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.3.0)

### Fixed

- #179229 Se corrigen las comprobaciones de url y nombre al crear ediciones

## [4.2.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.2.0)

### Added

- #167661 Se aÃ±ade la posiblidad de publicar notas individuales despuÃ©s de publicar la general en las actividades de evaluaciÃ³n
- #176051 Se aÃ±ade modo de visualizaciÃ³n para columna grande en el portlet de inscripciÃ³n
- #176473 Se exportan todos los resultados de los test de un usuario independientemente del tipo de pregunta

### Deleted

- #176473 En la exportaciÃ³n de resultados del usuario para los test se elimina el id de las preguntas y respuestas

### Updated

- #167661 Cambio de literal en actividad de evaluaciÃ³n
- #177082 Permiso de mÃ³dulo por defecto al scope group y no se ponen fechas al mÃ³dulo por defecto

### Fixed

- #176579 CorrecciÃ³n al eliminar turores y editores de un curso
- #175947 CorrecciÃ³n en tabla de procesos asÃ­ncronos cuando configuras solo un tipo
- #176560 Si la respuesta de las preguntas en la importaciÃ³n son nÃºmericos se importan
- #168038 Ya no se sobreescribe el tÃ­tulo del curso en el idioma por defecto cuando estÃ¡s en otro idioma
- #177114 Los mÃ³dulos aparecÃ­an bloqueados cuando no tenÃ¡in fecha
- #181637 CorrecciÃ³n al editar preguntas de los recursos externos.


## [4.1.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.2)

### Fixed

- #177149 Corregido el modo observador en los tests, solo muestra el feedback de lo que has realizado.


## [4.1.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.1)

### Fixed

- #178231 Se comprueba dentro del mÃ©todo de evaluaciÃ³n ponderado si la actividad ha sido marcada como requerida de aprobar en la configuraciÃ³n de las ponderaciones.


## [4.1.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.1.0)

### Added

- #170072 Se aÃ±aden mensajes asÃ­ncronos al finalizar el clonado de curso y la creaciÃ³n de la ediciÃ³n para acciones a posteriori desde proyectos.
- #168113 Se aÃ±aden parÃ¡metros para embeber las acciones especÃ­ficas
- #173883 Se aÃ±ade un mensaje cuando estÃ¡s en las actividades en modo lectura
- #158614 ParÃ¡metro rol en los mensajes de bienvenido y baja del curso

### Updated

- #172729 Se cambia el orden de asociaciÃ³n a curso para aÃ±adir primero el rol y de esta forma no se envÃ­e el correo de bienvenida o despedida a los tutores y editores
- #166015 Se muestra la actividad de evaluaciÃ³n en modo consulta
- #174290 Se aÃ±ade por interfaz el jsp para mostrar los resultados de una actividad en el gradebook

### Fixed

- #170041 Cambiado el formato de fechas al de liferay debido a un bug en el SimpleDateFormat.
- #170849 Se mantiene en la vista de ediciones cuando abres cieras o eliminas una
- #172627 No se pone fecha fin a los resultados de las actividades de evaluaciÃ³n hasta que no se publica
- #163474 Cuando el test es la Ãºltima actividad y al corregirlo ya estÃ¡s en modo observador no se mostraba la nota

### Deleted

- #161554 Se elimina el hook del search container

## [4.0.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.0.1)

### Fixed

- #172634 Se heredan los campos personalizados de las actividades al duplicar curso.
- #172535 CorrecciÃ³n de las preguntas tipo encuesta al exportar, duplicar y crear ediciones

## [4.0.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v4.0.0)

### Added

- #167833 AÃ±adidos los servicios countActiveByCompanyIdClassNameIdClassPK y getActiveByCompanyIdClassNameIdClassPK para ver los procesos que estÃ¡n actualmente en curso para una acciÃ³n.
- #167536 AÃ±adida configuraciÃ³n para ocultar las opciones dinÃ¡micas. Se pasan las preferencias al portlet destino para poder utilizarlas.
- #168060 AmpliaciÃ³n del portlet de "mis cursos" de wecorp. Nuevos servicios
- #165787 En la gestiÃ³n de cursos en Admin->Cursos se ha aÃ±adido en la configuraciÃ³n la posibilidad de habilitar los filtros de fechas de ejecuciÃ³n  para realizar las bÃºsquedas de cursos, buscarÃ¡ los cursos cuya fecha de inicio de ejecuciÃ³n sea mayor que la fecha de inicio seleccionada y/o cuya fecha de fin de ejecuciÃ³n sea menor a la fecha de fin seleccionada.

### Updated

- #167994 UnificaciÃ³n del servicio de mis cursos para usar el mismo finder para mis cursos y mis cursos finalizados. Se modifica el resgistry de los mÃ©todos de calificaciÃ³n para que busque siempre los mÃ©todos de calificaciÃ³n que estÃ¡n dentro del LMS en su contexto, independientemente de donde se llamen.
- #167833 AÃ±adido cache a false para los servicios de los procesos asÃ­ncronos.
- #167536 Se mandan las preferencias, el cur y el delta al portlet embebido en las acciones de administraciÃ³n dinÃ¡micas
- #168795 Protegido para evitar nullpointerexception en los tipos de cursos.

### Fixed

- #163617 Se elimina la barra despuÃ©s de web en la acciÃ³n de ir al curso
- #164214 Se establece correctamente el texto en la importaciÃ³n de preguntas. 
- #162383 Se trunca el nombre del grupo a 150 caracteres para que no de problemas en la creaciÃ³n de la ediciÃ³n.
- #166052 Protegido cuando la celda es nula. EliminaciÃ³n de segundo mensaje de error. Establecidos a xls y xlsx los tipos aceptados.
- #154930 Se modifica la actividad de desarrollo para que audite la correcciÃ³n y se obligue a meter nota con los comentarios para evitar fallos.
- #161331 Se actualiza el nÃºmero de usuarios para solo tener en cuenta a los estudiantes al aceptar una peticiÃ³n de registro.
- #163768 Se establecen las categorÃ­as de las actividades al duplicar antes de ser creadas para evitar errores con categorias obligatorias.163768
- #167390 Se copian los campos personalizados de las actividades al crear la ediciÃ³n.
- #163688 Se actualizan los estilos para los tipos de cursos sin el tema de wecorp.
- #167409 CreaciÃ³n de un nuevo mÃ©todo para duplicar lo especÃ­fico de cada actividad. ImplementaciÃ³n para los recursos externos.
- #167628 Cambiada la visualizaciÃ³n de los comentarios de la actividad de desarrollo en el gradebook.
- #107687 Se ordena por peso en la visualizaciÃ³n de resultados de los tests y en las correcciones.
- #160565 Se visualiza correctamente la pregunta combo en las encuestas.
- #167642 Se establece el literal estudiantes en vez de site member.
- #164518 Se deja de concatenar la URL en el borrado de preguntas. Se establece el mensaje success.
- #167845 Se establece el literal y la descripciÃ³n de la actividad en la previsualizaciÃ³n
- #160872 Se exportan e importan correctamente los permisos de mÃ³dulos y actividades en la importaciÃ³n de cursos.
- #169128 CorrecciÃ³n al duplicar curso, se pasa el visible a false en el momento de la reindexaciÃ³n para que no aparezca en catÃ¡logo.

## [3.9.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.9.1)

### Fixed

- #166068 Se conserva el id del tipo de curso cuando se actualiza un curso.

## [3.9.0](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.9.0)

### Added

- #162986 Se ha aÃ±adido una configuraciÃ³n nueva al portlet de inscripciÃ³n para que tenga en cuenta solo las ediciones cuyo tiempo de ejecuciÃ³n sea posterior a la fecha actual. 
  Si estÃ¡ marcado este check, no se tendrÃ¡n en cuenta estos cursos y no se le mostrarÃ¡n al usuario aunque estÃ¡ inscrito, permitiÃ©ndole matricularse a otras ediciones que estÃ©n disponibles.
- #159785 Nueva funcionalidad Limitacion del tiempo de estudio, se incluye en el metodo de inscripcion un nuevo campo a mostrar de dÃ­as/horas para poder finalizar el curso.
- #164258 Respuestas ordenadas aleatoriamente en las actividades tipo test. Se incluye la opciÃ³n en las actividades de Tipo Test que las preguntas de tipo Opciones/Multiple se puedan desordenar las respuestas.

### Updated

- #162450 Modificaciones gestiÃ³n de ediciones Wecorp: Cambios en la navegaciÃ³n
	- En el botÃ³n "Acciones" que se muestra al editar un curso padre se ha aÃ±adido la opciÃ³n de "Ver ediciones"
	- En el botÃ³n de acciones de la vista de "Editar una ediciÃ³n" se ha aÃ±adido "Editar curso padre"
	- Se muestra el botÃ³n de "Acciones" que aparece al editar un curso padre en la vista de editar una ediciÃ³n
	- Al editar una ediciÃ³n, debajo del nombre de la ediciÃ³n, se muestra: "Curso padre: Nombre del curso padre".
	- En el listado de ediciones de un curso se ha aÃ±adido el botÃ³n "Editar curso padre".
	- Se aÃ±aden nuevos campos a mostrar en los listados de cursos y ediciones
- #160445 Cambios para la auditorÃ­a, aÃ±adidas trazas y capturas de excepciones y quitada la auditorÃ­a de asociar usuario ya que estÃ¡ duplicada.
- #160557 Cambio en el informe de resultados de las encuestas. El funcionamiento ahora es una fila por respuesta de usuario, dejando blancos si no ha contestado.
- #160445 AÃ±adido enlace para ir a las ediciones desde la lista de cursos. AÃ±adido el tipo de curso para la ediciÃ³n.
- #147332 Cambiado literal de la ayuda de elegir equipo.
- #147332 Cambiado el literal de ayuda de los equipos en editar actividad
- #159623 [Portlet inscripciÃ³n] - No permitir desinscribrir a los usuarios cuando han finalizado el curso
	- Se incluye la condiciÃ³n de finalizaciÃ³n del curso.
	- Se actualizan idiomas para no perder codificaciones necesarias para catalÃ¡n.
	- Se incluye mediante configuraciÃ³n del portlet, estando por defecto permitido.
- #162986 La funcionalidad de filtrar las ediciones cuyo tiempo de ejecuciÃ³n sea posterior a la fecha actual se configura desde la configuraciÃ³n del LMS en vez de la configuraciÃ³n del  portlet de inscripciÃ³n.
- #159785 Se comprueba tambiÃ©n que el usuario estÃ© registrado en el curso a la hora de mostrar el texto de los dÃ­as/horas para poder finalizar el curso.

### Fixed

- #162450 Modificaciones gestiÃ³n de ediciones Wecorp: Cambios en la navegaciÃ³n. ComprobaciÃ³n de permisos en acciones
- #162971 VisualizaciÃ³n incorrecta del listado de ediciones de un curso cuando el theme del portal no es Wecorp. AÃ±adidos los estilos de generic-pop-up al propio portlet
- #159903 Corregido que al cambiar de tipo de inscripciÃ³n no recuperaba bien el valor (Cambio get por fetch para que lo recoja de la BBDD). 
Eliminados imports no usados. Protegidos los expandos por si hay algÃºn error.
- #162986 Corregido error en el que se podia desinscribir en el curso de un itinerario a traves del curso padre, aunque estuviera deshabilitada la desinscripcion.
- #163914 Corregido desfase horario de las ediciones al ver el listado de cursos.
- #165220 Resultados de las BÃºsquedas en editar actividade. Problema de paginaciÃ³n cuando se hace bÃºsqueda por palabras clave en la bÃºsqueda de recursos en la actividad Recurso mediateca.

### Refactor
 
- #159623 Meter inicializaciÃ³n courseResult en if para mejora de rendimiento

## [3.8.3](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.3)

### Fixed 

- #163615 Se filtran los tipos de curso por companyId

## [3.8.2](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.2)

### Fixed 

- #161546 CorrecciÃ³n en la importaciÃ³n masiva de ediciones, habÃ­a un error al guardar el groupId de las ediciones.

## [3.8.1](https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.1)

### Added

- #158723 Accesibilidad, aÃ±adidos estilos en el portlet para linkar correctamente imagen cambiada por accesibilidad que estaba en el tema.

### Fixed 

- #157508 Link correcto al css del mediaelementplayer.

## [3.8.0]

### Added(https://github.com/TelefonicaED/liferaylms-portlet/releases/tag/v3.8.0)

- #157779 AÃ±adidos expandos para actividades
- #157161 y #157162 AÃ±adir columna opcional de email a los portlets de gestiÃ³n de inscripciones y gestiÃ³n de estudiantes
- #154289 AÃ±adidos campos y configuraciÃ³n al portlet de procesos asÃ­ncronos para los informes por batch
- #151700 AÃ±adida traducciÃ³n cuando no tienes permisos para inscribirte en el curso
- #153195 Nueva funcionalidad en Actividad Tipo Test de exportar preguntas en formato Excel no contempla todos los formatos de preguntas.
- #138277 El alumno y el tutor deben poder eliminar el archivo de la actividad de desarrollo
- #153714 Servicio borrado learningactivityresult NullPointer. Se incluye catch para el nullPointer. AdemÃ¡s, se incluye funcionalidad para no permitir mover actividad de mÃ³dulo si hay intentos sobre la misma.
- #154291 AÃ±adida interfaz y desarrollo para realizar acciones genÃ©ricas para la administraciÃ³n de cursos
- #155181 AÃ±adido servicio para aÃ±adir curso con fechas de ejecuciÃ³n
- #153200 AÃ±adido el mecanismo para inscorporar portlet propios al mÃ©todo de inscripciÃ³n.

### Changed

- #158014 Se controla que si es en las activdiades P2P el valor numValidations del extraData es vacÃ­o no intente convertirlo a nÃºmero.
- #151694 Cambios para que no guarde la bÃºsqueda del usuario anterior. Corregidos fallos de sas en el css del mediaelement
- #154429 Quitamos el texto de descargar documetno en los adjuntos de las actividades de recurso externo
- #157865 Se actualiza el orden de las preguntas en el duplicado de cursos y en la importaciÃ³n
- #152030 Se comprueba si el curso estÃ¡ cerrado cuando se inscribe por servicio
- #157265 Al editar la ficha del curso miramos el nÃºmero de inscritos sin contar los tutores y editores.
- #157804  No se van a mostrar las preguntas en el modo observador en la actividad de recurso externo
- #155029 Se modifica la importaciÃ³n para que las actividades y los mÃ³dulos se importen con el mismo uuid, de esta forma, cuando se importan varias veces, no se duplican los mÃ³dulos y las actividades si no que se sobreescriben.
- #157195 No se muestra el icono de borrar el lar en la exportaciÃ³n si no tienes permiso para borrar cursos, ya que es el que se controla en back cuando le das.
- #154985 En la ediciÃ³n de recurso interno se pasan los parÃ¡metros de descripciÃ³n y tÃ­tulo por post. Queda pendiente que en la bÃºsqueda de recursos al paginar se pierden.
- #152552 Establecido el role a los formularios en todo el componente para accesibilidad.
- #153797 Se mueve la clase ExportUtil al service.
- #139242 Modificar la url para que desde la lista de actividades salga el preview de la actividad test.
- #154058 Cambio para que cuando no hay actividades obligatorias en un mÃ³dulo se recÃ¡lcule con el 100%
- #151340 RefactorizaciÃ³n de todo el portlet de estadÃ­sticas generales
- #137425 Modificaciones buscador facetas para el buscador de cursos
- #102776 No se puede borrar un curso si hay estudiantes que lo han terminado
- #154466 Se modifica para que siempre se llame a los diplomas externos, no sÃ³lo con el CourseResult pasado
- #153457 Cambios para maquetaciÃ³n: aÃ±adida clase genÃ©rica a portlet de inscripciÃ³n y  Span para separar fechas
- #144540 Idioma en la creaciÃ³n de cursos, cambio para que coja por defecto el del usuario.

### Fixed

- #140958 Corregidos problemas en auditorÃ­a cuando no encuentra un componente en el Registro de las clases de log
            Descomentada lÃ­nea de auditorÃ­a que se comentÃ³ por error. No se estaba auditando en el lms_auditentry
- #158119 Cambio para resolver la incidencia con la valoraciÃ³n de P2P que no actualiza bien el popup
- #157814 Si antes se habÃ­a cerrado un curso y volvias a reabrir, al terminar volvÃ­a a hacer la acciÃ³n anterior de cerrar por el parÃ¡metro redirect que se ha eliminado.
- #157886 Enlace a ediciÃ³n de curso corregido desde el curso padre
- #158463 No se guardan los expandos de los mÃ³dulos
- #157958 CorrecciÃ³n al exportar estadÃ­sticas generales
- #157508 CorrecciÃ³n para visualizar los videos de vimeo sin controles.
- #157748 CorrecciÃ³n sobre las tests con contraseÃ±a
- #157169 La columna de actividad precedente salÃ­a vacÃ­a en las estadÃ­sticas del curso.
- #157581 Desde la ficha del curso no se estaba pasando el courseId al include de los diplomas, por lo que no le estaba llegando y no aparecÃ­an seleccionados.
- #147490 Solucionado el problema al corregir las preguntas de rellenar huecos y arrastrar
- #157185 ModificaciÃ³n del javascript de buscar recurso en actividad de recurso interno para que funcione en ie
- #146325 Cambiado el componente de navegaciÃ³n por mÃ³dulos para que se comporte correctamente.
- #157265 El nÃºmero mÃ¡ximo de usuarios en un curso sÃ³lo debe contar a los estudiantes
- #155029 Corregido error al importar un lar de un curso sin borrar lo anterior, resuelta la excepciÃ³n DuplicateFileException
- #153797 CorrecciÃ³n de la incidencia cuando los archivos que se exportan llevaban un punto al final.
- #143338 CorrecciÃ³n de preview test y asset de mÃ³dulo al crear curso.
- #151635 Actividad de tipo test, correcciÃ³n al exportar respuestas de usuarios a CSV
