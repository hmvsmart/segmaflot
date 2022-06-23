<template>
  <div>
    <h2 id="page-heading" data-cy="NivelHeading">
      <span v-text="$t('segmaflotApp.nivel.home.title')" id="nivel-heading">Nivels</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.nivel.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NivelCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-nivel"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.nivel.home.createLabel')"> Create a new Nivel </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && nivels && nivels.length === 0">
      <span v-text="$t('segmaflotApp.nivel.home.notFound')">No nivels found</span>
    </div>
    <div class="table-responsive" v-if="nivels && nivels.length > 0">
      <table class="table table-striped" aria-describedby="nivels">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.nivel.estante')">Estante</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="nivel in nivels" :key="nivel.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NivelView', params: { nivelId: nivel.id } }">{{ nivel.id }}</router-link>
            </td>
            <td>{{ nivel.nombre }}</td>
            <td>{{ nivel.createByUser }}</td>
            <td>{{ nivel.createdOn ? $d(Date.parse(nivel.createdOn), 'short') : '' }}</td>
            <td>{{ nivel.modifyByUser }}</td>
            <td>{{ nivel.modifiedOn ? $d(Date.parse(nivel.modifiedOn), 'short') : '' }}</td>
            <td>{{ nivel.auditStatus }}</td>
            <td>
              <div v-if="nivel.estante">
                <router-link :to="{ name: 'EstanteView', params: { estanteId: nivel.estante.id } }">{{ nivel.estante.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NivelView', params: { nivelId: nivel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NivelEdit', params: { nivelId: nivel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(nivel)"
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
        ><span id="segmaflotApp.nivel.delete.question" data-cy="nivelDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-nivel-heading" v-text="$t('segmaflotApp.nivel.delete.question', { id: removeId })">
          Are you sure you want to delete this Nivel?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-nivel"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNivel()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./nivel.component.ts"></script>
