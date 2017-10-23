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

Este es un tip para cuando nosotros queremos limpiar los controles de nuestra pagina xhtml.
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


Paso 7:

Uso de Templates, se recomienda que se cree una carpeta "templates" dentro de "webapp/WEB-INF".
Dentro de la carpeta "templates" se creara un archivo con el asistente, damos "new HTML" y eleginos "New Facelet Template",
y usando este asistente nos generar un scaffolding de nuestro template donde tendresmo que modificar sobretodo la linea 4.

	<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:p="http://primefaces.org/ui">
      
El namespace "ui" esta usando una version anterior de facelets, debemos cambiarlo por:

	<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:p="http://primefaces.org/ui">

Como nos damos cuenta usamos el de "sun".

De la plantilla generada, debemos darnos cuenta que al header se le anteponga el namespace "h:header",
si queremos introducir codigo que siempre va a ser estatico, podemos usar el componente p:cache, esto hace que
el contenido este en memoria cache, asi:

	 <ui:insert name="footer">
	    	<p:cache>
	    		<div>Desarrollado en Cibertec</div>
	    		<div>Derechos reservados - 2017</div>
	    		<div>
	    			<a href="#">Facebook</a>
	    			<a href="#">Youtube</a>
	    			<a href="#">Twitter</a>
	    		</div>
	    	</p:cache>
	  </ui:insert>



Ojo: Si hacemos un cambio en este footer no se va a reflejar pues esta en cache, tendriamos que limpiarlo para
que recien limpie la cache.


Para "consumir" el template debemos rodear el "body" de nuestra pagina co la linea de abajo:

	<ui:composition  template="/WEB-INF/templates/plantilla.xhtml"></ui:composition>
	

Para sobreescribir los "ui:insert" lo hacemos de la siguiente manera:

	<ui:define name="content"></ui:content>

Con la lineas de arriba por ejemplo podemos rodear todo el formulario "form" y este seria el "content" que queremos
sobreescribir.


Se recomienda que la plantilla tenga los archivos css y js, para que las paginas clientes solo llamen a la plantilla:

	<h:head>
		<title><ui:insert name="title">Gestor de Contratos</ui:insert></title>
		<h:outputStylesheet name="estilos.css" library="css" />
		<h:outputScript name="calendar.js" library="js" />
	</h:head>

O en todo caso en cada cliente, los archivos js deberiamos ponerlos al final de la pagina y obviar lo recomendado arriba que sea
en el template.

Para cambiar el ancho de nuestra plantilla, agregamos estilos a nuestro archivo "estilos.css"

	.resolucion {
	width: 800px;
	margin: 0 auto;
	}

Y le agregamos esa clase al "body" de la plantila <body class="resolucion">

OJO: Con Ctrl + F5 limpias la cache. Debemos hacer esto para que actualice los valores del CSS.

Paso 8) Agregando iconos al menu de la plantilla

	<ui:insert name="header">
    
    	<h:form id="frmMenu">
    		<p:menubar autoDisplay="false">
    			<p:menuitem icon="ui-icon-home" value="Inicio" action="index?faces-redirect=true"/>
    			<p:menuitem icon="ui-icon-person" value="Puestos" action="puesto?faces-redirect=true"/>
    			<p:menuitem icon="ui-icon-document" value="Contrato" action="contrato?faces-redirect=true"/>
    		</p:menubar>
    	</h:form>

	</ui:insert>

Este es el enlace con todos los iconos de primefaces:

Link: http://www.petefreitag.com/cheatsheets/jqueryui-icons/

Se puede agregar una libreria adicional para soportar iconos de Font Awesome "fa-x".












Snippets de todo el proyecto

