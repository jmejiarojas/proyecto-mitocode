# proyecto-mitocode
Proyecto Final del Curso de JEE de mitocode Network. Incluye JPA, JSF2, EJB, CDI.

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

Paso 4:

Como observamos nos sigue marcando error el IDE, es proque aun nos falta crear el archivo web.xml.
Nos dirigimos a "Deployment Descriptor" le damos click derecho y elegimos "Generate Deployment Descriptor".
Esto nos genera un archivo en "src/main/webapp/WEB-INF/web.xml".
Este archivo por defecto nos sale con la version "2.5", ir a Gist o pastebin y reemplazar el codigo del "3.1".


*******************************************************************************************************************
*******************************************************************************************************************
Empezamos con la logica del negocio:

Paso 0:

Como estamos en un contexto de CDI es importante que nuestras clases implementen de "Serializable".

Lo que es ambito, debemos tener claro que los daos, los services solo se encargan del flujo de la
informacion por lo que las clases seran anotadas con "@RequestScoped" del paquete javax.enterprise.context.

El ambito en el controller, por ejemplo "PersonaBean" debe ser "@ViewScoped" porque va a obedecer o interactuar
con paginas.

Paso 1:
 
Crear todo el scaffolding: daos, models, services, controllers(beans).

Todo lo anterior mencionado ya es muy conocido.

Paso 2: 

A los beans se les va a agregar la anotacion "@named" para CDI de javax.inject.Named.
Tambien al bean se le va a agregar la anotacion "@ViewScoped" pero de javax.faces.view.ViewScoped por el tema de CDI.

Paso 3:

Importante-> Vamos a crear una carpeta en la ruta "src/main/resources" con el nombre de "META-INF",
dentro de esa carpeta vamos a crear un archivo de definicion de beans "beans.xml", si tenemos el JBoss Tools
instalado el asistente nos ayuda con el boilerplate del archivo.

Literalmente en el archivo beans.xml, lo que hace es que va a soportar cualquier tipo de anotacion de inyeccion
de independencias(named, inject, resources), si no tenemos este archivo no van a ser reconocidos, y van a ser
descubiertos si es que estan "anotados".

OJO: No estamos usando SPRING, estamos usando el CDI de Java Standard.

Paso 4:

Iniciamos con las injecciones.
Importante-> Las injecciones van a ser anotadas en las implementaciones y no en las interfaces.
El CDI de java es el que se encarga de rutear por detras cuando son llamadas desde el controller(beans).

Nos dirigimos a la clase PersonaDAOImpl, y le colocamos la anotacion "@Named", con esto le decimos que esta
clase es "inyectable".

Para seguir el flujo, podemos observar en la clase PersonaServiceImpl que creamos una variable dao que es del
tipo IPersonaDAO, por lo que le colocamos la anotacion @Inject. De esta manera amarramos la implementacion
donde colocamos la anotacion "@Named"(PersonaDAOImpl) y cuando hacemos uso de una variable de tipo IPersonaDAO
dentro del ServiceDAOImpl le colocamos la anotacion "@Inject" a dicha variabe, y de esta manera se complementan.

De esta manera puedo utilizar el objeto "dao" dentro del PersonaBean, sin en ningun momento instanciarlo con
la palabra "new".

Paso 5:

Ya tenemos nuestra logica implementada en el PersonaServiceImpl, ahora el controller BeanPersona, es el que 
tendria que instanciar al ServiceImpl para poder usar sus metodos ya implementados. Pero usando CDI solo
tendriamos que indicar en la interface "IPersonaService" que sea injectable con la anotacion "@Named",
y dentro del controller BeanPersona, cuando creemos una variable del tipo IPersonaService tendriamos
que agregarle la anotacion "@Inject" y de esta manera volvemos a amarrar nuestra logica, sin tener que hacer
uso de la palabra reservada "new".

**A manera de nemotecnia, podemos saber como colocar las etiquetas "@Named" cuando tenemos la seguridad
que esa clase "Impl", va a ser llamada de una clase superior, por ejemplo, cuando la clase PersonaDAOImpl
va a ser llamada en la clase ServiceImpl, ahi tenemos la seguridad de colocarle la etiqueta "@Named" a nuestra
clase de implementacion PersonaDAOImpl. Al colocar la etiqueta "@Named" en otras palabras le indicamos que
la clase es "inyectable".

********************************************************************************************************************
********************************************************************************************************************

Trabajando con JPA

Paso 1:

