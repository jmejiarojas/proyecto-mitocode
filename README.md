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

Dato: Cuando anotamos que nos vamos a apoyar en "javaee-api" en en pom.xml, le estamos diciendo que nos vamos a apoyar en la especificacion de JPA y la implementacion es de HIBERNATE, todo esto nos lo provee "wildfly" por la palabra reservada "provided".

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
 Dejamos el campo "Security Domain" vacio.
*Damos Next y observamos todo el resumen de lo que hemos configurado.
*Damos click a Finish y un mensaje verde modal debe confirmarlo.
*Luego de esto vemos en la columna de DataSources, nuestro DataSource creado, lo seleccionamos
 y elegimos su opcion "Test connection", si todo esta ok, debe mostrarnos un mensaje de "success".

Paso 2:

Configuramos nuestro archivo persistence.xml para hacer uso de JTA.
Como nos damos cuenta, ahora no va a ser necesario referenciar nuestros beans en dicho archivo.
Al hacer uso del "TransaccionType = JTA", no va a ser necesario usar el metodo de los Management "beginTransaction",
ni tampoco cerrarlos("close").
En "jta-data-source" nosotros tenemos que referenciar al DataSource que hemos creado en la configuaracion con el 
Wildfly.

Otra etiqueta importante aca es la property:
"<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>". Esta
propiedad lo que hace es borrar el contenido de la BD y volver a crear todo de nuevo.
Cuando arrancamos la aplicacion, si hay algo lo va a destruir y la vuelve a crear, sino hay nada lo crea.

Paso 3:

Ahora nos dirigimos a la clase PersonaDAOImpl, donde como estamos en un contexto de EJB, tenemos que usar sus
propias anotaciones, en este caso la anotacion "@Stateless" que en castellano es "sin estado", nos permite
reemplazar al "@Named" y al "@RequestScoped", entonces cuando colocamos la anotacion "Stateless", estamos diciendo
que nuestro "bean" va a ser inyectable(Named) y ademas solo es un puente recibe y enviar datos(RequestScoped).

Paso 4:

Podemos apreciar una nueva anotacion "@PersistenceContext" que me permite crear un EntityManager sin el uso
de una Factoria y con solo referenciar a la unidad de persistencia o si es una sola se referencia por default.
Como ya he mencionado anteriormente, el uso de JTA permite que todo se maneje de manera transacional, evitando
abrir y cerrar conecciones del EntityManager, ni tampoco "commitearlas" ni "rollbackearlas".

El siguiente paso es ir a la interface que provee los metodos a nuestro DAOImpl, me refiero al IPersonaDAO,
cuando trabajamos en un contexto de EJB, es necesario "anotar" a nuestra interfaz, hay dos opciones,anotarla
con "@Local" o con "@Remote". Por regla general casi siempre se trabaja con "@Local".
Las "Local" es que va a ser ejecutadas en la misma JVM que esta en el mismo servidor, es el mas comun.
El "Remote" es cuando nuestros EJB van a ser ejecutados en otra JVM de otro servidor.

Ahora nos dirigimos a quien es el que va a usar nuestra implmentacion, en este caso es nuestro "ServiceImpl",
y en vez de usar la anotacion "Inject" de CDI, vamos a usar la anotacion "EJB" pues estamos en un contexto de EJB.

Paso 5:

Para seguir usando CDI, vamos a tener que hacer nuestras Entidades que sean injectables, pero no podemos poner
"@Named" con su ambito "@RequestScoped" a nuestras Entidades y hacer eso rompe con el concepto de las entidades,
ya que las entidades no tiene estado pues son simplemente clases, para poder configurar esto vamos a tener que cambiar en nuestro archivo "beans.xml". 

Podemos observar que inicialmente configuramos el "discovery-mode" como "anotado", lo que vamos a hacer ahora es
que el "discovery-mode" va a tener el valor de "all".

Al colocar "all" se podria decir que le estamos diciendo al archivo "beans.xml" es que se van a inyectar los beans
sea como sea la forma que esten inyectandose. Esto quiere decir que se va a poder inyectar a los beans asi no tengan
la anotacion de los "Scope".

Una vez hecho esto, en nuestro controlador "PersonaBean" ya podemos colocar la anotacion "@Inject" sobre nuestra
entidad y ya no usar la palabra "new".

********************************************************************************************************************
********************************************************************************************************************

Proyecto de Gestion de Contratos

El proyecto final del curso consta de la realizacion de un sistema que va a gestionar el correo de las personas.
Tiene como base todo lo anterior ya desarrollado.
Ya tenemos configurado el "beans.xml", asi como tambien el persistence.xml.

