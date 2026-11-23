@Orders
Feature: Gestion de ordenes de compra en Swagger PetStore
  Como cliente de la tienda de mascotas
  Quiero generar y consultar ordenes de adopcion o compra mediante la API REST
  Para verificar el estado de mi pedido

  @CP-04_CrearYConsultarOrden @Positivo
  Scenario: Generar y consultar una orden de compra de mascota exitosamente
    Given el actor prepara una orden para 2 mascotas con estado "placed"
    When el actor registra la orden en la tienda
    And el actor consulta la orden por su identificador
    Then el actor verifica que el codigo de respuesta sea 200
    And el actor valida que el campo "status" de la respuesta sea "placed"
    And el actor valida que el campo "quantity" de la respuesta sea "2"

  @CP-05_ConsultarOrdenInexistente @Negativo
  Scenario: Consultar una orden inexistente retorna error 404
    When el actor consulta una orden con identificador inexistente
    Then el actor verifica que el codigo de respuesta sea 404
    And el actor valida que el campo "message" de la respuesta sea "Order not found"