import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Estado = () => import('@/entities/estado/estado.vue');
// prettier-ignore
const EstadoUpdate = () => import('@/entities/estado/estado-update.vue');
// prettier-ignore
const EstadoDetails = () => import('@/entities/estado/estado-details.vue');
// prettier-ignore
const Ciudad = () => import('@/entities/ciudad/ciudad.vue');
// prettier-ignore
const CiudadUpdate = () => import('@/entities/ciudad/ciudad-update.vue');
// prettier-ignore
const CiudadDetails = () => import('@/entities/ciudad/ciudad-details.vue');
// prettier-ignore
const CP = () => import('@/entities/cp/cp.vue');
// prettier-ignore
const CPUpdate = () => import('@/entities/cp/cp-update.vue');
// prettier-ignore
const CPDetails = () => import('@/entities/cp/cp-details.vue');
// prettier-ignore
const Colonia = () => import('@/entities/colonia/colonia.vue');
// prettier-ignore
const ColoniaUpdate = () => import('@/entities/colonia/colonia-update.vue');
// prettier-ignore
const ColoniaDetails = () => import('@/entities/colonia/colonia-details.vue');
// prettier-ignore
const Direccion = () => import('@/entities/direccion/direccion.vue');
// prettier-ignore
const DireccionUpdate = () => import('@/entities/direccion/direccion-update.vue');
// prettier-ignore
const DireccionDetails = () => import('@/entities/direccion/direccion-details.vue');
// prettier-ignore
const Sucursal = () => import('@/entities/sucursal/sucursal.vue');
// prettier-ignore
const SucursalUpdate = () => import('@/entities/sucursal/sucursal-update.vue');
// prettier-ignore
const SucursalDetails = () => import('@/entities/sucursal/sucursal-details.vue');
// prettier-ignore
const Persona = () => import('@/entities/persona/persona.vue');
// prettier-ignore
const PersonaUpdate = () => import('@/entities/persona/persona-update.vue');
// prettier-ignore
const PersonaDetails = () => import('@/entities/persona/persona-details.vue');
// prettier-ignore
const PersonaMoral = () => import('@/entities/persona-moral/persona-moral.vue');
// prettier-ignore
const PersonaMoralUpdate = () => import('@/entities/persona-moral/persona-moral-update.vue');
// prettier-ignore
const PersonaMoralDetails = () => import('@/entities/persona-moral/persona-moral-details.vue');
// prettier-ignore
const PersonaFisica = () => import('@/entities/persona-fisica/persona-fisica.vue');
// prettier-ignore
const PersonaFisicaUpdate = () => import('@/entities/persona-fisica/persona-fisica-update.vue');
// prettier-ignore
const PersonaFisicaDetails = () => import('@/entities/persona-fisica/persona-fisica-details.vue');
// prettier-ignore
const TipoContacto = () => import('@/entities/tipo-contacto/tipo-contacto.vue');
// prettier-ignore
const TipoContactoUpdate = () => import('@/entities/tipo-contacto/tipo-contacto-update.vue');
// prettier-ignore
const TipoContactoDetails = () => import('@/entities/tipo-contacto/tipo-contacto-details.vue');
// prettier-ignore
const Contacto = () => import('@/entities/contacto/contacto.vue');
// prettier-ignore
const ContactoUpdate = () => import('@/entities/contacto/contacto-update.vue');
// prettier-ignore
const ContactoDetails = () => import('@/entities/contacto/contacto-details.vue');
// prettier-ignore
const DireccionPersona = () => import('@/entities/direccion-persona/direccion-persona.vue');
// prettier-ignore
const DireccionPersonaUpdate = () => import('@/entities/direccion-persona/direccion-persona-update.vue');
// prettier-ignore
const DireccionPersonaDetails = () => import('@/entities/direccion-persona/direccion-persona-details.vue');
// prettier-ignore
const Empleado = () => import('@/entities/empleado/empleado.vue');
// prettier-ignore
const EmpleadoUpdate = () => import('@/entities/empleado/empleado-update.vue');
// prettier-ignore
const EmpleadoDetails = () => import('@/entities/empleado/empleado-details.vue');
// prettier-ignore
const Horario = () => import('@/entities/horario/horario.vue');
// prettier-ignore
const HorarioUpdate = () => import('@/entities/horario/horario-update.vue');
// prettier-ignore
const HorarioDetails = () => import('@/entities/horario/horario-details.vue');
// prettier-ignore
const DiaHorario = () => import('@/entities/dia-horario/dia-horario.vue');
// prettier-ignore
const DiaHorarioUpdate = () => import('@/entities/dia-horario/dia-horario-update.vue');
// prettier-ignore
const DiaHorarioDetails = () => import('@/entities/dia-horario/dia-horario-details.vue');
// prettier-ignore
const LicenciaManejo = () => import('@/entities/licencia-manejo/licencia-manejo.vue');
// prettier-ignore
const LicenciaManejoUpdate = () => import('@/entities/licencia-manejo/licencia-manejo-update.vue');
// prettier-ignore
const LicenciaManejoDetails = () => import('@/entities/licencia-manejo/licencia-manejo-details.vue');
// prettier-ignore
const ExperienciaHabilidad = () => import('@/entities/experiencia-habilidad/experiencia-habilidad.vue');
// prettier-ignore
const ExperienciaHabilidadUpdate = () => import('@/entities/experiencia-habilidad/experiencia-habilidad-update.vue');
// prettier-ignore
const ExperienciaHabilidadDetails = () => import('@/entities/experiencia-habilidad/experiencia-habilidad-details.vue');
// prettier-ignore
const EmpleadoTipoVehiculo = () => import('@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.vue');
// prettier-ignore
const EmpleadoTipoVehiculoUpdate = () => import('@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo-update.vue');
// prettier-ignore
const EmpleadoTipoVehiculoDetails = () => import('@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo-details.vue');
// prettier-ignore
const Vehiculo = () => import('@/entities/vehiculo/vehiculo.vue');
// prettier-ignore
const VehiculoUpdate = () => import('@/entities/vehiculo/vehiculo-update.vue');
// prettier-ignore
const VehiculoDetails = () => import('@/entities/vehiculo/vehiculo-details.vue');
// prettier-ignore
const Unidad = () => import('@/entities/unidad/unidad.vue');
// prettier-ignore
const UnidadUpdate = () => import('@/entities/unidad/unidad-update.vue');
// prettier-ignore
const UnidadDetails = () => import('@/entities/unidad/unidad-details.vue');
// prettier-ignore
const Poliza = () => import('@/entities/poliza/poliza.vue');
// prettier-ignore
const PolizaUpdate = () => import('@/entities/poliza/poliza-update.vue');
// prettier-ignore
const PolizaDetails = () => import('@/entities/poliza/poliza-details.vue');
// prettier-ignore
const Aditamento = () => import('@/entities/aditamento/aditamento.vue');
// prettier-ignore
const AditamentoUpdate = () => import('@/entities/aditamento/aditamento-update.vue');
// prettier-ignore
const AditamentoDetails = () => import('@/entities/aditamento/aditamento-details.vue');
// prettier-ignore
const AditamentoUnidad = () => import('@/entities/aditamento-unidad/aditamento-unidad.vue');
// prettier-ignore
const AditamentoUnidadUpdate = () => import('@/entities/aditamento-unidad/aditamento-unidad-update.vue');
// prettier-ignore
const AditamentoUnidadDetails = () => import('@/entities/aditamento-unidad/aditamento-unidad-details.vue');
// prettier-ignore
const Cliente = () => import('@/entities/cliente/cliente.vue');
// prettier-ignore
const ClienteUpdate = () => import('@/entities/cliente/cliente-update.vue');
// prettier-ignore
const ClienteDetails = () => import('@/entities/cliente/cliente-details.vue');
// prettier-ignore
const VehiculoCliente = () => import('@/entities/vehiculo-cliente/vehiculo-cliente.vue');
// prettier-ignore
const VehiculoClienteUpdate = () => import('@/entities/vehiculo-cliente/vehiculo-cliente-update.vue');
// prettier-ignore
const VehiculoClienteDetails = () => import('@/entities/vehiculo-cliente/vehiculo-cliente-details.vue');
// prettier-ignore
const Viaje = () => import('@/entities/viaje/viaje.vue');
// prettier-ignore
const ViajeUpdate = () => import('@/entities/viaje/viaje-update.vue');
// prettier-ignore
const ViajeDetails = () => import('@/entities/viaje/viaje-details.vue');
// prettier-ignore
const Itinerario = () => import('@/entities/itinerario/itinerario.vue');
// prettier-ignore
const ItinerarioUpdate = () => import('@/entities/itinerario/itinerario-update.vue');
// prettier-ignore
const ItinerarioDetails = () => import('@/entities/itinerario/itinerario-details.vue');
// prettier-ignore
const UnidadViaje = () => import('@/entities/unidad-viaje/unidad-viaje.vue');
// prettier-ignore
const UnidadViajeUpdate = () => import('@/entities/unidad-viaje/unidad-viaje-update.vue');
// prettier-ignore
const UnidadViajeDetails = () => import('@/entities/unidad-viaje/unidad-viaje-details.vue');
// prettier-ignore
const OperadorUnidad = () => import('@/entities/operador-unidad/operador-unidad.vue');
// prettier-ignore
const OperadorUnidadUpdate = () => import('@/entities/operador-unidad/operador-unidad-update.vue');
// prettier-ignore
const OperadorUnidadDetails = () => import('@/entities/operador-unidad/operador-unidad-details.vue');
// prettier-ignore
const Estante = () => import('@/entities/estante/estante.vue');
// prettier-ignore
const EstanteUpdate = () => import('@/entities/estante/estante-update.vue');
// prettier-ignore
const EstanteDetails = () => import('@/entities/estante/estante-details.vue');
// prettier-ignore
const Nivel = () => import('@/entities/nivel/nivel.vue');
// prettier-ignore
const NivelUpdate = () => import('@/entities/nivel/nivel-update.vue');
// prettier-ignore
const NivelDetails = () => import('@/entities/nivel/nivel-details.vue');
// prettier-ignore
const Seccion = () => import('@/entities/seccion/seccion.vue');
// prettier-ignore
const SeccionUpdate = () => import('@/entities/seccion/seccion-update.vue');
// prettier-ignore
const SeccionDetails = () => import('@/entities/seccion/seccion-details.vue');
// prettier-ignore
const Producto = () => import('@/entities/producto/producto.vue');
// prettier-ignore
const ProductoUpdate = () => import('@/entities/producto/producto-update.vue');
// prettier-ignore
const ProductoDetails = () => import('@/entities/producto/producto-details.vue');
// prettier-ignore
const Inventario = () => import('@/entities/inventario/inventario.vue');
// prettier-ignore
const InventarioUpdate = () => import('@/entities/inventario/inventario-update.vue');
// prettier-ignore
const InventarioDetails = () => import('@/entities/inventario/inventario-details.vue');
// prettier-ignore
const UbicacionInventario = () => import('@/entities/ubicacion-inventario/ubicacion-inventario.vue');
// prettier-ignore
const UbicacionInventarioUpdate = () => import('@/entities/ubicacion-inventario/ubicacion-inventario-update.vue');
// prettier-ignore
const UbicacionInventarioDetails = () => import('@/entities/ubicacion-inventario/ubicacion-inventario-details.vue');
// prettier-ignore
const Resurtir = () => import('@/entities/resurtir/resurtir.vue');
// prettier-ignore
const ResurtirUpdate = () => import('@/entities/resurtir/resurtir-update.vue');
// prettier-ignore
const ResurtirDetails = () => import('@/entities/resurtir/resurtir-details.vue');
// prettier-ignore
const RenglonResurtir = () => import('@/entities/renglon-resurtir/renglon-resurtir.vue');
// prettier-ignore
const RenglonResurtirUpdate = () => import('@/entities/renglon-resurtir/renglon-resurtir-update.vue');
// prettier-ignore
const RenglonResurtirDetails = () => import('@/entities/renglon-resurtir/renglon-resurtir-details.vue');
// prettier-ignore
const PrecioVenta = () => import('@/entities/precio-venta/precio-venta.vue');
// prettier-ignore
const PrecioVentaUpdate = () => import('@/entities/precio-venta/precio-venta-update.vue');
// prettier-ignore
const PrecioVentaDetails = () => import('@/entities/precio-venta/precio-venta-details.vue');
// prettier-ignore
const Venta = () => import('@/entities/venta/venta.vue');
// prettier-ignore
const VentaUpdate = () => import('@/entities/venta/venta-update.vue');
// prettier-ignore
const VentaDetails = () => import('@/entities/venta/venta-details.vue');
// prettier-ignore
const RenglonVenta = () => import('@/entities/renglon-venta/renglon-venta.vue');
// prettier-ignore
const RenglonVentaUpdate = () => import('@/entities/renglon-venta/renglon-venta-update.vue');
// prettier-ignore
const RenglonVentaDetails = () => import('@/entities/renglon-venta/renglon-venta-details.vue');
// prettier-ignore
const Perdida = () => import('@/entities/perdida/perdida.vue');
// prettier-ignore
const PerdidaUpdate = () => import('@/entities/perdida/perdida-update.vue');
// prettier-ignore
const PerdidaDetails = () => import('@/entities/perdida/perdida-details.vue');
// prettier-ignore
const Servicio = () => import('@/entities/servicio/servicio.vue');
// prettier-ignore
const ServicioUpdate = () => import('@/entities/servicio/servicio-update.vue');
// prettier-ignore
const ServicioDetails = () => import('@/entities/servicio/servicio-details.vue');
// prettier-ignore
const MaterialServicio = () => import('@/entities/material-servicio/material-servicio.vue');
// prettier-ignore
const MaterialServicioUpdate = () => import('@/entities/material-servicio/material-servicio-update.vue');
// prettier-ignore
const MaterialServicioDetails = () => import('@/entities/material-servicio/material-servicio-details.vue');
// prettier-ignore
const PrecioServicio = () => import('@/entities/precio-servicio/precio-servicio.vue');
// prettier-ignore
const PrecioServicioUpdate = () => import('@/entities/precio-servicio/precio-servicio-update.vue');
// prettier-ignore
const PrecioServicioDetails = () => import('@/entities/precio-servicio/precio-servicio-details.vue');
// prettier-ignore
const UnidadServicio = () => import('@/entities/unidad-servicio/unidad-servicio.vue');
// prettier-ignore
const UnidadServicioUpdate = () => import('@/entities/unidad-servicio/unidad-servicio-update.vue');
// prettier-ignore
const UnidadServicioDetails = () => import('@/entities/unidad-servicio/unidad-servicio-details.vue');
// prettier-ignore
const ListaServicio = () => import('@/entities/lista-servicio/lista-servicio.vue');
// prettier-ignore
const ListaServicioUpdate = () => import('@/entities/lista-servicio/lista-servicio-update.vue');
// prettier-ignore
const ListaServicioDetails = () => import('@/entities/lista-servicio/lista-servicio-details.vue');
// prettier-ignore
const Cita = () => import('@/entities/cita/cita.vue');
// prettier-ignore
const CitaUpdate = () => import('@/entities/cita/cita-update.vue');
// prettier-ignore
const CitaDetails = () => import('@/entities/cita/cita-details.vue');
// prettier-ignore
const StatusCita = () => import('@/entities/status-cita/status-cita.vue');
// prettier-ignore
const StatusCitaUpdate = () => import('@/entities/status-cita/status-cita-update.vue');
// prettier-ignore
const StatusCitaDetails = () => import('@/entities/status-cita/status-cita-details.vue');
// prettier-ignore
const Cotizacion = () => import('@/entities/cotizacion/cotizacion.vue');
// prettier-ignore
const CotizacionUpdate = () => import('@/entities/cotizacion/cotizacion-update.vue');
// prettier-ignore
const CotizacionDetails = () => import('@/entities/cotizacion/cotizacion-details.vue');
// prettier-ignore
const Pago = () => import('@/entities/pago/pago.vue');
// prettier-ignore
const PagoUpdate = () => import('@/entities/pago/pago-update.vue');
// prettier-ignore
const PagoDetails = () => import('@/entities/pago/pago-details.vue');
// prettier-ignore
const Pedido = () => import('@/entities/pedido/pedido.vue');
// prettier-ignore
const PedidoUpdate = () => import('@/entities/pedido/pedido-update.vue');
// prettier-ignore
const PedidoDetails = () => import('@/entities/pedido/pedido-details.vue');
// prettier-ignore
const ListaPedido = () => import('@/entities/lista-pedido/lista-pedido.vue');
// prettier-ignore
const ListaPedidoUpdate = () => import('@/entities/lista-pedido/lista-pedido-update.vue');
// prettier-ignore
const ListaPedidoDetails = () => import('@/entities/lista-pedido/lista-pedido-details.vue');
// prettier-ignore
const VentaPedido = () => import('@/entities/venta-pedido/venta-pedido.vue');
// prettier-ignore
const VentaPedidoUpdate = () => import('@/entities/venta-pedido/venta-pedido-update.vue');
// prettier-ignore
const VentaPedidoDetails = () => import('@/entities/venta-pedido/venta-pedido-details.vue');
// prettier-ignore
const Reporte = () => import('@/entities/reporte/reporte.vue');
// prettier-ignore
const ReporteUpdate = () => import('@/entities/reporte/reporte-update.vue');
// prettier-ignore
const ReporteDetails = () => import('@/entities/reporte/reporte-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'estado',
      name: 'Estado',
      component: Estado,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/new',
      name: 'EstadoCreate',
      component: EstadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/:estadoId/edit',
      name: 'EstadoEdit',
      component: EstadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/:estadoId/view',
      name: 'EstadoView',
      component: EstadoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ciudad',
      name: 'Ciudad',
      component: Ciudad,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ciudad/new',
      name: 'CiudadCreate',
      component: CiudadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ciudad/:ciudadId/edit',
      name: 'CiudadEdit',
      component: CiudadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ciudad/:ciudadId/view',
      name: 'CiudadView',
      component: CiudadDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cp',
      name: 'CP',
      component: CP,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cp/new',
      name: 'CPCreate',
      component: CPUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cp/:cPId/edit',
      name: 'CPEdit',
      component: CPUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cp/:cPId/view',
      name: 'CPView',
      component: CPDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'colonia',
      name: 'Colonia',
      component: Colonia,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'colonia/new',
      name: 'ColoniaCreate',
      component: ColoniaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'colonia/:coloniaId/edit',
      name: 'ColoniaEdit',
      component: ColoniaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'colonia/:coloniaId/view',
      name: 'ColoniaView',
      component: ColoniaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion',
      name: 'Direccion',
      component: Direccion,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion/new',
      name: 'DireccionCreate',
      component: DireccionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion/:direccionId/edit',
      name: 'DireccionEdit',
      component: DireccionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion/:direccionId/view',
      name: 'DireccionView',
      component: DireccionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sucursal',
      name: 'Sucursal',
      component: Sucursal,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sucursal/new',
      name: 'SucursalCreate',
      component: SucursalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sucursal/:sucursalId/edit',
      name: 'SucursalEdit',
      component: SucursalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sucursal/:sucursalId/view',
      name: 'SucursalView',
      component: SucursalDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona',
      name: 'Persona',
      component: Persona,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona/new',
      name: 'PersonaCreate',
      component: PersonaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona/:personaId/edit',
      name: 'PersonaEdit',
      component: PersonaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona/:personaId/view',
      name: 'PersonaView',
      component: PersonaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-moral',
      name: 'PersonaMoral',
      component: PersonaMoral,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-moral/new',
      name: 'PersonaMoralCreate',
      component: PersonaMoralUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-moral/:personaMoralId/edit',
      name: 'PersonaMoralEdit',
      component: PersonaMoralUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-moral/:personaMoralId/view',
      name: 'PersonaMoralView',
      component: PersonaMoralDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-fisica',
      name: 'PersonaFisica',
      component: PersonaFisica,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-fisica/new',
      name: 'PersonaFisicaCreate',
      component: PersonaFisicaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-fisica/:personaFisicaId/edit',
      name: 'PersonaFisicaEdit',
      component: PersonaFisicaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persona-fisica/:personaFisicaId/view',
      name: 'PersonaFisicaView',
      component: PersonaFisicaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-contacto',
      name: 'TipoContacto',
      component: TipoContacto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-contacto/new',
      name: 'TipoContactoCreate',
      component: TipoContactoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-contacto/:tipoContactoId/edit',
      name: 'TipoContactoEdit',
      component: TipoContactoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-contacto/:tipoContactoId/view',
      name: 'TipoContactoView',
      component: TipoContactoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contacto',
      name: 'Contacto',
      component: Contacto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contacto/new',
      name: 'ContactoCreate',
      component: ContactoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contacto/:contactoId/edit',
      name: 'ContactoEdit',
      component: ContactoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contacto/:contactoId/view',
      name: 'ContactoView',
      component: ContactoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion-persona',
      name: 'DireccionPersona',
      component: DireccionPersona,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion-persona/new',
      name: 'DireccionPersonaCreate',
      component: DireccionPersonaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion-persona/:direccionPersonaId/edit',
      name: 'DireccionPersonaEdit',
      component: DireccionPersonaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'direccion-persona/:direccionPersonaId/view',
      name: 'DireccionPersonaView',
      component: DireccionPersonaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado',
      name: 'Empleado',
      component: Empleado,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado/new',
      name: 'EmpleadoCreate',
      component: EmpleadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado/:empleadoId/edit',
      name: 'EmpleadoEdit',
      component: EmpleadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado/:empleadoId/view',
      name: 'EmpleadoView',
      component: EmpleadoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'horario',
      name: 'Horario',
      component: Horario,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'horario/new',
      name: 'HorarioCreate',
      component: HorarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'horario/:horarioId/edit',
      name: 'HorarioEdit',
      component: HorarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'horario/:horarioId/view',
      name: 'HorarioView',
      component: HorarioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dia-horario',
      name: 'DiaHorario',
      component: DiaHorario,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dia-horario/new',
      name: 'DiaHorarioCreate',
      component: DiaHorarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dia-horario/:diaHorarioId/edit',
      name: 'DiaHorarioEdit',
      component: DiaHorarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'dia-horario/:diaHorarioId/view',
      name: 'DiaHorarioView',
      component: DiaHorarioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'licencia-manejo',
      name: 'LicenciaManejo',
      component: LicenciaManejo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'licencia-manejo/new',
      name: 'LicenciaManejoCreate',
      component: LicenciaManejoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'licencia-manejo/:licenciaManejoId/edit',
      name: 'LicenciaManejoEdit',
      component: LicenciaManejoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'licencia-manejo/:licenciaManejoId/view',
      name: 'LicenciaManejoView',
      component: LicenciaManejoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'experiencia-habilidad',
      name: 'ExperienciaHabilidad',
      component: ExperienciaHabilidad,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'experiencia-habilidad/new',
      name: 'ExperienciaHabilidadCreate',
      component: ExperienciaHabilidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'experiencia-habilidad/:experienciaHabilidadId/edit',
      name: 'ExperienciaHabilidadEdit',
      component: ExperienciaHabilidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'experiencia-habilidad/:experienciaHabilidadId/view',
      name: 'ExperienciaHabilidadView',
      component: ExperienciaHabilidadDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado-tipo-vehiculo',
      name: 'EmpleadoTipoVehiculo',
      component: EmpleadoTipoVehiculo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado-tipo-vehiculo/new',
      name: 'EmpleadoTipoVehiculoCreate',
      component: EmpleadoTipoVehiculoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado-tipo-vehiculo/:empleadoTipoVehiculoId/edit',
      name: 'EmpleadoTipoVehiculoEdit',
      component: EmpleadoTipoVehiculoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'empleado-tipo-vehiculo/:empleadoTipoVehiculoId/view',
      name: 'EmpleadoTipoVehiculoView',
      component: EmpleadoTipoVehiculoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo',
      name: 'Vehiculo',
      component: Vehiculo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo/new',
      name: 'VehiculoCreate',
      component: VehiculoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo/:vehiculoId/edit',
      name: 'VehiculoEdit',
      component: VehiculoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo/:vehiculoId/view',
      name: 'VehiculoView',
      component: VehiculoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad',
      name: 'Unidad',
      component: Unidad,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad/new',
      name: 'UnidadCreate',
      component: UnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad/:unidadId/edit',
      name: 'UnidadEdit',
      component: UnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad/:unidadId/view',
      name: 'UnidadView',
      component: UnidadDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'poliza',
      name: 'Poliza',
      component: Poliza,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'poliza/new',
      name: 'PolizaCreate',
      component: PolizaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'poliza/:polizaId/edit',
      name: 'PolizaEdit',
      component: PolizaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'poliza/:polizaId/view',
      name: 'PolizaView',
      component: PolizaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento',
      name: 'Aditamento',
      component: Aditamento,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento/new',
      name: 'AditamentoCreate',
      component: AditamentoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento/:aditamentoId/edit',
      name: 'AditamentoEdit',
      component: AditamentoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento/:aditamentoId/view',
      name: 'AditamentoView',
      component: AditamentoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento-unidad',
      name: 'AditamentoUnidad',
      component: AditamentoUnidad,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento-unidad/new',
      name: 'AditamentoUnidadCreate',
      component: AditamentoUnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento-unidad/:aditamentoUnidadId/edit',
      name: 'AditamentoUnidadEdit',
      component: AditamentoUnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aditamento-unidad/:aditamentoUnidadId/view',
      name: 'AditamentoUnidadView',
      component: AditamentoUnidadDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente',
      name: 'Cliente',
      component: Cliente,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/new',
      name: 'ClienteCreate',
      component: ClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/:clienteId/edit',
      name: 'ClienteEdit',
      component: ClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/:clienteId/view',
      name: 'ClienteView',
      component: ClienteDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo-cliente',
      name: 'VehiculoCliente',
      component: VehiculoCliente,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo-cliente/new',
      name: 'VehiculoClienteCreate',
      component: VehiculoClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo-cliente/:vehiculoClienteId/edit',
      name: 'VehiculoClienteEdit',
      component: VehiculoClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vehiculo-cliente/:vehiculoClienteId/view',
      name: 'VehiculoClienteView',
      component: VehiculoClienteDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'viaje',
      name: 'Viaje',
      component: Viaje,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'viaje/new',
      name: 'ViajeCreate',
      component: ViajeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'viaje/:viajeId/edit',
      name: 'ViajeEdit',
      component: ViajeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'viaje/:viajeId/view',
      name: 'ViajeView',
      component: ViajeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'itinerario',
      name: 'Itinerario',
      component: Itinerario,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'itinerario/new',
      name: 'ItinerarioCreate',
      component: ItinerarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'itinerario/:itinerarioId/edit',
      name: 'ItinerarioEdit',
      component: ItinerarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'itinerario/:itinerarioId/view',
      name: 'ItinerarioView',
      component: ItinerarioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-viaje',
      name: 'UnidadViaje',
      component: UnidadViaje,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-viaje/new',
      name: 'UnidadViajeCreate',
      component: UnidadViajeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-viaje/:unidadViajeId/edit',
      name: 'UnidadViajeEdit',
      component: UnidadViajeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-viaje/:unidadViajeId/view',
      name: 'UnidadViajeView',
      component: UnidadViajeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operador-unidad',
      name: 'OperadorUnidad',
      component: OperadorUnidad,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operador-unidad/new',
      name: 'OperadorUnidadCreate',
      component: OperadorUnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operador-unidad/:operadorUnidadId/edit',
      name: 'OperadorUnidadEdit',
      component: OperadorUnidadUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operador-unidad/:operadorUnidadId/view',
      name: 'OperadorUnidadView',
      component: OperadorUnidadDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estante',
      name: 'Estante',
      component: Estante,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estante/new',
      name: 'EstanteCreate',
      component: EstanteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estante/:estanteId/edit',
      name: 'EstanteEdit',
      component: EstanteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estante/:estanteId/view',
      name: 'EstanteView',
      component: EstanteDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nivel',
      name: 'Nivel',
      component: Nivel,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nivel/new',
      name: 'NivelCreate',
      component: NivelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nivel/:nivelId/edit',
      name: 'NivelEdit',
      component: NivelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nivel/:nivelId/view',
      name: 'NivelView',
      component: NivelDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seccion',
      name: 'Seccion',
      component: Seccion,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seccion/new',
      name: 'SeccionCreate',
      component: SeccionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seccion/:seccionId/edit',
      name: 'SeccionEdit',
      component: SeccionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seccion/:seccionId/view',
      name: 'SeccionView',
      component: SeccionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto',
      name: 'Producto',
      component: Producto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/new',
      name: 'ProductoCreate',
      component: ProductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/:productoId/edit',
      name: 'ProductoEdit',
      component: ProductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/:productoId/view',
      name: 'ProductoView',
      component: ProductoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventario',
      name: 'Inventario',
      component: Inventario,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventario/new',
      name: 'InventarioCreate',
      component: InventarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventario/:inventarioId/edit',
      name: 'InventarioEdit',
      component: InventarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventario/:inventarioId/view',
      name: 'InventarioView',
      component: InventarioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ubicacion-inventario',
      name: 'UbicacionInventario',
      component: UbicacionInventario,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ubicacion-inventario/new',
      name: 'UbicacionInventarioCreate',
      component: UbicacionInventarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ubicacion-inventario/:ubicacionInventarioId/edit',
      name: 'UbicacionInventarioEdit',
      component: UbicacionInventarioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ubicacion-inventario/:ubicacionInventarioId/view',
      name: 'UbicacionInventarioView',
      component: UbicacionInventarioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resurtir',
      name: 'Resurtir',
      component: Resurtir,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resurtir/new',
      name: 'ResurtirCreate',
      component: ResurtirUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resurtir/:resurtirId/edit',
      name: 'ResurtirEdit',
      component: ResurtirUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resurtir/:resurtirId/view',
      name: 'ResurtirView',
      component: ResurtirDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-resurtir',
      name: 'RenglonResurtir',
      component: RenglonResurtir,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-resurtir/new',
      name: 'RenglonResurtirCreate',
      component: RenglonResurtirUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-resurtir/:renglonResurtirId/edit',
      name: 'RenglonResurtirEdit',
      component: RenglonResurtirUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-resurtir/:renglonResurtirId/view',
      name: 'RenglonResurtirView',
      component: RenglonResurtirDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-venta',
      name: 'PrecioVenta',
      component: PrecioVenta,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-venta/new',
      name: 'PrecioVentaCreate',
      component: PrecioVentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-venta/:precioVentaId/edit',
      name: 'PrecioVentaEdit',
      component: PrecioVentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-venta/:precioVentaId/view',
      name: 'PrecioVentaView',
      component: PrecioVentaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta',
      name: 'Venta',
      component: Venta,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta/new',
      name: 'VentaCreate',
      component: VentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta/:ventaId/edit',
      name: 'VentaEdit',
      component: VentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta/:ventaId/view',
      name: 'VentaView',
      component: VentaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-venta',
      name: 'RenglonVenta',
      component: RenglonVenta,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-venta/new',
      name: 'RenglonVentaCreate',
      component: RenglonVentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-venta/:renglonVentaId/edit',
      name: 'RenglonVentaEdit',
      component: RenglonVentaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'renglon-venta/:renglonVentaId/view',
      name: 'RenglonVentaView',
      component: RenglonVentaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perdida',
      name: 'Perdida',
      component: Perdida,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perdida/new',
      name: 'PerdidaCreate',
      component: PerdidaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perdida/:perdidaId/edit',
      name: 'PerdidaEdit',
      component: PerdidaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perdida/:perdidaId/view',
      name: 'PerdidaView',
      component: PerdidaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servicio',
      name: 'Servicio',
      component: Servicio,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servicio/new',
      name: 'ServicioCreate',
      component: ServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servicio/:servicioId/edit',
      name: 'ServicioEdit',
      component: ServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'servicio/:servicioId/view',
      name: 'ServicioView',
      component: ServicioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'material-servicio',
      name: 'MaterialServicio',
      component: MaterialServicio,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'material-servicio/new',
      name: 'MaterialServicioCreate',
      component: MaterialServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'material-servicio/:materialServicioId/edit',
      name: 'MaterialServicioEdit',
      component: MaterialServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'material-servicio/:materialServicioId/view',
      name: 'MaterialServicioView',
      component: MaterialServicioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-servicio',
      name: 'PrecioServicio',
      component: PrecioServicio,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-servicio/new',
      name: 'PrecioServicioCreate',
      component: PrecioServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-servicio/:precioServicioId/edit',
      name: 'PrecioServicioEdit',
      component: PrecioServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'precio-servicio/:precioServicioId/view',
      name: 'PrecioServicioView',
      component: PrecioServicioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-servicio',
      name: 'UnidadServicio',
      component: UnidadServicio,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-servicio/new',
      name: 'UnidadServicioCreate',
      component: UnidadServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-servicio/:unidadServicioId/edit',
      name: 'UnidadServicioEdit',
      component: UnidadServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unidad-servicio/:unidadServicioId/view',
      name: 'UnidadServicioView',
      component: UnidadServicioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-servicio',
      name: 'ListaServicio',
      component: ListaServicio,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-servicio/new',
      name: 'ListaServicioCreate',
      component: ListaServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-servicio/:listaServicioId/edit',
      name: 'ListaServicioEdit',
      component: ListaServicioUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-servicio/:listaServicioId/view',
      name: 'ListaServicioView',
      component: ListaServicioDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cita',
      name: 'Cita',
      component: Cita,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cita/new',
      name: 'CitaCreate',
      component: CitaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cita/:citaId/edit',
      name: 'CitaEdit',
      component: CitaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cita/:citaId/view',
      name: 'CitaView',
      component: CitaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status-cita',
      name: 'StatusCita',
      component: StatusCita,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status-cita/new',
      name: 'StatusCitaCreate',
      component: StatusCitaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status-cita/:statusCitaId/edit',
      name: 'StatusCitaEdit',
      component: StatusCitaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status-cita/:statusCitaId/view',
      name: 'StatusCitaView',
      component: StatusCitaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cotizacion',
      name: 'Cotizacion',
      component: Cotizacion,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cotizacion/new',
      name: 'CotizacionCreate',
      component: CotizacionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cotizacion/:cotizacionId/edit',
      name: 'CotizacionEdit',
      component: CotizacionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cotizacion/:cotizacionId/view',
      name: 'CotizacionView',
      component: CotizacionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pago',
      name: 'Pago',
      component: Pago,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pago/new',
      name: 'PagoCreate',
      component: PagoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pago/:pagoId/edit',
      name: 'PagoEdit',
      component: PagoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pago/:pagoId/view',
      name: 'PagoView',
      component: PagoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido',
      name: 'Pedido',
      component: Pedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/new',
      name: 'PedidoCreate',
      component: PedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/:pedidoId/edit',
      name: 'PedidoEdit',
      component: PedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/:pedidoId/view',
      name: 'PedidoView',
      component: PedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-pedido',
      name: 'ListaPedido',
      component: ListaPedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-pedido/new',
      name: 'ListaPedidoCreate',
      component: ListaPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-pedido/:listaPedidoId/edit',
      name: 'ListaPedidoEdit',
      component: ListaPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'lista-pedido/:listaPedidoId/view',
      name: 'ListaPedidoView',
      component: ListaPedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta-pedido',
      name: 'VentaPedido',
      component: VentaPedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta-pedido/new',
      name: 'VentaPedidoCreate',
      component: VentaPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta-pedido/:ventaPedidoId/edit',
      name: 'VentaPedidoEdit',
      component: VentaPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venta-pedido/:ventaPedidoId/view',
      name: 'VentaPedidoView',
      component: VentaPedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reporte',
      name: 'Reporte',
      component: Reporte,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reporte/new',
      name: 'ReporteCreate',
      component: ReporteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reporte/:reporteId/edit',
      name: 'ReporteEdit',
      component: ReporteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reporte/:reporteId/view',
      name: 'ReporteView',
      component: ReporteDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