1)En el index.xhtml se ha trabajado en la persistencia de datos con Persona y Telefono, como sabemos una person puede tener muchos
telefonos. Lo que hicimos es lo siguiente:
	
	Persona.java
	
	@OneToMany(mappedBy = "persona", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Telefono> telefonos;
	
	Telefono.java
	
	@ManyToOne
	@JoinColumn(name = "idPersona", nullable = false) // Indicamos el campo en la otra tabla que permite la relacion
	private Persona persona;
	
Es decir lo trabajamos con fetch.EAGER(ansioso), en castellano esto quiere decir que cuando traigamos la data de Persona, esta data
vendra incluida con el listado de Telefonos para cada Persona.

2)Por otro lado en puesto.xhtml hemos usado la relacion de Puesto y Funcion, como sabemos un Puesto puede tener muchas funciones, 
pero para el proyecto lo hemos trabajado de forma "LAZY".

	Puesto.java
	
	@OneToMany(mappedBy = "puesto", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
				CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
		private List<Funcion> funciones;

	Funcion.java

	@ManyToOne
	@JoinColumn(name = "idPuesto", nullable = false)
	private Puesto puesto;

Entonces para el caso de si yo quiero traer el listado de Funciones lo tengo que hacer de forma manual, teniendo como parametro
el puesto del cual quiero obtener las funciones:

public List<Funcion> listar(Puesto puesto) throws Exception {
		
	Funcion.java
		
	@Override
	public List<Funcion> listar(Puesto puesto) throws Exception {
		
		List<Funcion> funciones = null;
		Query query = manager.createQuery("FROM Funcion f where f.puesto.idPuesto = ?1");

		query.setParameter(1, puesto.getIdPuesto());
		funciones = (List<Funcion>)query.getResultList();

		return funciones;
	}


3) Si queremos ver como trabajamos con "gifs" que nos avisen cuando haya un procesamiento ajax y no ajax, eso lo podemos
apreciar dentro de "config.xhtml" y su respectivo ManagedBean.

4) Obs: Cuando le damos clicl para actualizar una fila de la tabla y luego la cerramos con la "x" en la parte superior derecha,
lo que pasa es que en memoria queda la data de la persona. Luego cuando vamos a dar "Nuevo registro", en el metodo
"limpiarControles()" debemos de limpiar todos los atributos de la "persona" o de cualquier entidad, pero sobretodo limpiar el
atributo de llave primaria que es en el meotodo "operar" que se evalua esa variable para registra o actualizar un registro.

5)Tenemos nuestra entidad Contrato, el cual sus atributos son Persona, Puesto y otros datos de tipo primitivo.

Puesto y Persona se eligiran de la vista mediante:

	<p:selectOneMenu>
		<f:selectItem itemLabel="--Seleccione--" itemValue="#{null}" noSelectionOption="true"/>
		<f:selectItems value="#{contratoBean.lstPuestos}" var="pue" itemLabel="#{pue.nombre}" itemValue="#{pue.idPuesto}"/>
	</p:selectOneMenu>

, si lo hacemos de la forma normal, nosotros estariamos
capturando solo el idPersona y idPuesto y lo que se busca es capturar todo el objeto para poder setearlo a Contrato de la siguiente forma:



	public void registrar() {
		try {
			contrato.setIdContrato(contratoService.generarId(persona));
			contrato.setPersona(persona);
			contrato.setPuesto(puesto);
			contratoService.registrar(contrato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.listarContratos();
	}

Entonces para que se pueda realizar ese escenario lo que podemos hacer es usar "omnifaces".
Debemos chekar que en nuestro pom tengamos la dependencia de "omnifaces".

El codigo final en la vista quedaria asi:

	<p:outputLabel value="Puesto"/>
	<p:selectOneMenu value="#{contratoBean.puesto}" converter="omnifaces.SelectItemsConverter">
		<f:selectItem itemLabel="--Selecccione--" itemValue="#{null}" noSelectionOption="true" />
		<f:selectItems value="#{contratoBean.lstPuestos}" var="pue" itemLabel="#{pue.nombre}" itemValue="#{pue}" />
	</p:selectOneMenu>

Como vemos en el itemValue del "f:selectItem" le pasamos todo el objeto y ya no "obj.getId()".

Si no hiciera eso en el managedBean tendria que recibir los "ids" y luego buscar la entidad a la que pertenece para luego
setearlo al momento de registrar un Contrato.

6) Cuando no encargamos que JPA genere la llave primaria de mi entidad, en este caso puntual es de la entidad
Contrato que tiene su propia clase Embedable ContratoPK con llaveCompuesta de 3 llaves, lo cual se procede de la siguiente manera,
debemos tener un mecanismo para generar mi llave primaria. Las lineas de debajo me trae el maximo "idContrato" de la persona a la
que pertenece el contrato en la lista de contratos anteriores.

	@SuppressWarnings("unchecked")
	@Override
	public synchronized int generarId(Persona persona) {
		int id = 0;
		List<Contrato> lista = new ArrayList<>();
		Query query = manager.createQuery("FROM Contrato c where c.persona.idPersona = ?1");
		query.setParameter(1, persona.getIdPersona());

		lista = (List<Contrato>) query.getResultList();

		if (lista != null && !lista.isEmpty()) {
			id = lista.get(0).getIdContrato() + 1;
		} else {
			id = 1;
		}

		return id;
	}

Como vemos con el codigo de arriba obtenemos el ultimo codigo del contrato y le aumentamos en uno, pero debemos estar atento
a la concurrencia ya que puede estar registrando un contrato a la vez y esto podría generar un error, es por es que le
colocamos al metodo la palabra reservada "synchronized".


7) En el requerimiento del Proyecto se dice que cuando digamos al trabajador "Jose" se le genera un nuevo contrato, 
el resto de sus contratos anteriores quedan con un estado de "0" y el actual si es "1", por default es "1" segun la definicion
que hicimos en la misma Entidad "Contrato".

	public void registrar(Contrato contrato) throws Exception {
		
		//1 es activo, 0 es Inactivo, se setea 1 por defaul en la Entidad Contrato.
		
		manager.persist(contrato);
		
		/*
		 * Seteamos el estado a "0" de los contratos de la Persona que se este registrando,
		 * excepto el contrato que se esta registrando en ese momento.
		 *
		 * */
		Query query = manager.createQuery(
				"UPDATE Contrato c SET c.estado = '0' WHERE c.persona.idPersona = ?1 AND c.idContrato <> ?2");
		query.setParameter(1, contrato.getPersona().getIdPersona());
		query.setParameter(2, contrato.getIdContrato());
		query.executeUpdate();
	}

