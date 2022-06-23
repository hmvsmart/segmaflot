<template>
  <div>
    <h2 id="page-heading" data-cy="InventarioHeading">
      <span v-text="$t('segmaflotApp.inventario.home.title')" id="inventario-heading">Inventarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.inventario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'InventarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-inventario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.inventario.home.createLabel')"> Create a new Inventario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && inventarios && inventarios.length === 0">
      <span v-text="$t('segmaflotApp.inventario.home.notFound')">No inventarios found</span>
    </div>
    <div class="table-responsive" v-if="inventarios && inventarios.length > 0">
      <table class="table table-striped" aria-describedby="inventarios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.cantidadMinimaEst')">Cantidad Minima Est</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.cantidadMaximaEst')">Cantidad Maxima Est</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.producto')">Producto</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.inventario.sucursal')">Sucursal</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="inventario in inventarios" :key="inventario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: inventario.id } }">{{ inventario.id }}</router-link>
            </td>
            <td>{{ inventario.cantidad }}</td>
            <td>{{ inventario.cantidadMinimaEst }}</td>
            <td>{{ inventario.cantidadMaximaEst }}</td>
            <td>{{ inventario.status }}</td>
            <td>{{ inventario.createByUser }}</td>
            <td>{{ inventario.createdOn ? $d(Date.parse(inventario.createdOn), 'short') : '' }}</td>
            <td>{{ inventario.modifyByUser }}</td>
            <td>{{ inventario.modifiedOn ? $d(Date.parse(inventario.modifiedOn), 'short') : '' }}</td>
            <td>{{ inventario.auditStatus }}</td>
            <td>
              <div v-if="inventario.producto">
                <router-link :to="{ name: 'ProductoView', params: { productoId: inventario.producto.id } }">{{
                  inventario.producto.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="inventario.sucursal">
                <router-link :to="{ name: 'SucursalView', params: { sucursalId: inventario.sucursal.id } }">{{
                  inventario.sucursal.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'InventarioView', params: { inventarioId: inventario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'InventarioEdit', params: { inventarioId: inventario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(inventario)"
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
        ><span id="segmaflotApp.inventario.delete.question" data-cy="inventarioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-inventario-heading" v-text="$t('segmaflotApp.inventario.delete.question', { id: removeId })">
          Are you sure you want to delete this Inventario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-inventario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeInventario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./inventario.component.ts"></script>
