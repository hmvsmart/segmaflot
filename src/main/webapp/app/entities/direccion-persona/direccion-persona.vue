<template>
  <div>
    <h2 id="page-heading" data-cy="DireccionPersonaHeading">
      <span v-text="$t('segmaflotApp.direccionPersona.home.title')" id="direccion-persona-heading">Direccion Personas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.direccionPersona.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DireccionPersonaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-direccion-persona"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.direccionPersona.home.createLabel')"> Create a new Direccion Persona </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && direccionPersonas && direccionPersonas.length === 0">
      <span v-text="$t('segmaflotApp.direccionPersona.home.notFound')">No direccionPersonas found</span>
    </div>
    <div class="table-responsive" v-if="direccionPersonas && direccionPersonas.length > 0">
      <table class="table table-striped" aria-describedby="direccionPersonas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.direccion')">Direccion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.direccionPersona.persona')">Persona</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="direccionPersona in direccionPersonas" :key="direccionPersona.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DireccionPersonaView', params: { direccionPersonaId: direccionPersona.id } }">{{
                direccionPersona.id
              }}</router-link>
            </td>
            <td>{{ direccionPersona.fecha }}</td>
            <td>{{ direccionPersona.status }}</td>
            <td>{{ direccionPersona.createByUser }}</td>
            <td>{{ direccionPersona.createdOn ? $d(Date.parse(direccionPersona.createdOn), 'short') : '' }}</td>
            <td>{{ direccionPersona.modifyByUser }}</td>
            <td>{{ direccionPersona.modifiedOn ? $d(Date.parse(direccionPersona.modifiedOn), 'short') : '' }}</td>
            <td>{{ direccionPersona.auditStatus }}</td>
            <td>
              <div v-if="direccionPersona.direccion">
                <router-link :to="{ name: 'DireccionView', params: { direccionId: direccionPersona.direccion.id } }">{{
                  direccionPersona.direccion.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="direccionPersona.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: direccionPersona.persona.id } }">{{
                  direccionPersona.persona.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DireccionPersonaView', params: { direccionPersonaId: direccionPersona.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'DireccionPersonaEdit', params: { direccionPersonaId: direccionPersona.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(direccionPersona)"
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
        ><span
          id="segmaflotApp.direccionPersona.delete.question"
          data-cy="direccionPersonaDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-direccionPersona-heading" v-text="$t('segmaflotApp.direccionPersona.delete.question', { id: removeId })">
          Are you sure you want to delete this Direccion Persona?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-direccionPersona"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDireccionPersona()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./direccion-persona.component.ts"></script>
