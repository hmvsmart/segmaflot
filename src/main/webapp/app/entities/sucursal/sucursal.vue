<template>
  <div>
    <h2 id="page-heading" data-cy="SucursalHeading">
      <span v-text="$t('segmaflotApp.sucursal.home.title')" id="sucursal-heading">Sucursals</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.sucursal.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SucursalCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-sucursal"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.sucursal.home.createLabel')"> Create a new Sucursal </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && sucursals && sucursals.length === 0">
      <span v-text="$t('segmaflotApp.sucursal.home.notFound')">No sucursals found</span>
    </div>
    <div class="table-responsive" v-if="sucursals && sucursals.length > 0">
      <table class="table table-striped" aria-describedby="sucursals">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.nombre')">Nombre</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.tipoSucursal')">Tipo Sucursal</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.telefono')">Telefono</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.sucursal.empresa')">Empresa</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sucursal in sucursals" :key="sucursal.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SucursalView', params: { sucursalId: sucursal.id } }">{{ sucursal.id }}</router-link>
            </td>
            <td>{{ sucursal.nombre }}</td>
            <td>{{ sucursal.tipoSucursal }}</td>
            <td>{{ sucursal.telefono }}</td>
            <td>{{ sucursal.status }}</td>
            <td>{{ sucursal.createByUser }}</td>
            <td>{{ sucursal.createdOn ? $d(Date.parse(sucursal.createdOn), 'short') : '' }}</td>
            <td>{{ sucursal.modifyByUser }}</td>
            <td>{{ sucursal.modifiedOn ? $d(Date.parse(sucursal.modifiedOn), 'short') : '' }}</td>
            <td>{{ sucursal.auditStatus }}</td>
            <td>
              <div v-if="sucursal.empresa">
                <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: sucursal.empresa.id } }">{{
                  sucursal.empresa.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SucursalView', params: { sucursalId: sucursal.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SucursalEdit', params: { sucursalId: sucursal.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(sucursal)"
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
        ><span id="segmaflotApp.sucursal.delete.question" data-cy="sucursalDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-sucursal-heading" v-text="$t('segmaflotApp.sucursal.delete.question', { id: removeId })">
          Are you sure you want to delete this Sucursal?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-sucursal"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSucursal()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./sucursal.component.ts"></script>
