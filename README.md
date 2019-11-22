#liferaylms

Núcleo de LMS para liferay. Permite la creación y realización de cursos con actividades y módulos. 


## Parametrización

lms.competences.pages=A4,A4 landscape

#1 Open, 2 restricted, 3 private
#lms.site.types=3,2,1
#module.show.icon=true/false
#activity.show.categorization=true/false
#lms.learningactivity.maxfile=4

learningactivity.show.califications=true

#lms.learningactivity.scormasset=com.liferay.lms.learningactivity.scormcontent.ScormContentAR
learningactivity.resourceExternal.complementaryFile=true
login.events.post=com.liferay.lms.actions.PostLoginAction
lms.p2p.numcustomquestion=5
lms.learningactivity.testoption.editformat=true
lms.question.formattype.normal=0
lms.question.formattype.horizontal=1
lms.question.formattype.combo=2
lms.module.courtesytime.miliseconds=5000

## Instalación

Dejar el war en deploy
Dejar el jar en lib/ext

## Extensión de funcionalidad

Permite la extensión de:

- Actividades
- Métodos de evaluación
- Sistemas de calificación
- Métodos de inscripción
- Preguntas de test
- Diplomas

### Acciones en la administración de cursos

Se ha establecido una interfaz para extender las acciones del curso en la administración de cursos con la interfaz AdminActionType.
Para implementar una nueva acción hay que añadir lo siguiente:

1. Añadir la siguiente línea en el portal.properties de tu portlet:

> lms.admin.action.type.portlet_WAR_nombreportlet.typeId=com.ted.xxx.ClaseImplementa
> portlet.add.default.resource.check.whitelist=portlet_WAR_nombreportlet

2. Implementar la interfaz AdminActionType.
3. En el liferay-portlet de tu portlet, tienes que añadir esta propiedad al portlet que va a ser embebido:

> <use-default-template>false</use-default-template>

4. Añadir en el portal-ext.properties las siguientes propiedades:

> portlet.add.default.resource.check.enabled=true

5. Añadir en el archivo [admin-course-action.md](admin-course-action.md) el identificador

## Compilación

* [JAVA](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html) - Versión 7
* [LIFERAY](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/6.1.1%20GA2/) - Versión 6.1.1 GA2

## Contribución

Por favor, leer [CONTRIBUTING.md](https://code.telefonicaed.com/wecorp/templates-md/blob/master/CONTRIBUTING.md) para conocer en detalle el código de conducto, y los procesos a seguir para realizar los merge request.

## Versionado

Para las versiones disponibles, ver el [repositorio de tags](https://github.com/TelefonicaED/liferaylms-portlet/releases). 

## Licencia

Este proyecto tiene licencia Propietary - ver el archivo [LICENSE.md](LICENSE.md) para más detalle.
