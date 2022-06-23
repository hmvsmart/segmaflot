<template>
  <div>
    <h2 id="page-heading" data-cy="ResurtirHeading">
      <span v-text="$t('segmaflotApp.resurtir.home.title')" id="resurtir-heading">Resurtirs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.resurtir.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ResurtirCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-resurtir"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.resurtir.home.createLabel')"> Create a new Resurtir </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && resurtirs && resurtirs.length === 0">
      <span v-text="$t('segmaflotApp.resurtir.home.notFound')">No resurtirs found</span>
    </div>
    <div class="table-responsive" v-if="resurtirs && resurtirs.length > 0">
      <table class="table table-striped" aria-describedby="resurtirs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.total')">Total</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.resurtir.inventario')">Inventario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="resurtir in resurtirs" :key="resurtir.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ResurtirView', params: { resurtirId: resurtir.id } }">{{ resurtir.id }}</router-link>
            </td>
            <td>{{ resurtir.fecha ? $d(Date.parse(resurtir.fecha), 'short') : '' }}</td>
            <td>{{ resurtir.total }}</td>
            <td>{{ resurtir.createByUser }}</td>
            <td>{{ resurtir.createdOn ? $d(Date.parse(resurtir.createdOn), 'short') : '' }}</td>
            <td>{{ resurtir.modifyByUser }}</td>
            <td>{{ resurtir.modifiedOn ? $d(Date.parse(resurtir.modifiedOn), 'short') : '' }}</td>
            <td>{{ resurtir.auditStatus }}</td>
            <td>
              <div v-if="resurtir.inventario">
                <router-link :to="{ name: 'InventarioView', params: { inventarioId: resurtir.inventario.id } }">{{
                  resurtir.inventario.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ResurtirView', params: { resurtirId: resurtir.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ResurtirEdit', params: { resurtirId: resurtir.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(resurtir)"
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
        ><span id="segmaflotApp.resurtir.delete.question" data-cy="resurtirDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-resurtir-heading" v-text="$t('segmaflotApp.resurtir.delete.question', { id: removeId })">
          Are you sure you want to delete this Resurtir?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-resurtir"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeResurtir()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./resurtir.component.ts"></script>
