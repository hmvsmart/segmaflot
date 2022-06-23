<template>
  <div>
    <h2 id="page-heading" data-cy="ItinerarioHeading">
      <span v-text="$t('segmaflotApp.itinerario.home.title')" id="itinerario-heading">Itinerarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.itinerario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ItinerarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-itinerario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.itinerario.home.createLabel')"> Create a new Itinerario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && itinerarios && itinerarios.length === 0">
      <span v-text="$t('segmaflotApp.itinerario.home.notFound')">No itinerarios found</span>
    </div>
    <div class="table-responsive" v-if="itinerarios && itinerarios.length > 0">
      <table class="table table-striped" aria-describedby="itinerarios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.accion')">Accion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.fechaHoraEstimada')">Fecha Hora Estimada</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.itinerario.viaje')">Viaje</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="itinerario in itinerarios" :key="itinerario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ItinerarioView', params: { itinerarioId: itinerario.id } }">{{ itinerario.id }}</router-link>
            </td>
            <td>{{ itinerario.accion }}</td>
            <td>{{ itinerario.fechaHoraEstimada ? $d(Date.parse(itinerario.fechaHoraEstimada), 'short') : '' }}</td>
            <td>{{ itinerario.createByUser }}</td>
            <td>{{ itinerario.createdOn ? $d(Date.parse(itinerario.createdOn), 'short') : '' }}</td>
            <td>{{ itinerario.modifyByUser }}</td>
            <td>{{ itinerario.modifiedOn ? $d(Date.parse(itinerario.modifiedOn), 'short') : '' }}</td>
            <td>{{ itinerario.auditStatus }}</td>
            <td>
              <div v-if="itinerario.viaje">
                <router-link :to="{ name: 'ViajeView', params: { viajeId: itinerario.viaje.id } }">{{ itinerario.viaje.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ItinerarioView', params: { itinerarioId: itinerario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ItinerarioEdit', params: { itinerarioId: itinerario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(itinerario)"
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
        ><span id="segmaflotApp.itinerario.delete.question" data-cy="itinerarioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-itinerario-heading" v-text="$t('segmaflotApp.itinerario.delete.question', { id: removeId })">
          Are you sure you want to delete this Itinerario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-itinerario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeItinerario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./itinerario.component.ts"></script>
