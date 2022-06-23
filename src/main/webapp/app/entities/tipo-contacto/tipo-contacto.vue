<template>
  <div>
    <h2 id="page-heading" data-cy="TipoContactoHeading">
      <span v-text="$t('segmaflotApp.tipoContacto.home.title')" id="tipo-contacto-heading">Tipo Contactos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.tipoContacto.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TipoContactoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tipo-contacto"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.tipoContacto.home.createLabel')"> Create a new Tipo Contacto </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tipoContactos && tipoContactos.length === 0">
      <span v-text="$t('segmaflotApp.tipoContacto.home.notFound')">No tipoContactos found</span>
    </div>
    <div class="table-responsive" v-if="tipoContactos && tipoContactos.length > 0">
      <table class="table table-striped" aria-describedby="tipoContactos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.tipoContacto.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoContacto in tipoContactos" :key="tipoContacto.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TipoContactoView', params: { tipoContactoId: tipoContacto.id } }">{{
                tipoContacto.id
              }}</router-link>
            </td>
            <td>{{ tipoContacto.nombre }}</td>
            <td>{{ tipoContacto.createByUser }}</td>
            <td>{{ tipoContacto.createdOn ? $d(Date.parse(tipoContacto.createdOn), 'short') : '' }}</td>
            <td>{{ tipoContacto.modifyByUser }}</td>
            <td>{{ tipoContacto.modifiedOn ? $d(Date.parse(tipoContacto.modifiedOn), 'short') : '' }}</td>
            <td>{{ tipoContacto.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TipoContactoView', params: { tipoContactoId: tipoContacto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TipoContactoEdit', params: { tipoContactoId: tipoContacto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tipoContacto)"
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
        ><span id="segmaflotApp.tipoContacto.delete.question" data-cy="tipoContactoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tipoContacto-heading" v-text="$t('segmaflotApp.tipoContacto.delete.question', { id: removeId })">
          Are you sure you want to delete this Tipo Contacto?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tipoContacto"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTipoContacto()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./tipo-contacto.component.ts"></script>
