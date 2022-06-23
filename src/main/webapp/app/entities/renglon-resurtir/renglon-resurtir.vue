<template>
  <div>
    <h2 id="page-heading" data-cy="RenglonResurtirHeading">
      <span v-text="$t('segmaflotApp.renglonResurtir.home.title')" id="renglon-resurtir-heading">Renglon Resurtirs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.renglonResurtir.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RenglonResurtirCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-renglon-resurtir"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.renglonResurtir.home.createLabel')"> Create a new Renglon Resurtir </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && renglonResurtirs && renglonResurtirs.length === 0">
      <span v-text="$t('segmaflotApp.renglonResurtir.home.notFound')">No renglonResurtirs found</span>
    </div>
    <div class="table-responsive" v-if="renglonResurtirs && renglonResurtirs.length > 0">
      <table class="table table-striped" aria-describedby="renglonResurtirs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.fechaCaducidad')">Fecha Caducidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.precioCompra')">Precio Compra</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.renglonResurtir.resurtir')">Resurtir</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="renglonResurtir in renglonResurtirs" :key="renglonResurtir.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RenglonResurtirView', params: { renglonResurtirId: renglonResurtir.id } }">{{
                renglonResurtir.id
              }}</router-link>
            </td>
            <td>{{ renglonResurtir.fechaCaducidad }}</td>
            <td>{{ renglonResurtir.cantidad }}</td>
            <td>{{ renglonResurtir.precioCompra }}</td>
            <td>{{ renglonResurtir.createByUser }}</td>
            <td>{{ renglonResurtir.createdOn ? $d(Date.parse(renglonResurtir.createdOn), 'short') : '' }}</td>
            <td>{{ renglonResurtir.modifyByUser }}</td>
            <td>{{ renglonResurtir.modifiedOn ? $d(Date.parse(renglonResurtir.modifiedOn), 'short') : '' }}</td>
            <td>{{ renglonResurtir.auditStatus }}</td>
            <td>
              <div v-if="renglonResurtir.resurtir">
                <router-link :to="{ name: 'ResurtirView', params: { resurtirId: renglonResurtir.resurtir.id } }">{{
                  renglonResurtir.resurtir.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RenglonResurtirView', params: { renglonResurtirId: renglonResurtir.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RenglonResurtirEdit', params: { renglonResurtirId: renglonResurtir.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(renglonResurtir)"
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
          id="segmaflotApp.renglonResurtir.delete.question"
          data-cy="renglonResurtirDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-renglonResurtir-heading" v-text="$t('segmaflotApp.renglonResurtir.delete.question', { id: removeId })">
          Are you sure you want to delete this Renglon Resurtir?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-renglonResurtir"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRenglonResurtir()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./renglon-resurtir.component.ts"></script>
