<template>
  <div>
    <h2 id="page-heading" data-cy="UnidadHeading">
      <span v-text="$t('segmaflotApp.unidad.home.title')" id="unidad-heading">Unidads</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.unidad.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UnidadCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-unidad"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.unidad.home.createLabel')"> Create a new Unidad </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && unidads && unidads.length === 0">
      <span v-text="$t('segmaflotApp.unidad.home.notFound')">No unidads found</span>
    </div>
    <div class="table-responsive" v-if="unidads && unidads.length > 0">
      <table class="table table-striped" aria-describedby="unidads">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.numeroSerie')">Numero Serie</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.kmActual')">Km Actual</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.tipoAdquisicion')">Tipo Adquisicion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.imagen')">Imagen</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.color')">Color</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.descripcion')">Descripcion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.unidad.vehiculo')">Vehiculo</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="unidad in unidads" :key="unidad.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UnidadView', params: { unidadId: unidad.id } }">{{ unidad.id }}</router-link>
            </td>
            <td>{{ unidad.fecha }}</td>
            <td>{{ unidad.numeroSerie }}</td>
            <td>{{ unidad.kmActual }}</td>
            <td>{{ unidad.tipoAdquisicion }}</td>
            <td>
              <a v-if="unidad.imagen" v-on:click="openFile(unidad.imagenContentType, unidad.imagen)" v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="unidad.imagen">{{ unidad.imagenContentType }}, {{ byteSize(unidad.imagen) }}</span>
            </td>
            <td>{{ unidad.color }}</td>
            <td>
              <a
                v-if="unidad.descripcion"
                v-on:click="openFile(unidad.descripcionContentType, unidad.descripcion)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="unidad.descripcion">{{ unidad.descripcionContentType }}, {{ byteSize(unidad.descripcion) }}</span>
            </td>
            <td>{{ unidad.status }}</td>
            <td>{{ unidad.createByUser }}</td>
            <td>{{ unidad.createdOn ? $d(Date.parse(unidad.createdOn), 'short') : '' }}</td>
            <td>{{ unidad.modifyByUser }}</td>
            <td>{{ unidad.modifiedOn ? $d(Date.parse(unidad.modifiedOn), 'short') : '' }}</td>
            <td>{{ unidad.auditStatus }}</td>
            <td>
              <div v-if="unidad.vehiculo">
                <router-link :to="{ name: 'VehiculoView', params: { vehiculoId: unidad.vehiculo.id } }">{{
                  unidad.vehiculo.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UnidadView', params: { unidadId: unidad.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UnidadEdit', params: { unidadId: unidad.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(unidad)"
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
        ><span id="segmaflotApp.unidad.delete.question" data-cy="unidadDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-unidad-heading" v-text="$t('segmaflotApp.unidad.delete.question', { id: removeId })">
          Are you sure you want to delete this Unidad?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-unidad"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUnidad()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./unidad.component.ts"></script>
