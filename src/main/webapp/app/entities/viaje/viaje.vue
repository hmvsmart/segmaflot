<template>
  <div>
    <h2 id="page-heading" data-cy="ViajeHeading">
      <span v-text="$t('segmaflotApp.viaje.home.title')" id="viaje-heading">Viajes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.viaje.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ViajeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-viaje"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.viaje.home.createLabel')"> Create a new Viaje </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && viajes && viajes.length === 0">
      <span v-text="$t('segmaflotApp.viaje.home.notFound')">No viajes found</span>
    </div>
    <div class="table-responsive" v-if="viajes && viajes.length > 0">
      <table class="table table-striped" aria-describedby="viajes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.fechaInicio')">Fecha Inicio</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.fechaFin')">Fecha Fin</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.horaEmbarque')">Hora Embarque</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.tipoCarga')">Tipo Carga</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.pesoNeto')">Peso Neto</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.comentarios')">Comentarios</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.viaje.origen')">Origen</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="viaje in viajes" :key="viaje.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ViajeView', params: { viajeId: viaje.id } }">{{ viaje.id }}</router-link>
            </td>
            <td>{{ viaje.fecha }}</td>
            <td>{{ viaje.fechaInicio }}</td>
            <td>{{ viaje.fechaFin }}</td>
            <td>{{ viaje.horaEmbarque ? $d(Date.parse(viaje.horaEmbarque), 'short') : '' }}</td>
            <td>{{ viaje.tipoCarga }}</td>
            <td>{{ viaje.pesoNeto }}</td>
            <td>
              <a
                v-if="viaje.comentarios"
                v-on:click="openFile(viaje.comentariosContentType, viaje.comentarios)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="viaje.comentarios">{{ viaje.comentariosContentType }}, {{ byteSize(viaje.comentarios) }}</span>
            </td>
            <td>{{ viaje.status }}</td>
            <td>{{ viaje.createByUser }}</td>
            <td>{{ viaje.createdOn ? $d(Date.parse(viaje.createdOn), 'short') : '' }}</td>
            <td>{{ viaje.modifyByUser }}</td>
            <td>{{ viaje.modifiedOn ? $d(Date.parse(viaje.modifiedOn), 'short') : '' }}</td>
            <td>{{ viaje.auditStatus }}</td>
            <td>
              <div v-if="viaje.origen">
                <router-link :to="{ name: 'DireccionView', params: { direccionId: viaje.origen.id } }">{{ viaje.origen.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ViajeView', params: { viajeId: viaje.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ViajeEdit', params: { viajeId: viaje.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(viaje)"
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
        ><span id="segmaflotApp.viaje.delete.question" data-cy="viajeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-viaje-heading" v-text="$t('segmaflotApp.viaje.delete.question', { id: removeId })">
          Are you sure you want to delete this Viaje?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-viaje"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeViaje()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./viaje.component.ts"></script>
