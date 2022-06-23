<template>
  <div>
    <h2 id="page-heading" data-cy="VentaHeading">
      <span v-text="$t('segmaflotApp.venta.home.title')" id="venta-heading">Ventas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.venta.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VentaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-venta"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.venta.home.createLabel')"> Create a new Venta </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ventas && ventas.length === 0">
      <span v-text="$t('segmaflotApp.venta.home.notFound')">No ventas found</span>
    </div>
    <div class="table-responsive" v-if="ventas && ventas.length > 0">
      <table class="table table-striped" aria-describedby="ventas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.total')">Total</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.venta.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="venta in ventas" :key="venta.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VentaView', params: { ventaId: venta.id } }">{{ venta.id }}</router-link>
            </td>
            <td>{{ venta.fecha ? $d(Date.parse(venta.fecha), 'short') : '' }}</td>
            <td>{{ venta.total }}</td>
            <td>{{ venta.createByUser }}</td>
            <td>{{ venta.createdOn ? $d(Date.parse(venta.createdOn), 'short') : '' }}</td>
            <td>{{ venta.modifyByUser }}</td>
            <td>{{ venta.modifiedOn ? $d(Date.parse(venta.modifiedOn), 'short') : '' }}</td>
            <td>{{ venta.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VentaView', params: { ventaId: venta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VentaEdit', params: { ventaId: venta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(venta)"
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
        ><span id="segmaflotApp.venta.delete.question" data-cy="ventaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-venta-heading" v-text="$t('segmaflotApp.venta.delete.question', { id: removeId })">
          Are you sure you want to delete this Venta?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-venta"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVenta()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./venta.component.ts"></script>
