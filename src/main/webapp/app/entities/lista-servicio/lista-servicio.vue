<template>
  <div>
    <h2 id="page-heading" data-cy="ListaServicioHeading">
      <span v-text="$t('segmaflotApp.listaServicio.home.title')" id="lista-servicio-heading">Lista Servicios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.listaServicio.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ListaServicioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-lista-servicio"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.listaServicio.home.createLabel')"> Create a new Lista Servicio </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && listaServicios && listaServicios.length === 0">
      <span v-text="$t('segmaflotApp.listaServicio.home.notFound')">No listaServicios found</span>
    </div>
    <div class="table-responsive" v-if="listaServicios && listaServicios.length > 0">
      <table class="table table-striped" aria-describedby="listaServicios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.servicio')">Servicio</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.listaServicio.unidadServicio')">Unidad Servicio</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="listaServicio in listaServicios" :key="listaServicio.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ListaServicioView', params: { listaServicioId: listaServicio.id } }">{{
                listaServicio.id
              }}</router-link>
            </td>
            <td>{{ listaServicio.cantidad }}</td>
            <td>{{ listaServicio.createByUser }}</td>
            <td>{{ listaServicio.createdOn ? $d(Date.parse(listaServicio.createdOn), 'short') : '' }}</td>
            <td>{{ listaServicio.modifyByUser }}</td>
            <td>{{ listaServicio.modifiedOn ? $d(Date.parse(listaServicio.modifiedOn), 'short') : '' }}</td>
            <td>{{ listaServicio.auditStatus }}</td>
            <td>
              <div v-if="listaServicio.servicio">
                <router-link :to="{ name: 'ServicioView', params: { servicioId: listaServicio.servicio.id } }">{{
                  listaServicio.servicio.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="listaServicio.unidadServicio">
                <router-link :to="{ name: 'UnidadServicioView', params: { unidadServicioId: listaServicio.unidadServicio.id } }">{{
                  listaServicio.unidadServicio.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ListaServicioView', params: { listaServicioId: listaServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ListaServicioEdit', params: { listaServicioId: listaServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(listaServicio)"
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
        ><span id="segmaflotApp.listaServicio.delete.question" data-cy="listaServicioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-listaServicio-heading" v-text="$t('segmaflotApp.listaServicio.delete.question', { id: removeId })">
          Are you sure you want to delete this Lista Servicio?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-listaServicio"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeListaServicio()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./lista-servicio.component.ts"></script>
