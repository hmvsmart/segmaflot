<template>
  <div>
    <h2 id="page-heading" data-cy="RenglonVentaHeading">
      <span v-text="$t('segmaflotApp.renglonVenta.home.title')" id="renglon-venta-heading">Renglon Ventas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.renglonVenta.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RenglonVentaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-renglon-venta"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.renglonVenta.home.createLabel')"> Create a new Renglon Venta </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && renglonVentas && renglonVentas.length === 0">
      <span v-text="$t('segmaflotApp.renglonVenta.home.notFound')">No renglonVentas found</span>
    </div>
    <div class="table-responsive" v-if="renglonVentas && renglonVentas.length > 0">
      <table class="table table-striped" aria-describedby="renglonVentas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.venta')">Venta</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonVenta.precioventa')">Precioventa</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="renglonVenta in renglonVentas" :key="renglonVenta.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RenglonVentaView', params: { renglonVentaId: renglonVenta.id } }">{{
                renglonVenta.id
              }}</router-link>
            </td>
            <td>{{ renglonVenta.cantidad }}</td>
            <td>{{ renglonVenta.createByUser }}</td>
            <td>{{ renglonVenta.createdOn ? $d(Date.parse(renglonVenta.createdOn), 'short') : '' }}</td>
            <td>{{ renglonVenta.modifyByUser }}</td>
            <td>{{ renglonVenta.modifiedOn ? $d(Date.parse(renglonVenta.modifiedOn), 'short') : '' }}</td>
            <td>{{ renglonVenta.auditStatus }}</td>
            <td>
              <div v-if="renglonVenta.venta">
                <router-link :to="{ name: 'VentaView', params: { ventaId: renglonVenta.venta.id } }">{{
                  renglonVenta.venta.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="renglonVenta.precioventa">
                <router-link :to="{ name: 'PrecioVentaView', params: { precioVentaId: renglonVenta.precioventa.id } }">{{
                  renglonVenta.precioventa.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RenglonVentaView', params: { renglonVentaId: renglonVenta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RenglonVentaEdit', params: { renglonVentaId: renglonVenta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(renglonVenta)"
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
        ><span id="segmaflotApp.renglonVenta.delete.question" data-cy="renglonVentaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-renglonVenta-heading" v-text="$t('segmaflotApp.renglonVenta.delete.question', { id: removeId })">
          Are you sure you want to delete this Renglon Venta?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-renglonVenta"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRenglonVenta()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./renglon-venta.component.ts"></script>
