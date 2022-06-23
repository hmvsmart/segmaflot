<template>
  <div>
    <h2 id="page-heading" data-cy="VehiculoHeading">
      <span v-text="$t('segmaflotApp.vehiculo.home.title')" id="vehiculo-heading">Vehiculos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.vehiculo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VehiculoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-vehiculo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.vehiculo.home.createLabel')"> Create a new Vehiculo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && vehiculos && vehiculos.length === 0">
      <span v-text="$t('segmaflotApp.vehiculo.home.notFound')">No vehiculos found</span>
    </div>
    <div class="table-responsive" v-if="vehiculos && vehiculos.length > 0">
      <table class="table table-striped" aria-describedby="vehiculos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.marca')">Marca</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.submarca')">Submarca</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.modelo')">Modelo</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.generacion')">Generacion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.tipoVehiculo')">Tipo Vehiculo</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculo.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vehiculo in vehiculos" :key="vehiculo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VehiculoView', params: { vehiculoId: vehiculo.id } }">{{ vehiculo.id }}</router-link>
            </td>
            <td>{{ vehiculo.marca }}</td>
            <td>{{ vehiculo.submarca }}</td>
            <td>{{ vehiculo.modelo }}</td>
            <td>{{ vehiculo.generacion }}</td>
            <td>{{ vehiculo.tipoVehiculo }}</td>
            <td>{{ vehiculo.createByUser }}</td>
            <td>{{ vehiculo.createdOn ? $d(Date.parse(vehiculo.createdOn), 'short') : '' }}</td>
            <td>{{ vehiculo.modifyByUser }}</td>
            <td>{{ vehiculo.modifiedOn ? $d(Date.parse(vehiculo.modifiedOn), 'short') : '' }}</td>
            <td>{{ vehiculo.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VehiculoView', params: { vehiculoId: vehiculo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VehiculoEdit', params: { vehiculoId: vehiculo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(vehiculo)"
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
        ><span id="segmaflotApp.vehiculo.delete.question" data-cy="vehiculoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vehiculo-heading" v-text="$t('segmaflotApp.vehiculo.delete.question', { id: removeId })">
          Are you sure you want to delete this Vehiculo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-vehiculo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVehiculo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./vehiculo.component.ts"></script>
