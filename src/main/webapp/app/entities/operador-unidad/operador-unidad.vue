<template>
  <div>
    <h2 id="page-heading" data-cy="OperadorUnidadHeading">
      <span v-text="$t('segmaflotApp.operadorUnidad.home.title')" id="operador-unidad-heading">Operador Unidads</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.operadorUnidad.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OperadorUnidadCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-operador-unidad"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.operadorUnidad.home.createLabel')"> Create a new Operador Unidad </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && operadorUnidads && operadorUnidads.length === 0">
      <span v-text="$t('segmaflotApp.operadorUnidad.home.notFound')">No operadorUnidads found</span>
    </div>
    <div class="table-responsive" v-if="operadorUnidads && operadorUnidads.length > 0">
      <table class="table table-striped" aria-describedby="operadorUnidads">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.rol')">Rol</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.unidasViaje')">Unidas Viaje</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.operadorUnidad.empleado')">Empleado</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="operadorUnidad in operadorUnidads" :key="operadorUnidad.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OperadorUnidadView', params: { operadorUnidadId: operadorUnidad.id } }">{{
                operadorUnidad.id
              }}</router-link>
            </td>
            <td>{{ operadorUnidad.fecha ? $d(Date.parse(operadorUnidad.fecha), 'short') : '' }}</td>
            <td>{{ operadorUnidad.rol }}</td>
            <td>{{ operadorUnidad.status }}</td>
            <td>{{ operadorUnidad.createByUser }}</td>
            <td>{{ operadorUnidad.createdOn ? $d(Date.parse(operadorUnidad.createdOn), 'short') : '' }}</td>
            <td>{{ operadorUnidad.modifyByUser }}</td>
            <td>{{ operadorUnidad.modifiedOn ? $d(Date.parse(operadorUnidad.modifiedOn), 'short') : '' }}</td>
            <td>{{ operadorUnidad.auditStatus }}</td>
            <td>
              <div v-if="operadorUnidad.unidasViaje">
                <router-link :to="{ name: 'UnidadViajeView', params: { unidadViajeId: operadorUnidad.unidasViaje.id } }">{{
                  operadorUnidad.unidasViaje.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="operadorUnidad.empleado">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: operadorUnidad.empleado.id } }">{{
                  operadorUnidad.empleado.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OperadorUnidadView', params: { operadorUnidadId: operadorUnidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OperadorUnidadEdit', params: { operadorUnidadId: operadorUnidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(operadorUnidad)"
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
          id="segmaflotApp.operadorUnidad.delete.question"
          data-cy="operadorUnidadDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-operadorUnidad-heading" v-text="$t('segmaflotApp.operadorUnidad.delete.question', { id: removeId })">
          Are you sure you want to delete this Operador Unidad?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-operadorUnidad"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOperadorUnidad()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./operador-unidad.component.ts"></script>
