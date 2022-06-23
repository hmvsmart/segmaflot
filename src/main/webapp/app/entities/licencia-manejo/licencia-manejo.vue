<template>
  <div>
    <h2 id="page-heading" data-cy="LicenciaManejoHeading">
      <span v-text="$t('segmaflotApp.licenciaManejo.home.title')" id="licencia-manejo-heading">Licencia Manejos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.licenciaManejo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LicenciaManejoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-licencia-manejo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.licenciaManejo.home.createLabel')"> Create a new Licencia Manejo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && licenciaManejos && licenciaManejos.length === 0">
      <span v-text="$t('segmaflotApp.licenciaManejo.home.notFound')">No licenciaManejos found</span>
    </div>
    <div class="table-responsive" v-if="licenciaManejos && licenciaManejos.length > 0">
      <table class="table table-striped" aria-describedby="licenciaManejos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.tipo')">Tipo</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.fechaExpiracion')">Fecha Expiracion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.licenciaManejo.empleado')">Empleado</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="licenciaManejo in licenciaManejos" :key="licenciaManejo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LicenciaManejoView', params: { licenciaManejoId: licenciaManejo.id } }">{{
                licenciaManejo.id
              }}</router-link>
            </td>
            <td>{{ licenciaManejo.fecha }}</td>
            <td>{{ licenciaManejo.tipo }}</td>
            <td>{{ licenciaManejo.status }}</td>
            <td>{{ licenciaManejo.fechaExpiracion }}</td>
            <td>{{ licenciaManejo.createByUser }}</td>
            <td>{{ licenciaManejo.createdOn ? $d(Date.parse(licenciaManejo.createdOn), 'short') : '' }}</td>
            <td>{{ licenciaManejo.modifyByUser }}</td>
            <td>{{ licenciaManejo.modifiedOn ? $d(Date.parse(licenciaManejo.modifiedOn), 'short') : '' }}</td>
            <td>{{ licenciaManejo.auditStatus }}</td>
            <td>
              <div v-if="licenciaManejo.empleado">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: licenciaManejo.empleado.id } }">{{
                  licenciaManejo.empleado.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'LicenciaManejoView', params: { licenciaManejoId: licenciaManejo.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'LicenciaManejoEdit', params: { licenciaManejoId: licenciaManejo.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(licenciaManejo)"
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
          id="segmaflotApp.licenciaManejo.delete.question"
          data-cy="licenciaManejoDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-licenciaManejo-heading" v-text="$t('segmaflotApp.licenciaManejo.delete.question', { id: removeId })">
          Are you sure you want to delete this Licencia Manejo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-licenciaManejo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLicenciaManejo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./licencia-manejo.component.ts"></script>
