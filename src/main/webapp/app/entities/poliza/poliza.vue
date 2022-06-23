<template>
  <div>
    <h2 id="page-heading" data-cy="PolizaHeading">
      <span v-text="$t('segmaflotApp.poliza.home.title')" id="poliza-heading">Polizas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.poliza.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PolizaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-poliza"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.poliza.home.createLabel')"> Create a new Poliza </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && polizas && polizas.length === 0">
      <span v-text="$t('segmaflotApp.poliza.home.notFound')">No polizas found</span>
    </div>
    <div class="table-responsive" v-if="polizas && polizas.length > 0">
      <table class="table table-striped" aria-describedby="polizas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.fechaVencimiento')">Fecha Vencimiento</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.numeroPoliza')">Numero Poliza</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.poliza.unidad')">Unidad</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="poliza in polizas" :key="poliza.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PolizaView', params: { polizaId: poliza.id } }">{{ poliza.id }}</router-link>
            </td>
            <td>{{ poliza.fecha }}</td>
            <td>{{ poliza.fechaVencimiento }}</td>
            <td>{{ poliza.numeroPoliza }}</td>
            <td>{{ poliza.createByUser }}</td>
            <td>{{ poliza.createdOn ? $d(Date.parse(poliza.createdOn), 'short') : '' }}</td>
            <td>{{ poliza.modifyByUser }}</td>
            <td>{{ poliza.modifiedOn ? $d(Date.parse(poliza.modifiedOn), 'short') : '' }}</td>
            <td>{{ poliza.auditStatus }}</td>
            <td>
              <div v-if="poliza.unidad">
                <router-link :to="{ name: 'UnidadView', params: { unidadId: poliza.unidad.id } }">{{ poliza.unidad.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PolizaView', params: { polizaId: poliza.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PolizaEdit', params: { polizaId: poliza.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(poliza)"
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
        ><span id="segmaflotApp.poliza.delete.question" data-cy="polizaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-poliza-heading" v-text="$t('segmaflotApp.poliza.delete.question', { id: removeId })">
          Are you sure you want to delete this Poliza?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-poliza"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePoliza()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./poliza.component.ts"></script>
