<template>
  <div>
    <h2 id="page-heading" data-cy="PedidoHeading">
      <span v-text="$t('segmaflotApp.pedido.home.title')" id="pedido-heading">Pedidos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.pedido.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PedidoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-pedido"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.pedido.home.createLabel')"> Create a new Pedido </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && pedidos && pedidos.length === 0">
      <span v-text="$t('segmaflotApp.pedido.home.notFound')">No pedidos found</span>
    </div>
    <div class="table-responsive" v-if="pedidos && pedidos.length > 0">
      <table class="table table-striped" aria-describedby="pedidos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pedido.empresa')">Empresa</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pedido in pedidos" :key="pedido.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: pedido.id } }">{{ pedido.id }}</router-link>
            </td>
            <td>{{ pedido.fecha }}</td>
            <td>{{ pedido.createByUser }}</td>
            <td>{{ pedido.createdOn ? $d(Date.parse(pedido.createdOn), 'short') : '' }}</td>
            <td>{{ pedido.modifyByUser }}</td>
            <td>{{ pedido.modifiedOn ? $d(Date.parse(pedido.modifiedOn), 'short') : '' }}</td>
            <td>{{ pedido.auditStatus }}</td>
            <td>
              <div v-if="pedido.empresa">
                <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: pedido.empresa.id } }">{{
                  pedido.empresa.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PedidoView', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PedidoEdit', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(pedido)"
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
        ><span id="segmaflotApp.pedido.delete.question" data-cy="pedidoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-pedido-heading" v-text="$t('segmaflotApp.pedido.delete.question', { id: removeId })">
          Are you sure you want to delete this Pedido?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-pedido"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePedido()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./pedido.component.ts"></script>