8) A diferencia de la Entidad Persona que la fechaNac la trabajamos con la api LocalDate de java8, en la entidad Contrato
hemos trabajado los campos fecha con "Date". Entonces la manera de trabajarlo en JPA sería:

	Contrato.java

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicio", nullable = false)
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaFin", nullable = false)
	private Date fechaFin; 

	contrato.xhtml
	
	<p:outputLabel value="Fecha de Inicio" />
	<p:calendar value="#{contratoBean.contrato.fechaInicio}" />

	<p:outputLabel value="Fecha de Fin" />
	<p:calendar value="#{contratoBean.contrato.fechaFin}" />
	
	ContratoBean.java
	
	public void registrar() {

		try {
			contrato.setIdContrato(contratoService.generarId(persona));
			contrato.setPersona(persona);
			contrato.setPuesto(puesto);
			contratoService.registrar(contrato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.listarContratos();
	}

Como podemos apreciar en el ManagedBean no se hace ningun paso extra, ya que el "p:calendar" reconoce los tipos "Date".

9) Trabajar con Converters:

Se recomienda un "converter" para cada cosa en particular.

Por ejemplo en la Tabla contrato tenemos contratos que estan activos e Inactivos, y queremos mostrar en el datatable 
no su numero de estado(0 y 1), sino una palabra relacionada a su descripcion. Para hacer eso se genera un converter.
Otra salida es usar "Transient" de JPA como ya vimos con "nombreCompleto" de la entidad Persona.

	EstadoConverter.java
	
	package pe.cibertec.converter;
	
	import javax.faces.component.UIComponent;
	import javax.faces.context.FacesContext;
	import javax.faces.convert.Converter;
	import javax.faces.convert.FacesConverter;
	
	@FacesConverter("estadoConverter")
	public class EstadoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		String tipo = "";
		
		if(value != null) {
			tipo = String.valueOf(value);
			switch (tipo) {
			case "1":
				tipo = "Activo";
				break;
			case "0":
				tipo = "INACTIVO";
				break;
			default:
				tipo = "NO VALIDO";
				break;
			}
		}
		return tipo;
	}

	}

Como vemos en el codigo tipo = String.valueOf(value) estamos casteando para poder analizar el tipo "0" o "1".
	
La manera mas simple de implementar un Converter es como esta en la parte superior.
Del codigo arriba tenemos los metodos sobreescritos "getAsObject()" que es el metodo a evaluar y el metodo "getAsString()" es el elemento
vamos a devolver al usuario. 
	
	contrato.xhtml
	
	<p:column headerText="Estado">
		<p:outputLabel value="#{con.estado}">
			<f:converter converterId="estadoConverter"/>
		</p:outputLabel>
	</p:column>
	
10) Si queremos mostrar en nuestro datatable un campo que es de tipo Date con una mascara personalizada usaremos:



	<p:column headerText="Fecha de Inicio">
		<p:outputLabel value="#{con.fechaInicio}">
			<f:convertDateTime pattern="dd/MM/yyyy" />
		</p:outputLabel>
	</p:column>

