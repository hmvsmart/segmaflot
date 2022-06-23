<template>
  <div>
    <h2 id="page-heading" data-cy="DiaHorarioHeading">
      <span v-text="$t('segmaflotApp.diaHorario.home.title')" id="dia-horario-heading">Dia Horarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.diaHorario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DiaHorarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-dia-horario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.diaHorario.home.createLabel')"> Create a new Dia Horario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && diaHorarios && diaHorarios.length === 0">
      <span v-text="$t('segmaflotApp.diaHorario.home.notFound')">No diaHorarios found</span>
    </div>
    <div class="table-responsive" v-if="diaHorarios && diaHorarios.length > 0">
      <table class="table table-striped" aria-describedby="diaHorarios">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.dia')">Dia</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.horaEntrada')">Hora Entrada</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.horaSalida')">Hora Salida</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.diaHorario.horario')">Horario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="diaHorario in diaHorarios" :key="diaHorario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DiaHorarioView', params: { diaHorarioId: diaHorario.id } }">{{ diaHorario.id }}</router-link>
            </td>
            <td>{{ diaHorario.dia }}</td>
            <td>{{ diaHorario.horaEntrada ? $d(Date.parse(diaHorario.horaEntrada), 'short') : '' }}</td>
            <td>{{ diaHorario.horaSalida ? $d(Date.parse(diaHorario.horaSalida), 'short') : '' }}</td>
            <td>{{ diaHorario.createByUser }}</td>
            <td>{{ diaHorario.createdOn ? $d(Date.parse(diaHorario.createdOn), 'short') : '' }}</td>
            <td>{{ diaHorario.modifyByUser }}</td>
            <td>{{ diaHorario.modifiedOn ? $d(Date.parse(diaHorario.modifiedOn), 'short') : '' }}</td>
            <td>{{ diaHorario.auditStatus }}</td>
            <td>
              <div v-if="diaHorario.horario">
                <router-link :to="{ name: 'HorarioView', params: { horarioId: diaHorario.horario.id } }">{{
                  diaHorario.horario.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DiaHorarioView', params: { diaHorarioId: diaHorario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DiaHorarioEdit', params: { diaHorarioId: diaHorario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(diaHorario)"
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
        ><span id="segmaflotApp.diaHorario.delete.question" data-cy="diaHorarioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-diaHorario-heading" v-text="$t('segmaflotApp.diaHorario.delete.question', { id: removeId })">
          Are you sure you want to delete this Dia Horario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-diaHorario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDiaHorario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./dia-horario.component.ts"></script>
