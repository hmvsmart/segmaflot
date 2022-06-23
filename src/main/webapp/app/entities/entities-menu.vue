<template>
  <div>
    <b-dropdown-item to="/estado">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estado')">Estado</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ciudad">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ciudad')">Ciudad</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cp">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cp')">CP</span>
    </b-dropdown-item>
    <b-dropdown-item to="/colonia">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.colonia')">Colonia</span>
    </b-dropdown-item>
    <b-dropdown-item to="/direccion">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.direccion')">Direccion</span>
    </b-dropdown-item>
    <b-dropdown-item to="/sucursal">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.sucursal')">Sucursal</span>
    </b-dropdown-item>
    <b-dropdown-item to="/persona">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.persona')">Persona</span>
    </b-dropdown-item>
    <b-dropdown-item to="/persona-moral">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.personaMoral')">Persona Moral</span>
    </b-dropdown-item>
    <b-dropdown-item to="/persona-fisica">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.personaFisica')">Persona Fisica</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-contacto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.tipoContacto')">Tipo Contacto</span>
    </b-dropdown-item>
    <b-dropdown-item to="/contacto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.contacto')">Contacto</span>
    </b-dropdown-item>
    <b-dropdown-item to="/direccion-persona">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.direccionPersona')">Direccion Persona</span>
    </b-dropdown-item>
    <b-dropdown-item to="/empleado">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.empleado')">Empleado</span>
    </b-dropdown-item>
    <b-dropdown-item to="/horario">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.horario')">Horario</span>
    </b-dropdown-item>
    <b-dropdown-item to="/dia-horario">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.diaHorario')">Dia Horario</span>
    </b-dropdown-item>
    <b-dropdown-item to="/licencia-manejo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.licenciaManejo')">Licencia Manejo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/experiencia-habilidad">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.experienciaHabilidad')">Experiencia Habilidad</span>
    </b-dropdown-item>
    <b-dropdown-item to="/empleado-tipo-vehiculo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.empleadoTipoVehiculo')">Empleado Tipo Vehiculo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/vehiculo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.vehiculo')">Vehiculo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/unidad">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.unidad')">Unidad</span>
    </b-dropdown-item>
    <b-dropdown-item to="/poliza">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.poliza')">Poliza</span>
    </b-dropdown-item>
    <b-dropdown-item to="/aditamento">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.aditamento')">Aditamento</span>
    </b-dropdown-item>
    <b-dropdown-item to="/aditamento-unidad">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.aditamentoUnidad')">Aditamento Unidad</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cliente">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cliente')">Cliente</span>
    </b-dropdown-item>
    <b-dropdown-item to="/vehiculo-cliente">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.vehiculoCliente')">Vehiculo Cliente</span>
    </b-dropdown-item>
    <b-dropdown-item to="/viaje">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.viaje')">Viaje</span>
    </b-dropdown-item>
    <b-dropdown-item to="/itinerario">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.itinerario')">Itinerario</span>
    </b-dropdown-item>
    <b-dropdown-item to="/unidad-viaje">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.unidadViaje')">Unidad Viaje</span>
    </b-dropdown-item>
    <b-dropdown-item to="/operador-unidad">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.operadorUnidad')">Operador Unidad</span>
    </b-dropdown-item>
    <b-dropdown-item to="/estante">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estante')">Estante</span>
    </b-dropdown-item>
    <b-dropdown-item to="/nivel">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.nivel')">Nivel</span>
    </b-dropdown-item>
    <b-dropdown-item to="/seccion">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.seccion')">Seccion</span>
    </b-dropdown-item>
    <b-dropdown-item to="/producto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.producto')">Producto</span>
    </b-dropdown-item>
    <b-dropdown-item to="/inventario">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.inventario')">Inventario</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ubicacion-inventario">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ubicacionInventario')">Ubicacion Inventario</span>
    </b-dropdown-item>
    <b-dropdown-item to="/resurtir">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.resurtir')">Resurtir</span>
    </b-dropdown-item>
    <b-dropdown-item to="/renglon-resurtir">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.renglonResurtir')">Renglon Resurtir</span>
    </b-dropdown-item>
    <b-dropdown-item to="/precio-venta">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.precioVenta')">Precio Venta</span>
    </b-dropdown-item>
    <b-dropdown-item to="/venta">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.venta')">Venta</span>
    </b-dropdown-item>
    <b-dropdown-item to="/renglon-venta">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.renglonVenta')">Renglon Venta</span>
    </b-dropdown-item>
    <b-dropdown-item to="/perdida">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.perdida')">Perdida</span>
    </b-dropdown-item>
    <b-dropdown-item to="/servicio">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.servicio')">Servicio</span>
    </b-dropdown-item>
    <b-dropdown-item to="/material-servicio">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.materialServicio')">Material Servicio</span>
    </b-dropdown-item>
    <b-dropdown-item to="/precio-servicio">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.precioServicio')">Precio Servicio</span>
    </b-dropdown-item>
    <b-dropdown-item to="/unidad-servicio">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.unidadServicio')">Unidad Servicio</span>
    </b-dropdown-item>
    <b-dropdown-item to="/lista-servicio">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.listaServicio')">Lista Servicio</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cita">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cita')">Cita</span>
    </b-dropdown-item>
    <b-dropdown-item to="/status-cita">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.statusCita')">Status Cita</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cotizacion">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cotizacion')">Cotizacion</span>
    </b-dropdown-item>
    <b-dropdown-item to="/pago">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.pago')">Pago</span>
    </b-dropdown-item>
    <b-dropdown-item to="/pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.pedido')">Pedido</span>
    </b-dropdown-item>
    <b-dropdown-item to="/lista-pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.listaPedido')">Lista Pedido</span>
    </b-dropdown-item>
    <b-dropdown-item to="/venta-pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ventaPedido')">Venta Pedido</span>
    </b-dropdown-item>
    <b-dropdown-item to="/reporte">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.reporte')">Reporte</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
