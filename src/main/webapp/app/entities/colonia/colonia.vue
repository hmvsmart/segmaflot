<template>
  <div>
    <h2 id="page-heading" data-cy="ColoniaHeading">
      <span v-text="$t('segmaflotApp.colonia.home.title')" id="colonia-heading">Colonias</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.colonia.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ColoniaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-colonia"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.colonia.home.createLabel')"> Create a new Colonia </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && colonias && colonias.length === 0">
      <span v-text="$t('segmaflotApp.colonia.home.notFound')">No colonias found</span>
    </div>
    <div class="table-responsive" v-if="colonias && colonias.length > 0">
      <table class="table table-striped" aria-describedby="colonias">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.colonia.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.colonia.cp')">Cp</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="colonia in colonias" :key="colonia.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ColoniaView', params: { coloniaId: colonia.id } }">{{ colonia.id }}</router-link>
            </td>
            <td>{{ colonia.nombre }}</td>
            <td>
              <div v-if="colonia.cp">
                <router-link :to="{ name: 'CPView', params: { cPId: colonia.cp.id } }">{{ colonia.cp.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ColoniaView', params: { coloniaId: colonia.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ColoniaEdit', params: { coloniaId: colonia.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(colonia)"
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
        ><span id="segmaflotApp.colonia.delete.question" data-cy="coloniaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-colonia-heading" v-text="$t('segmaflotApp.colonia.delete.question', { id: removeId })">
          Are you sure you want to delete this Colonia?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-colonia"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeColonia()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./colonia.component.ts"></script>
