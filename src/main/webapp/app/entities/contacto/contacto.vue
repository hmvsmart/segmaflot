<template>
  <div>
    <h2 id="page-heading" data-cy="ContactoHeading">
      <span v-text="$t('segmaflotApp.contacto.home.title')" id="contacto-heading">Contactos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.contacto.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ContactoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-contacto"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.contacto.home.createLabel')"> Create a new Contacto </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && contactos && contactos.length === 0">
      <span v-text="$t('segmaflotApp.contacto.home.notFound')">No contactos found</span>
    </div>
    <div class="table-responsive" v-if="contactos && contactos.length > 0">
      <table class="table table-striped" aria-describedby="contactos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.valor')">Valor</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.etiqueta')">Etiqueta</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.string')">String</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.tipoContacto')">Tipo Contacto</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.contacto.persona')">Persona</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="contacto in contactos" :key="contacto.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ContactoView', params: { contactoId: contacto.id } }">{{ contacto.id }}</router-link>
            </td>
            <td>{{ contacto.valor }}</td>
            <td>{{ contacto.etiqueta }}</td>
            <td>{{ contacto.string }}</td>
            <td>{{ contacto.createByUser }}</td>
            <td>{{ contacto.createdOn ? $d(Date.parse(contacto.createdOn), 'short') : '' }}</td>
            <td>{{ contacto.modifyByUser }}</td>
            <td>{{ contacto.modifiedOn ? $d(Date.parse(contacto.modifiedOn), 'short') : '' }}</td>
            <td>{{ contacto.auditStatus }}</td>
            <td>
              <div v-if="contacto.tipoContacto">
                <router-link :to="{ name: 'TipoContactoView', params: { tipoContactoId: contacto.tipoContacto.id } }">{{
                  contacto.tipoContacto.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="contacto.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: contacto.persona.id } }">{{
                  contacto.persona.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ContactoView', params: { contactoId: contacto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ContactoEdit', params: { contactoId: contacto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(contacto)"
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
        ><span id="segmaflotApp.contacto.delete.question" data-cy="contactoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-contacto-heading" v-text="$t('segmaflotApp.contacto.delete.question', { id: removeId })">
          Are you sure you want to delete this Contacto?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-contacto"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeContacto()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./contacto.component.ts"></script>
