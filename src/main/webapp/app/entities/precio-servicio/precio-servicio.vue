<template>
  <div>
    <h2 id="page-heading" data-cy="PrecioServicioHeading">
      <span v-text="$t('segmaflotApp.precioServicio.home.title')" id="precio-servicio-heading">Precio Servicios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.precioServicio.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PrecioServicioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-precio-servicio"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.precioServicio.home.createLabel')"> Create a new Precio Servicio </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && precioServicios && precioServicios.length === 0">
      <span v-text="$t('segmaflotApp.precioServicio.home.notFound')">No precioServicios found</span>
    </div>
    <div class="table-responsive" v-if="precioServicios && precioServicios.length > 0">
      <table class="table table-striped" aria-describedby="precioServicios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.costo')">Costo</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.precioServicio.servicio')">Servicio</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="precioServicio in precioServicios" :key="precioServicio.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PrecioServicioView', params: { precioServicioId: precioServicio.id } }">{{
                precioServicio.id
              }}</router-link>
            </td>
            <td>{{ precioServicio.fecha }}</td>
            <td>{{ precioServicio.costo }}</td>
            <td>{{ precioServicio.createByUser }}</td>
            <td>{{ precioServicio.createdOn ? $d(Date.parse(precioServicio.createdOn), 'short') : '' }}</td>
            <td>{{ precioServicio.modifyByUser }}</td>
            <td>{{ precioServicio.modifiedOn ? $d(Date.parse(precioServicio.modifiedOn), 'short') : '' }}</td>
            <td>{{ precioServicio.auditStatus }}</td>
            <td>
              <div v-if="precioServicio.servicio">
                <router-link :to="{ name: 'ServicioView', params: { servicioId: precioServicio.servicio.id } }">{{
                  precioServicio.servicio.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PrecioServicioView', params: { precioServicioId: precioServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PrecioServicioEdit', params: { precioServicioId: precioServicio.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(precioServicio)"
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
          id="segmaflotApp.precioServicio.delete.question"
          data-cy="precioServicioDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-precioServicio-heading" v-text="$t('segmaflotApp.precioServicio.delete.question', { id: removeId })">
          Are you sure you want to delete this Precio Servicio?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-precioServicio"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePrecioServicio()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./precio-servicio.component.ts"></script>
