<template>
  <div>
    <h2 id="page-heading" data-cy="PerdidaHeading">
      <span v-text="$t('segmaflotApp.perdida.home.title')" id="perdida-heading">Perdidas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.perdida.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PerdidaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-perdida"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.perdida.home.createLabel')"> Create a new Perdida </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && perdidas && perdidas.length === 0">
      <span v-text="$t('segmaflotApp.perdida.home.notFound')">No perdidas found</span>
    </div>
    <div class="table-responsive" v-if="perdidas && perdidas.length > 0">
      <table class="table table-striped" aria-describedby="perdidas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.perdida.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.perdida.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.perdida.observaciones')">Observaciones</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.perdida.inventario')">Inventario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="perdida in perdidas" :key="perdida.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PerdidaView', params: { perdidaId: perdida.id } }">{{ perdida.id }}</router-link>
            </td>
            <td>{{ perdida.fecha ? $d(Date.parse(perdida.fecha), 'short') : '' }}</td>
            <td>{{ perdida.cantidad }}</td>
            <td>
              <a
                v-if="perdida.observaciones"
                v-on:click="openFile(perdida.observacionesContentType, perdida.observaciones)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="perdida.observaciones">{{ perdida.observacionesContentType }}, {{ byteSize(perdida.observaciones) }}</span>
            </td>
            <td>
              <div v-if="perdida.inventario">
                <router-link :to="{ name: 'InventarioView', params: { inventarioId: perdida.inventario.id } }">{{
                  perdida.inventario.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PerdidaView', params: { perdidaId: perdida.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PerdidaEdit', params: { perdidaId: perdida.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(perdida)"
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
        ><span id="segmaflotApp.perdida.delete.question" data-cy="perdidaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-perdida-heading" v-text="$t('segmaflotApp.perdida.delete.question', { id: removeId })">
          Are you sure you want to delete this Perdida?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-perdida"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePerdida()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./perdida.component.ts"></script>
