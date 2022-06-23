<template>
  <div>
    <h2 id="page-heading" data-cy="EmpleadoTipoVehiculoHeading">
      <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.home.title')" id="empleado-tipo-vehiculo-heading">Empleado Tipo Vehiculos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EmpleadoTipoVehiculoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-empleado-tipo-vehiculo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.home.createLabel')"> Create a new Empleado Tipo Vehiculo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && empleadoTipoVehiculos && empleadoTipoVehiculos.length === 0">
      <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.home.notFound')">No empleadoTipoVehiculos found</span>
    </div>
    <div class="table-responsive" v-if="empleadoTipoVehiculos && empleadoTipoVehiculos.length > 0">
      <table class="table table-striped" aria-describedby="empleadoTipoVehiculos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleadoTipoVehiculo.empleado')">Empleado</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="empleadoTipoVehiculo in empleadoTipoVehiculos" :key="empleadoTipoVehiculo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmpleadoTipoVehiculoView', params: { empleadoTipoVehiculoId: empleadoTipoVehiculo.id } }">{{
                empleadoTipoVehiculo.id
              }}</router-link>
            </td>
            <td>{{ empleadoTipoVehiculo.fecha }}</td>
            <td>{{ empleadoTipoVehiculo.status }}</td>
            <td>{{ empleadoTipoVehiculo.createByUser }}</td>
            <td>{{ empleadoTipoVehiculo.createdOn ? $d(Date.parse(empleadoTipoVehiculo.createdOn), 'short') : '' }}</td>
            <td>{{ empleadoTipoVehiculo.modifyByUser }}</td>
            <td>{{ empleadoTipoVehiculo.modifiedOn ? $d(Date.parse(empleadoTipoVehiculo.modifiedOn), 'short') : '' }}</td>
            <td>{{ empleadoTipoVehiculo.auditStatus }}</td>
            <td>
              <div v-if="empleadoTipoVehiculo.empleado">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: empleadoTipoVehiculo.empleado.id } }">{{
                  empleadoTipoVehiculo.empleado.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'EmpleadoTipoVehiculoView', params: { empleadoTipoVehiculoId: empleadoTipoVehiculo.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'EmpleadoTipoVehiculoEdit', params: { empleadoTipoVehiculoId: empleadoTipoVehiculo.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(empleadoTipoVehiculo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="segmaflotApp.empleadoTipoVehiculo.delete.question"
          data-cy="empleadoTipoVehiculoDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-empleadoTipoVehiculo-heading" v-text="$t('segmaflotApp.empleadoTipoVehiculo.delete.question', { id: removeId })">
          Are you sure you want to delete this Empleado Tipo Vehiculo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-empleadoTipoVehiculo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEmpleadoTipoVehiculo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./empleado-tipo-vehiculo.component.ts"></script>
