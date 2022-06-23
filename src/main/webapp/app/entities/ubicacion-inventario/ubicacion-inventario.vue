<template>
  <div>
    <h2 id="page-heading" data-cy="UbicacionInventarioHeading">
      <span v-text="$t('segmaflotApp.ubicacionInventario.home.title')" id="ubicacion-inventario-heading">Ubicacion Inventarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.ubicacionInventario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UbicacionInventarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ubicacion-inventario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.ubicacionInventario.home.createLabel')"> Create a new Ubicacion Inventario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ubicacionInventarios && ubicacionInventarios.length === 0">
      <span v-text="$t('segmaflotApp.ubicacionInventario.home.notFound')">No ubicacionInventarios found</span>
    </div>
    <div class="table-responsive" v-if="ubicacionInventarios && ubicacionInventarios.length > 0">
      <table class="table table-striped" aria-describedby="ubicacionInventarios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.seccion')">Seccion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.ubicacionInventario.inventario')">Inventario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ubicacionInventario in ubicacionInventarios" :key="ubicacionInventario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UbicacionInventarioView', params: { ubicacionInventarioId: ubicacionInventario.id } }">{{
                ubicacionInventario.id
              }}</router-link>
            </td>
            <td>{{ ubicacionInventario.status }}</td>
            <td>{{ ubicacionInventario.createByUser }}</td>
            <td>{{ ubicacionInventario.createdOn ? $d(Date.parse(ubicacionInventario.createdOn), 'short') : '' }}</td>
            <td>{{ ubicacionInventario.modifyByUser }}</td>
            <td>{{ ubicacionInventario.modifiedOn ? $d(Date.parse(ubicacionInventario.modifiedOn), 'short') : '' }}</td>
            <td>{{ ubicacionInventario.auditStatus }}</td>
            <td>
              <div v-if="ubicacionInventario.seccion">
                <router-link :to="{ name: 'SeccionView', params: { seccionId: ubicacionInventario.seccion.id } }">{{
                  ubicacionInventario.seccion.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="ubicacionInventario.inventario">
                <router-link :to="{ name: 'InventarioView', params: { inventarioId: ubicacionInventario.inventario.id } }">{{
                  ubicacionInventario.inventario.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'UbicacionInventarioView', params: { ubicacionInventarioId: ubicacionInventario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'UbicacionInventarioEdit', params: { ubicacionInventarioId: ubicacionInventario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ubicacionInventario)"
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
          id="segmaflotApp.ubicacionInventario.delete.question"
          data-cy="ubicacionInventarioDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ubicacionInventario-heading" v-text="$t('segmaflotApp.ubicacionInventario.delete.question', { id: removeId })">
          Are you sure you want to delete this Ubicacion Inventario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ubicacionInventario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUbicacionInventario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ubicacion-inventario.component.ts"></script>