Paso 1:

Para este primer paso, ya tenemos que contar con nuestro diagrama de clases en UML para ver las relaciones
de nuestras futuras tablas.

Mapeamos nuestras tablas a objetos. Cuando se ejecute el proyecto, y como el persistence.xml esta configurado
en "drop-and-create", cada vez que levantemos la aplicacion se va a crear la estrucutra de nuestra BD.

Las Entidades son todas intuitivas cuando es una relaciond de "Uno a muchos", como de "Uno a uno", pero cuando
tenemos una tabla con llave primaria compuesta, ahi es donde se hace un artificio y se crea una entidad Extra, 
que luego es referenciada a la Entidad con llave compuesta. En este caso la Entidad "Contrato" es la que tiene
la llave primaria compuesta y la entidad que se crea para que gestione sus llaves primaria es "ContratoPK".

Paso 2:

En la clase "ContratoPK", debemos crear los metodos "equals" and "hashCode" porque tiene atributos que son objetos
y para poder diferenciarlos debemos hacer ello.

Como vemos nuestra llave compuesta en ContratoPK, hace referencia a la Entidad Persona y Puesto, y ahora debemos
crear por ende sus metodos equals and hashCode respectivamente; hay un detalle importante, esta Entidad permite
diferenciarse del resto por su id, es por eso que en ambas entidades(Persona y Puesto) nosotros vamos a crear
los metodos equals and hashCode haciendo referencia solo al atributo "id" de cada entidad.

Para referenciar a la entidad "Contrato" su clase que es llave foranea "ContratoPK" se le tiene que agregar la
anotacion "@IdClass(ContratoPK.class)"

Paso 3:

En este paso trabajaremos con la vista y con los detalles que esta conlleva.
El primer detalle que sale a la vista es de que el control de primefaces llamado "p:calendar" recibe como argumento
un "Date" y nosotros en la entidad persona lo estamos manejando como "LocalDate", es por eso que en el controller
vamos a hacer una pequenia conversion para pasar el valor capturado en la vista a un objeto LocalDate y de esta
manera sea "persistido" en la BD.

Como vamos a trabajar con imagenes, tenemos que poner en el "commandButton" en su propiedad "ajax" a false; ademas
en el formulario indicar a "enctype = multipart/form-data".

Para que el componente calendar de PrimeFaces tenga una traduccion a espaniol, nosotros tenemos que agregar un
archivo javascript en "webapp/resources/js/calendar.js". Luego de ellos referenciarlo en nustro archivo xhtml.

Nosotros vamos a querer registrar una persona con muchos telefonos, todo esto en el mismo formulario. La idea es que
cuando terminemos de registrar los telefonos en la lista pero en memoria, cuando el demos a la opcion Registrar, recien en ese momento se graba los telefonos en la tabla correspondiente. Para todos estos detalles, noas tenemos+
que dirigir a nuestra Entidad Persona y tenemos que agregarle un atributo "List<Persona>" con sus respectivas
anotaciones de Hibernate y JPA.

Paso 4:

Vamos a tener inconvenientes cuando vamos a querer traer los telefonos de la persona, por defecto la instancia de una persona no trae los telefonos porque por defecto la carga es "Lazy Load", asi que en el mapeo de la Entidad
Persona configuramos con el valor de "Fetch.EAGER" a fetch."

Paso 5:

Este es un tip para cuando nosotros queremos limpiar los controles de nestra pagina xhtml.
Lo primero que tenemos que hacer es crear un metodo "limpiarControles" en nuestro Bean que nos va a permitir iniciar los valores.
El segundo paso es agregarle el valor "limpiarControles()" al atributo "actionListener", luego de esto cambiamos
la forma de mostrar nuestro widgetDialog, ya no lo invocaremos con el atributo "onclick" sino con el atributo
"oncomplete".

Paso 6:

OJO: No se puede tener mas de un FetchType.EAGER en la aplicacion cuando trabajamos con JPA.
Lo que hace EAGER es cargarme todos los hijos en mejoria para luego cargarlos al padre. Todo esto  es un proceso
pesadaso.
Pero cuando usamos la aplicacion especicifica de Hibernate o de otros ORM's la historia es distinta.
Entonces cuando nosotros configuramos a FetchType.LAZY lo que decimos es que cargarmos solo los datos del padre pero
la lista hija no las va a cargar hasta que yo lo indique, y ya vemos si le decimos los primeros elementos, todos de
golpe, etc.