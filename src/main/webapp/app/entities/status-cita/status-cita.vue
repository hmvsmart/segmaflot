<template>
  <div>
    <h2 id="page-heading" data-cy="StatusCitaHeading">
      <span v-text="$t('segmaflotApp.statusCita.home.title')" id="status-cita-heading">Status Citas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.statusCita.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StatusCitaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-status-cita"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.statusCita.home.createLabel')"> Create a new Status Cita </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && statusCitas && statusCitas.length === 0">
      <span v-text="$t('segmaflotApp.statusCita.home.notFound')">No statusCitas found</span>
    </div>
    <div class="table-responsive" v-if="statusCitas && statusCitas.length > 0">
      <table class="table table-striped" aria-describedby="statusCitas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.statusCita.cita')">Cita</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="statusCita in statusCitas" :key="statusCita.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StatusCitaView', params: { statusCitaId: statusCita.id } }">{{ statusCita.id }}</router-link>
            </td>
            <td>{{ statusCita.fecha }}</td>
            <td v-text="$t('segmaflotApp.TipoStatusCita.' + statusCita.status)">{{ statusCita.status }}</td>
            <td>{{ statusCita.createByUser }}</td>
            <td>{{ statusCita.createdOn ? $d(Date.parse(statusCita.createdOn), 'short') : '' }}</td>
            <td>{{ statusCita.modifyByUser }}</td>
            <td>{{ statusCita.modifiedOn ? $d(Date.parse(statusCita.modifiedOn), 'short') : '' }}</td>
            <td>{{ statusCita.auditStatus }}</td>
            <td>
              <div v-if="statusCita.cita">
                <router-link :to="{ name: 'CitaView', params: { citaId: statusCita.cita.id } }">{{ statusCita.cita.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StatusCitaView', params: { statusCitaId: statusCita.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StatusCitaEdit', params: { statusCitaId: statusCita.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(statusCita)"
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
        ><span id="segmaflotApp.statusCita.delete.question" data-cy="statusCitaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-statusCita-heading" v-text="$t('segmaflotApp.statusCita.delete.question', { id: removeId })">
          Are you sure you want to delete this Status Cita?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-statusCita"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStatusCita()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./status-cita.component.ts"></script>
