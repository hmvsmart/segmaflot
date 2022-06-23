<template>
  <div>
    <h2 id="page-heading" data-cy="HorarioHeading">
      <span v-text="$t('segmaflotApp.horario.home.title')" id="horario-heading">Horarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.horario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'HorarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-horario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.horario.home.createLabel')"> Create a new Horario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && horarios && horarios.length === 0">
      <span v-text="$t('segmaflotApp.horario.home.notFound')">No horarios found</span>
    </div>
    <div class="table-responsive" v-if="horarios && horarios.length > 0">
      <table class="table table-striped" aria-describedby="horarios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.status')">Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.horario.empleado')">Empleado</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="horario in horarios" :key="horario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HorarioView', params: { horarioId: horario.id } }">{{ horario.id }}</router-link>
            </td>
            <td>{{ horario.fecha }}</td>
            <td>{{ horario.status }}</td>
            <td>{{ horario.createByUser }}</td>
            <td>{{ horario.createdOn ? $d(Date.parse(horario.createdOn), 'short') : '' }}</td>
            <td>{{ horario.modifyByUser }}</td>
            <td>{{ horario.modifiedOn ? $d(Date.parse(horario.modifiedOn), 'short') : '' }}</td>
            <td>{{ horario.auditStatus }}</td>
            <td>
              <div v-if="horario.empleado">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: horario.empleado.id } }">{{
                  horario.empleado.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HorarioView', params: { horarioId: horario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HorarioEdit', params: { horarioId: horario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(horario)"
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
        ><span id="segmaflotApp.horario.delete.question" data-cy="horarioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-horario-heading" v-text="$t('segmaflotApp.horario.delete.question', { id: removeId })">
          Are you sure you want to delete this Horario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-horario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeHorario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./horario.component.ts"></script>
