<template>
  <div>
    <h2 id="page-heading" data-cy="PersonaMoralHeading">
      <span v-text="$t('segmaflotApp.personaMoral.home.title')" id="persona-moral-heading">Persona Morals</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.personaMoral.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PersonaMoralCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-persona-moral"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.personaMoral.home.createLabel')"> Create a new Persona Moral </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && personaMorals && personaMorals.length === 0">
      <span v-text="$t('segmaflotApp.personaMoral.home.notFound')">No personaMorals found</span>
    </div>
    <div class="table-responsive" v-if="personaMorals && personaMorals.length > 0">
      <table class="table table-striped" aria-describedby="personaMorals">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.razonSocial')">Razon Social</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.rfc')">Rfc</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.personaMoral.persona')">Persona</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="personaMoral in personaMorals" :key="personaMoral.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: personaMoral.id } }">{{
                personaMoral.id
              }}</router-link>
            </td>
            <td>{{ personaMoral.razonSocial }}</td>
            <td>{{ personaMoral.rfc }}</td>
            <td>{{ personaMoral.createByUser }}</td>
            <td>{{ personaMoral.createdOn ? $d(Date.parse(personaMoral.createdOn), 'short') : '' }}</td>
            <td>{{ personaMoral.modifyByUser }}</td>
            <td>{{ personaMoral.modifiedOn ? $d(Date.parse(personaMoral.modifiedOn), 'short') : '' }}</td>
            <td>{{ personaMoral.auditStatus }}</td>
            <td>
              <div v-if="personaMoral.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: personaMoral.persona.id } }">{{
                  personaMoral.persona.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: personaMoral.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PersonaMoralEdit', params: { personaMoralId: personaMoral.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(personaMoral)"
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
        ><span id="segmaflotApp.personaMoral.delete.question" data-cy="personaMoralDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-personaMoral-heading" v-text="$t('segmaflotApp.personaMoral.delete.question', { id: removeId })">
          Are you sure you want to delete this Persona Moral?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-personaMoral"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePersonaMoral()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./persona-moral.component.ts"></script>
