<template>
  <div>
    <h2 id="page-heading" data-cy="VehiculoClienteHeading">
      <span v-text="$t('segmaflotApp.vehiculoCliente.home.title')" id="vehiculo-cliente-heading">Vehiculo Clientes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.vehiculoCliente.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VehiculoClienteCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-vehiculo-cliente"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.vehiculoCliente.home.createLabel')"> Create a new Vehiculo Cliente </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && vehiculoClientes && vehiculoClientes.length === 0">
      <span v-text="$t('segmaflotApp.vehiculoCliente.home.notFound')">No vehiculoClientes found</span>
    </div>
    <div class="table-responsive" v-if="vehiculoClientes && vehiculoClientes.length > 0">
      <table class="table table-striped" aria-describedby="vehiculoClientes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.numeroSerie')">Numero Serie</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.color')">Color</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.vehiculoCliente.cliente')">Cliente</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vehiculoCliente in vehiculoClientes" :key="vehiculoCliente.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VehiculoClienteView', params: { vehiculoClienteId: vehiculoCliente.id } }">{{
                vehiculoCliente.id
              }}</router-link>
            </td>
            <td>{{ vehiculoCliente.fecha }}</td>
            <td>{{ vehiculoCliente.numeroSerie }}</td>
            <td>{{ vehiculoCliente.color }}</td>
            <td>{{ vehiculoCliente.status }}</td>
            <td>{{ vehiculoCliente.createByUser }}</td>
            <td>{{ vehiculoCliente.createdOn ? $d(Date.parse(vehiculoCliente.createdOn), 'short') : '' }}</td>
            <td>{{ vehiculoCliente.modifyByUser }}</td>
            <td>{{ vehiculoCliente.modifiedOn ? $d(Date.parse(vehiculoCliente.modifiedOn), 'short') : '' }}</td>
            <td>{{ vehiculoCliente.auditStatus }}</td>
            <td>
              <div v-if="vehiculoCliente.cliente">
                <router-link :to="{ name: 'ClienteView', params: { clienteId: vehiculoCliente.cliente.id } }">{{
                  vehiculoCliente.cliente.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'VehiculoClienteView', params: { vehiculoClienteId: vehiculoCliente.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'VehiculoClienteEdit', params: { vehiculoClienteId: vehiculoCliente.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(vehiculoCliente)"
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
          id="segmaflotApp.vehiculoCliente.delete.question"
          data-cy="vehiculoClienteDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vehiculoCliente-heading" v-text="$t('segmaflotApp.vehiculoCliente.delete.question', { id: removeId })">
          Are you sure you want to delete this Vehiculo Cliente?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-vehiculoCliente"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVehiculoCliente()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./vehiculo-cliente.component.ts"></script>