Creamos en nuestra carpeta "src/main/resources/META-INF" nuestro archivo "persistence.xmnl".
Para escribir el contenido de nuestro archivo persistence.xml nos guiamos de Gist o de Pastebin.

Paso 2:

Empezamos con las anotaciones a nuestras entidades. Todas son del paquete "javax.persistence"

@Entity -> Para indicar que JPA lo va a manejar como una entidad.
@Id -> Es la llave primaria de la Entidad.
@Column -> Para personalizar el nombre que va a tener el campo en la tabla.

Paso 3:

En la clase PersonaDAOImpl, donde esta nuestra logica real, es ahi donde instanciaremos a nuestro EntitiFactory,
este a su vez nos permite crear el EntityManager que es con el que vamos a manejar las operaciones con la BD.

Se crea los metodos de listarPersonas dentro del PersonaDAOImpl, como punto importante a destacar es que la linea

"Query query = manager.createQuery("FROM Persona p");" , la palabra Persona esta en mayuscula y asi debe estar
porque no estamos haciendo referencia a nuestra tabla en la BD, sino estamos haciendo referencia a la entidad
"Persona" y se debe respetar la mayuscula.

Paso 4:

En el controlador PersonaBean, vemos como interactuamos con la capa de Servicio, hay un concepto nuevo que es
el "@PostConstructor" , y es ahi donde nosotros vamos a iniciar nuestras variables.

Si lo que queremos es mostrar un listado al cargarse la pagina, entonces tenemos que crear una variable lstPersonas
con su metodo getter y setter para que sea accesible desde mi JSF, y tenemos que cargar dicha variable lstPersona
en el constructor, para que al cargarse la pagina se vea nuestra data.

Paso 5:

Todo desde ahora ya se maneja en la vista que son los archivos ".xhtml", para eso es recomendable guiarse de la 
documentacion oficial de Primefaces.

Como tip, el "filterGlobal" esta amarrado al "widgetVar", ademas para que funcione el filtro global tiene que estar
habilitado el "filterBy".

Otro tip mas es poner el "filterMatchMode" en "contains" que es lo mas recomendable de cara al usuario final.


********************************************************************************************************************
********************************************************************************************************************

Trabajando con EJB:

En palabras sencillas, EJB es una especificacion que contiene a JPA por dentro, es mayr y mas grande que JPA.

EJB nos brinda Persistencia, Transacciones, Seguridad; cosas que JPA no nos brinda y tendriamos que apoyarnos
en librerias, digamos que EJB es una tecnologia "JPA con superpoderes".


Paso 1:

Vamos a configurar el persistence.xml para que soporte un pool de conexiones.

Se usa un pool de conexiones para poder aceptar solicitudes del servidor como si fuese un pulpo, cada conexion
que se le hace al servidor va a hacer administrada por cada tentaculo, obviamente esto es una analogia.

La configuracion basica(sin pool) es como si fuese una sola autopista por donde van a pasar las conexiones,
en este caso solo una autopista; en cambio, con el pool de conexiones es como si hubieras 10 autopistas.

La configuracion del servidor Wildfly para Postgres y MySql esta en el video 6 parte 1.

Nota: Para configurar con Postgres, se utiliza un asistente.
Para ello tenemos que digitar el link, chekamos que este levantado el servidor widlfly, "localhost:990.
Para esto tenemos que haber creado un usuario de wildfly, se adjunta documentacion en drive o en el material
relacionado del curso.

*Le damos a la opcion "ADD" donde buscamos nuestro conector de postgres en el disco duro "postgres9.4-jdbc.jar".
*Luego le ponemos un nombre familiar, el runtime es tal cual y le damos a Finish.

Luego tenemos que crear un DataSource:

*Estando en la pestania "Home", seleccionamos la opcion "Configuration/Start".
*Le damos a "SuSystem", luego elegimos "DataSources". En type, elegimos "Non-XA".
*Luego elegimos la opcion "Add" que esta en la columnda "DataSources".
*En la ventana que se desplego, elegimos para "Postgres DataSource".
*En la ventana, el campo Name, colocamos un nombre para el DataSource.
*OJO: En el campo JDNI, es el campo importante, y este nombre que va a ir en el persistence.xml.
*Damos Next, elegimos el postgres.jar que asociamos en el deployment(El primer paso).
*Damos Next, y nos vamos a la pestania "Detected driver" y elegimos el postgres.jar del inicio.
*Damos Next, y llenamos con las credenciales de nuestro postgres y la BD que vamos a usar.



