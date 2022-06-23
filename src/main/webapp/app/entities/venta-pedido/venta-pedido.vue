<template>
  <div>
    <h2 id="page-heading" data-cy="VentaPedidoHeading">
      <span v-text="$t('segmaflotApp.ventaPedido.home.title')" id="venta-pedido-heading">Venta Pedidos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.ventaPedido.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VentaPedidoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-venta-pedido"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.ventaPedido.home.createLabel')"> Create a new Venta Pedido </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ventaPedidos && ventaPedidos.length === 0">
      <span v-text="$t('segmaflotApp.ventaPedido.home.notFound')">No ventaPedidos found</span>
    </div>
    <div class="table-responsive" v-if="ventaPedidos && ventaPedidos.length > 0">
      <table class="table table-striped" aria-describedby="ventaPedidos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ventaPedido.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ventaPedido.venta')">Venta</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ventaPedido.pedido')">Pedido</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ventaPedido in ventaPedidos" :key="ventaPedido.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VentaPedidoView', params: { ventaPedidoId: ventaPedido.id } }">{{ ventaPedido.id }}</router-link>
            </td>
            <td>{{ ventaPedido.fecha }}</td>
            <td>
              <div v-if="ventaPedido.venta">
                <router-link :to="{ name: 'VentaView', params: { ventaId: ventaPedido.venta.id } }">{{ ventaPedido.venta.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="ventaPedido.pedido">
                <router-link :to="{ name: 'PedidoView', params: { pedidoId: ventaPedido.pedido.id } }">{{
                  ventaPedido.pedido.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VentaPedidoView', params: { ventaPedidoId: ventaPedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VentaPedidoEdit', params: { ventaPedidoId: ventaPedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ventaPedido)"
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
        ><span id="segmaflotApp.ventaPedido.delete.question" data-cy="ventaPedidoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ventaPedido-heading" v-text="$t('segmaflotApp.ventaPedido.delete.question', { id: removeId })">
          Are you sure you want to delete this Venta Pedido?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ventaPedido"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVentaPedido()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./venta-pedido.component.ts"></script>
