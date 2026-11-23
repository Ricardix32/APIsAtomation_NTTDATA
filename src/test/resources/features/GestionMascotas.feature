@PetStore
Feature: Gestion de mascotas en Swagger PetStore
  Como administrador de la tienda de mascotas
  Quiero registrar, actualizar y eliminar mascotas mediante la API REST
  Para mantener el catalogo actualizado

  @CP-01_CrearMascota @Positivo
  Scenario: Registrar una nueva mascota exitosamente
    Given el actor prepara los datos de una mascota llamada "Firulais" con estado "available"
    When el actor registra la mascota en PetStore
    Then el actor verifica que el codigo de respuesta sea 200
    And el actor valida que el campo "name" de la respuesta sea "Firulais"
    And el actor valida que el campo "status" de la respuesta sea "available"

  @CP-02_ActualizarMascota @Positivo
  Scenario: Actualizar nombre y estado de una mascota existente
    Given el actor tiene una mascota registrada llamada "Rocky" con estado "available"
    When el actor actualiza el nombre de la mascota a "Rocky Jr" y su estado a "sold"
    Then el actor verifica que el codigo de respuesta sea 200
    And el actor valida que el campo "name" de la respuesta sea "Rocky Jr"
    And el actor valida que el campo "status" de la respuesta sea "sold"

  @CP-03_EliminarMascota @Negativo
  Scenario: Consultar una mascota eliminada retorna error 404
    Given el actor tiene una mascota registrada llamada "Toby" con estado "pending"
    And el actor elimina la mascota del catalogo
    When el actor consulta la mascota por su identificador
    Then el actor verifica que el codigo de respuesta sea 404
    And el actor valida que el campo "message" de la respuesta sea "Pet not found"