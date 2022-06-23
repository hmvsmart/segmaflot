<template>
  <div>
    <h2 id="page-heading" data-cy="PrecioVentaHeading">
      <span v-text="$t('segmaflotApp.precioVenta.home.title')" id="precio-venta-heading">Precio Ventas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.precioVenta.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PrecioVentaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-precio-venta"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.precioVenta.home.createLabel')"> Create a new Precio Venta </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && precioVentas && precioVentas.length === 0">
      <span v-text="$t('segmaflotApp.precioVenta.home.notFound')">No precioVentas found</span>
    </div>
    <div class="table-responsive" v-if="precioVentas && precioVentas.length > 0">
      <table class="table table-striped" aria-describedby="precioVentas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.ppu')">Ppu</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioVenta.inventario')">Inventario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="precioVenta in precioVentas" :key="precioVenta.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PrecioVentaView', params: { precioVentaId: precioVenta.id } }">{{ precioVenta.id }}</router-link>
            </td>
            <td>{{ precioVenta.fecha }}</td>
            <td>{{ precioVenta.ppu }}</td>
            <td>{{ precioVenta.createByUser }}</td>
            <td>{{ precioVenta.createdOn ? $d(Date.parse(precioVenta.createdOn), 'short') : '' }}</td>
            <td>{{ precioVenta.modifyByUser }}</td>
            <td>{{ precioVenta.modifiedOn ? $d(Date.parse(precioVenta.modifiedOn), 'short') : '' }}</td>
            <td>{{ precioVenta.auditStatus }}</td>
            <td>
              <div v-if="precioVenta.inventario">
                <router-link :to="{ name: 'InventarioView', params: { inventarioId: precioVenta.inventario.id } }">{{
                  precioVenta.inventario.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PrecioVentaView', params: { precioVentaId: precioVenta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PrecioVentaEdit', params: { precioVentaId: precioVenta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(precioVenta)"
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
        ><span id="segmaflotApp.precioVenta.delete.question" data-cy="precioVentaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-precioVenta-heading" v-text="$t('segmaflotApp.precioVenta.delete.question', { id: removeId })">
          Are you sure you want to delete this Precio Venta?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-precioVenta"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePrecioVenta()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./precio-venta.component.ts"></script>