11) Si queremos asignar una imagen segun una cadena, esto lo aplicamos en el datatable de contratos, donde tenemos
la opcion de '1' o '0' y segun eso podemos evaluar y mostrar con "rendered" que se visualice un graphicImage:

	<p:column headerText="Estado">
		<p:outputLabel value="#{con.estado}">
			<f:converter converterId="estadoConverter"/>
			<p:graphicImage library="images" name="check.jpg" rendered="#{con.estado eq '1'}"/>
			<p:graphicImage library="images" name="close.png" rendered="#{con.estado eq '0'}"/>
		</p:outputLabel>
	</p:column>


Si queremos que se aprecie solo el graphicimage y no la palabra "Activo" o lo contrario solo limpiamos.

	
	<p:column headerText="Estado">
		<p:outputLabel>
			<p:graphicImage library="images" name="check.jpg" rendered="#{con.estado eq '1'}"/>
			<p:graphicImage library="images" name="close.png" rendered="#{con.estado eq '0'}"/>
		</p:outputLabel>
	</p:column>


12) Si queremos trabajar con mas temas de primefaces debemos tener esta dependencia en nuestro pom.xml

Puedes elegir de esta referncia, los community: https://www.primefaces.org/themes/#tab-community

	<dependency>
		<groupId>org.primefaces.themes</groupId>
		<artifactId>all-themes</artifactId>
		<version>1.0.10</version>
	</dependency>
	
Y en la seccion de repositories, agregar el siguiente repository:

	<repositories>
		<repository>
			<id>prime-repo</id>
			<name>PrimeFaces Maven Repository</name>
			<url>http://repository.primefaces.org</url>
			<layout>default</layout>
		</repository>
	</repositories>

Ademas de ello debemnos agregar en nuestro archivo "web.xml", lo siguiente:

	<context-param>
	  	<param-name>primefaces.THEME</param-name>
	  	<param-value>bootstrap</param-value>
  	</context-param>
  	
Como hemos traido todos los temas, tenemos un listado de varios temas, para el ejemplo hemos agregado el "bootstrap".

Si solo hubieramos traido un tema en particular, sería mas limitado el numero de temas que podemos digitar en "<param-value>"


13) Siguiendo con el tema de la plantilla, ahora pondremos un "menu" que se compartira en toda la aplicacion:

	<ui:insert name="header">
    
	    	<h:form id="frmMenu">
	    		<p:menubar autoDisplay="false">
	    			<p:menuitem value="Inicio" action="index?faces-redirect=true"/>
	    			<p:menuitem value="Puestos" action="puesto?faces-redirect=true"/>
	    			<p:menuitem value="Contrato" action="contrato?faces-redirect=true"/>
	    		</p:menubar>
	    	</h:form>

	</ui:insert>
	
Lo ponemos dentro de un "h:form" pues vamos a hacer peticiones de tipo Get.
Otro detalle es este formato:

	action="index?faces-redirect=true"

Cuando le indicamos lo que esta despues del signo de interrogacion es para que se muestre la url cuando lleguemos a ese page.

14) El tema de "bundles" lo manejamos de esta manera, lo que buscamos es que el mensaje del "datatable" cuando no hay registros
se carguen de un archivo porperties. Se procede de la siguiente manera:

Lo que se busca hacer es que de un archivo properties con mensajes personalizados, este archivo sera accedido desde una pagina xhtml. Para eso crearemos estos archivos.

* 1:

Dentro de "META-INF" crear un archivo "mensaje-properties" con el siguiente contenido:

     mensaje_vacio = No hay datos que mostrar

* 2:

Dentro de "WEB-INF" crearemos el archivo "faces-config.xml" con el siguiente contenido:

	<?xml version="1.0"?>
	<faces-config version="2.2" xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd">
	
	<application>
	<resource-bundle>
	<base-name>mensajes</base-name>
	<var>msg</var>
	</resource-bundle>
	</application>
	
	</faces-config>

Darse cuenta que "base-name" debe coincidir con el nombre de nuestro archivo properties sin su extension "mensajes".

* 3:

Vamos a colocar la referencia a la plantilla para que acceda a todas las paginas hijas.

	<ui:insert name="header">

     	<f:loadBundle basename="mensajes" var="msg"/>
      .
      .

	</ui:insert>

* 4:

Y en la vista, en este caso un datatable

	<p:dataTable value="#{personaBean.listaPersonas}" var="persona"
	emptyMessage="#{msg.mensaje_vacio}"
	widgetVar="personasTable" rows="10" paginator="true"
	currentPageReportTemplate="Mostrando {startRecord}-{endRecord} de {totalRecords} registros"
	paginatorTemplate="{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} ">

Como vemos arriba podemos llamar de frente a esa variable pero desde un archivo properties.









