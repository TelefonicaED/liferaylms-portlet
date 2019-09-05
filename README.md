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

##Extensión de funcionalidad

Permite la extensión de:

- Actividades
- Métodos de evaluación
- Sistemas de calificación
- Métodos de inscripción
- Preguntas de test
- Acciones en la administración de cursos
- Diplomas

## Compilación

* [JAVA](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html) - Versión 7
* [LIFERAY](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/6.1.1%20GA2/) - Versión 6.1.1 GA2

## Contribución

Por favor, leer [CONTRIBUTING.md](https://code.telefonicaed.com/wecorp/templates-md/blob/master/CONTRIBUTING.md) para conocer en detalle el código de conducto, y los procesos a seguir para realizar los merge request.

## Versionado

Para las versiones disponibles, ver el [repositorio de tags](https://github.com/TelefonicaED/liferaylms-portlet/releases). 

## Licencia

Este proyecto tiene licencia Propietary - ver el archivo [LICENSE.md](LICENSE.md) para más detalle.
