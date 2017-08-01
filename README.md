# proyecto-mitocode
Proyecto Final dle Curso de JEE de mitocode Network. Incluye JPA, JSF2, EJB, CDI.

Requerimientos:

IDE: Eclipse. Motor de BD: MySql y Postgres. Servidor de Aplicaciones: Widlfly10.0

Paso 0:

Agregar el servidor de aplicaciones Wildfly10.0.

Paso 1:

Crear un nuevo proyecto de tipo Maven Project. Crear un proyecto de Maven "simple project"

En Group Id, colocar el nombre de la empresa. En Artidact Id, colocar el nombre dle proyecto. El tipo de empaquetado es WAR.

Paso 2:

Colocar las dependencias en el archivo pom.xml, se recomienda reutilizar del Gist o Pastebin. Dar Alt + F5 para actualizar las depedencias de Maven.

Paso 3:

Ir a propiedades del proyecto, o dar ALT + Enter. Escribir la palabra facets y revisar en el panel izquierdo que Dynamic Web Module este chekado en la version 3.1. En el ultimo panel, el del extremo izquiedo, ir a la pestania de Runtimes y chekar a wildfly10 Runtime. Darle solo a APLY, no a Aply and Close. Regresar al buscador y borrar la busqueda y digitar Java Server Faces, ahi tenemos que chekar que el combo Type, este en la opcion "Library provided by target runtime".

Nota: Como nos dimos cuenta en el archivo pom.xml, el jsf-api como el jsf-impl tienen el atributo de provided, esto quiere decir que el servidor de aplicaciones wildfly es el que provee estas apis. Y es por eso tambien que en las "proiedades del proyecto" hemos configurado para que el JSF reciba del Wildfly estas especificaciones.
