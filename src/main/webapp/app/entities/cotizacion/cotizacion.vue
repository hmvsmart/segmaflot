<template>
  <div>
    <h2 id="page-heading" data-cy="CotizacionHeading">
      <span v-text="$t('segmaflotApp.cotizacion.home.title')" id="cotizacion-heading">Cotizacions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.cotizacion.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CotizacionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cotizacion"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.cotizacion.home.createLabel')"> Create a new Cotizacion </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cotizacions && cotizacions.length === 0">
      <span v-text="$t('segmaflotApp.cotizacion.home.notFound')">No cotizacions found</span>
    </div>
    <div class="table-responsive" v-if="cotizacions && cotizacions.length > 0">
      <table class="table table-striped" aria-describedby="cotizacions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.cotizacion.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cotizacion in cotizacions" :key="cotizacion.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CotizacionView', params: { cotizacionId: cotizacion.id } }">{{ cotizacion.id }}</router-link>
            </td>
            <td>{{ cotizacion.fecha }}</td>
            <td>{{ cotizacion.createByUser }}</td>
            <td>{{ cotizacion.createdOn ? $d(Date.parse(cotizacion.createdOn), 'short') : '' }}</td>
            <td>{{ cotizacion.modifyByUser }}</td>
            <td>{{ cotizacion.modifiedOn ? $d(Date.parse(cotizacion.modifiedOn), 'short') : '' }}</td>
            <td>{{ cotizacion.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CotizacionView', params: { cotizacionId: cotizacion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CotizacionEdit', params: { cotizacionId: cotizacion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cotizacion)"
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
        ><span id="segmaflotApp.cotizacion.delete.question" data-cy="cotizacionDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cotizacion-heading" v-text="$t('segmaflotApp.cotizacion.delete.question', { id: removeId })">
          Are you sure you want to delete this Cotizacion?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cotizacion"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCotizacion()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cotizacion.component.ts"></script>
