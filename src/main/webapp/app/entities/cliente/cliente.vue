<template>
  <div>
    <h2 id="page-heading" data-cy="ClienteHeading">
      <span v-text="$t('segmaflotApp.cliente.home.title')" id="cliente-heading">Clientes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.cliente.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ClienteCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cliente"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.cliente.home.createLabel')"> Create a new Cliente </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clientes && clientes.length === 0">
      <span v-text="$t('segmaflotApp.cliente.home.notFound')">No clientes found</span>
    </div>
    <div class="table-responsive" v-if="clientes && clientes.length > 0">
      <table class="table table-striped" aria-describedby="clientes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.observaciones')">Observaciones</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cliente.persona')">Persona</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in clientes" :key="cliente.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ClienteView', params: { clienteId: cliente.id } }">{{ cliente.id }}</router-link>
            </td>
            <td>{{ cliente.fecha }}</td>
            <td>
              <a
                v-if="cliente.observaciones"
                v-on:click="openFile(cliente.observacionesContentType, cliente.observaciones)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="cliente.observaciones">{{ cliente.observacionesContentType }}, {{ byteSize(cliente.observaciones) }}</span>
            </td>
            <td>{{ cliente.createByUser }}</td>
            <td>{{ cliente.createdOn ? $d(Date.parse(cliente.createdOn), 'short') : '' }}</td>
            <td>{{ cliente.modifyByUser }}</td>
            <td>{{ cliente.modifiedOn ? $d(Date.parse(cliente.modifiedOn), 'short') : '' }}</td>
            <td>{{ cliente.auditStatus }}</td>
            <td>
              <div v-if="cliente.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: cliente.persona.id } }">{{ cliente.persona.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ClienteView', params: { clienteId: cliente.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ClienteEdit', params: { clienteId: cliente.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cliente)"
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
        ><span id="segmaflotApp.cliente.delete.question" data-cy="clienteDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cliente-heading" v-text="$t('segmaflotApp.cliente.delete.question', { id: removeId })">
          Are you sure you want to delete this Cliente?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cliente"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCliente()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cliente.component.ts"></script>
