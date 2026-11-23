#Estudiante: Zee Ricardo Montenegro Baca

# Automatización de Pruebas API REST - Swagger PetStore

Proyecto integrador de automatización de servicios REST desarrollado con **Serenity BDD**, **Cucumber** y el patrón **Screenplay** sobre la API pública de [Swagger PetStore](https://petstore.swagger.io/).

---

## Stack Tecnológico

* **Lenguaje:** Java 21
* **Gestor de dependencias y ciclo de vida:** Apache Maven
* **Framework BDD & Reportes:** Serenity BDD `4.2.1` + Serenity Cucumber
* **Cliente HTTP:** Serenity Screenplay REST / Rest Assured
* **Ejecutor de pruebas:** JUnit 4 + Maven Failsafe Plugin

---

## Arquitectura del Proyecto (Patrón Screenplay)

```text
src
├── main
│   └── java/org/example
│       ├── apis/
│       │   └── PetStoreEndpoints.java      # Constantes centralizadas de rutas REST
│       ├── models/
│       │   ├── Order.java                  # POJO de orden de compra (/store/order)
│       │   └── Pet.java                    # POJO de mascota (/pet)
│       ├── questions/
│       │   ├── PetField.java               # Validación dinámica de campos JSON
│       │   └── ResponseCode.java           # Validación del código de estado HTTP
│       └── tasks/
│           ├── DeleteOrder.java            # DELETE /store/order/{orderId}
│           ├── DeletePet.java              # DELETE /pet/{petId}
│           ├── GetOrder.java               # GET /store/order/{orderId}
│           ├── GetPet.java                 # GET /pet/{petId}
│           ├── PostOrder.java              # POST /store/order
│           ├── PostPet.java                # POST /pet
│           └── PutPet.java                 # PUT /pet
└── test
    ├── java/org/example
    │   ├── RunnerTest.java                 # Runner principal de CucumberWithSerenity
    │   └── stepDefinitions/
    │       ├── ParameterDefinition.java    # Configuración de OnlineCast e inyección de Actor
    │       ├── PetStepDefinition.java      # Definición de pasos y hook @After para mascotas
    │       └── StoreOrderStepDefinition.java # Definición de pasos y hook @After para órdenes
    └── resources
        ├── features/
        │   ├── GestionMascotas.feature     # Escenarios CRUD positivos y negativos de /pet
        │   └── GestionOrdenes.feature      # Escenarios positivos y negativos de /store/order
        └── serenity.conf                   # Configuración HOCON (petstore.baseurl)
