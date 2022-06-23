<template>
  <div>
    <h2 id="page-heading" data-cy="PagoHeading">
      <span v-text="$t('segmaflotApp.pago.home.title')" id="pago-heading">Pagos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.pago.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PagoCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-pago">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.pago.home.createLabel')"> Create a new Pago </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && pagos && pagos.length === 0">
      <span v-text="$t('segmaflotApp.pago.home.notFound')">No pagos found</span>
    </div>
    <div class="table-responsive" v-if="pagos && pagos.length > 0">
      <table class="table table-striped" aria-describedby="pagos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.tipoPago')">Tipo Pago</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.monto')">Monto</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.area')">Area</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.identificador')">Identificador</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.pago.auditStatus')">Audit Status</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pago in pagos" :key="pago.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PagoView', params: { pagoId: pago.id } }">{{ pago.id }}</router-link>
            </td>
            <td>{{ pago.fecha }}</td>
            <td>{{ pago.tipoPago }}</td>
            <td>{{ pago.monto }}</td>
            <td>{{ pago.area }}</td>
            <td>{{ pago.identificador }}</td>
            <td>{{ pago.createByUser }}</td>
            <td>{{ pago.createdOn ? $d(Date.parse(pago.createdOn), 'short') : '' }}</td>
            <td>{{ pago.modifyByUser }}</td>
            <td>{{ pago.modifiedOn ? $d(Date.parse(pago.modifiedOn), 'short') : '' }}</td>
            <td>{{ pago.auditStatus }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PagoView', params: { pagoId: pago.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PagoEdit', params: { pagoId: pago.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(pago)"
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
        ><span id="segmaflotApp.pago.delete.question" data-cy="pagoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-pago-heading" v-text="$t('segmaflotApp.pago.delete.question', { id: removeId })">
          Are you sure you want to delete this Pago?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-pago"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePago()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./pago.component.ts"></script>
