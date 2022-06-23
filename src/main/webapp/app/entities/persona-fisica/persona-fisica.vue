<template>
  <div>
    <h2 id="page-heading" data-cy="PersonaFisicaHeading">
      <span v-text="$t('segmaflotApp.personaFisica.home.title')" id="persona-fisica-heading">Persona Fisicas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.personaFisica.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PersonaFisicaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-persona-fisica"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.personaFisica.home.createLabel')"> Create a new Persona Fisica </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && personaFisicas && personaFisicas.length === 0">
      <span v-text="$t('segmaflotApp.personaFisica.home.notFound')">No personaFisicas found</span>
    </div>
    <div class="table-responsive" v-if="personaFisicas && personaFisicas.length > 0">
      <table class="table table-striped" aria-describedby="personaFisicas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.apaterno')">Apaterno</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.amaterno')">Amaterno</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.fechaNacimiento')">Fecha Nacimiento</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.curp')">Curp</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaFisica.persona')">Persona</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="personaFisica in personaFisicas" :key="personaFisica.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PersonaFisicaView', params: { personaFisicaId: personaFisica.id } }">{{
                personaFisica.id
              }}</router-link>
            </td>
            <td>{{ personaFisica.nombre }}</td>
            <td>{{ personaFisica.apaterno }}</td>
            <td>{{ personaFisica.amaterno }}</td>
            <td>{{ personaFisica.fechaNacimiento }}</td>
            <td>{{ personaFisica.curp }}</td>
            <td>{{ personaFisica.createByUser }}</td>
            <td>{{ personaFisica.createdOn ? $d(Date.parse(personaFisica.createdOn), 'short') : '' }}</td>
            <td>{{ personaFisica.modifyByUser }}</td>
            <td>{{ personaFisica.modifiedOn ? $d(Date.parse(personaFisica.modifiedOn), 'short') : '' }}</td>
            <td>{{ personaFisica.auditStatus }}</td>
            <td>
              <div v-if="personaFisica.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: personaFisica.persona.id } }">{{
                  personaFisica.persona.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PersonaFisicaView', params: { personaFisicaId: personaFisica.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PersonaFisicaEdit', params: { personaFisicaId: personaFisica.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(personaFisica)"
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
        ><span id="segmaflotApp.personaFisica.delete.question" data-cy="personaFisicaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-personaFisica-heading" v-text="$t('segmaflotApp.personaFisica.delete.question', { id: removeId })">
          Are you sure you want to delete this Persona Fisica?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-personaFisica"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePersonaFisica()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./persona-fisica.component.ts"></script>
