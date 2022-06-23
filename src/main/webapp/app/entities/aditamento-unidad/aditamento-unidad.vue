<template>
  <div>
    <h2 id="page-heading" data-cy="AditamentoUnidadHeading">
      <span v-text="$t('segmaflotApp.aditamentoUnidad.home.title')" id="aditamento-unidad-heading">Aditamento Unidads</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.aditamentoUnidad.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AditamentoUnidadCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-aditamento-unidad"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.home.createLabel')"> Create a new Aditamento Unidad </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && aditamentoUnidads && aditamentoUnidads.length === 0">
      <span v-text="$t('segmaflotApp.aditamentoUnidad.home.notFound')">No aditamentoUnidads found</span>
    </div>
    <div class="table-responsive" v-if="aditamentoUnidads && aditamentoUnidads.length > 0">
      <table class="table table-striped" aria-describedby="aditamentoUnidads">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.numeroSerie')">Numero Serie</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.aditamento')">Aditamento</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.aditamentoUnidad.unidad')">Unidad</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="aditamentoUnidad in aditamentoUnidads" :key="aditamentoUnidad.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AditamentoUnidadView', params: { aditamentoUnidadId: aditamentoUnidad.id } }">{{
                aditamentoUnidad.id
              }}</router-link>
            </td>
            <td>{{ aditamentoUnidad.fecha }}</td>
            <td>{{ aditamentoUnidad.numeroSerie }}</td>
            <td>{{ aditamentoUnidad.createByUser }}</td>
            <td>{{ aditamentoUnidad.createdOn ? $d(Date.parse(aditamentoUnidad.createdOn), 'short') : '' }}</td>
            <td>{{ aditamentoUnidad.modifyByUser }}</td>
            <td>{{ aditamentoUnidad.modifiedOn ? $d(Date.parse(aditamentoUnidad.modifiedOn), 'short') : '' }}</td>
            <td>{{ aditamentoUnidad.auditStatus }}</td>
            <td>
              <div v-if="aditamentoUnidad.aditamento">
                <router-link :to="{ name: 'AditamentoView', params: { aditamentoId: aditamentoUnidad.aditamento.id } }">{{
                  aditamentoUnidad.aditamento.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="aditamentoUnidad.unidad">
                <router-link :to="{ name: 'UnidadView', params: { unidadId: aditamentoUnidad.unidad.id } }">{{
                  aditamentoUnidad.unidad.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AditamentoUnidadView', params: { aditamentoUnidadId: aditamentoUnidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AditamentoUnidadEdit', params: { aditamentoUnidadId: aditamentoUnidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(aditamentoUnidad)"
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
          id="segmaflotApp.aditamentoUnidad.delete.question"
          data-cy="aditamentoUnidadDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-aditamentoUnidad-heading" v-text="$t('segmaflotApp.aditamentoUnidad.delete.question', { id: removeId })">
          Are you sure you want to delete this Aditamento Unidad?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-aditamentoUnidad"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAditamentoUnidad()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./aditamento-unidad.component.ts"></script>
