<template>
  <div>
    <h2 id="page-heading" data-cy="SeccionHeading">
      <span v-text="$t('segmaflotApp.seccion.home.title')" id="seccion-heading">Seccions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.seccion.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SeccionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-seccion"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.seccion.home.createLabel')"> Create a new Seccion </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && seccions && seccions.length === 0">
      <span v-text="$t('segmaflotApp.seccion.home.notFound')">No seccions found</span>
    </div>
    <div class="table-responsive" v-if="seccions && seccions.length > 0">
      <table class="table table-striped" aria-describedby="seccions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.seccion.nivel')">Nivel</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="seccion in seccions" :key="seccion.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SeccionView', params: { seccionId: seccion.id } }">{{ seccion.id }}</router-link>
            </td>
            <td>{{ seccion.nombre }}</td>
            <td>{{ seccion.createByUser }}</td>
            <td>{{ seccion.createdOn ? $d(Date.parse(seccion.createdOn), 'short') : '' }}</td>
            <td>{{ seccion.modifyByUser }}</td>
            <td>{{ seccion.modifiedOn ? $d(Date.parse(seccion.modifiedOn), 'short') : '' }}</td>
            <td>{{ seccion.auditStatus }}</td>
            <td>
              <div v-if="seccion.nivel">
                <router-link :to="{ name: 'NivelView', params: { nivelId: seccion.nivel.id } }">{{ seccion.nivel.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SeccionView', params: { seccionId: seccion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SeccionEdit', params: { seccionId: seccion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(seccion)"
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
        ><span id="segmaflotApp.seccion.delete.question" data-cy="seccionDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-seccion-heading" v-text="$t('segmaflotApp.seccion.delete.question', { id: removeId })">
          Are you sure you want to delete this Seccion?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-seccion"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSeccion()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./seccion.component.ts"></script>
