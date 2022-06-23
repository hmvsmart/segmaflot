<template>
  <div>
    <h2 id="page-heading" data-cy="CPHeading">
      <span v-text="$t('segmaflotApp.cP.home.title')" id="cp-heading">CPS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.cP.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CPCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-cp">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.cP.home.createLabel')"> Create a new CP </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cPS && cPS.length === 0">
      <span v-text="$t('segmaflotApp.cP.home.notFound')">No cPS found</span>
    </div>
    <div class="table-responsive" v-if="cPS && cPS.length > 0">
      <table class="table table-striped" aria-describedby="cPS">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cP.cp')">Cp</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cP.ciudad')">Ciudad</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cP in cPS" :key="cP.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CPView', params: { cPId: cP.id } }">{{ cP.id }}</router-link>
            </td>
            <td>{{ cP.cp }}</td>
            <td>
              <div v-if="cP.ciudad">
                <router-link :to="{ name: 'CiudadView', params: { ciudadId: cP.ciudad.id } }">{{ cP.ciudad.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CPView', params: { cPId: cP.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CPEdit', params: { cPId: cP.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cP)"
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
        ><span id="segmaflotApp.cP.delete.question" data-cy="cPDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cP-heading" v-text="$t('segmaflotApp.cP.delete.question', { id: removeId })">
          Are you sure you want to delete this CP?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cP"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCP()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cp.component.ts"></script>
