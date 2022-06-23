<template>
  <div>
    <h2 id="page-heading" data-cy="EmpleadoHeading">
      <span v-text="$t('segmaflotApp.empleado.home.title')" id="empleado-heading">Empleados</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.empleado.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EmpleadoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-empleado"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.empleado.home.createLabel')"> Create a new Empleado </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && empleados && empleados.length === 0">
      <span v-text="$t('segmaflotApp.empleado.home.notFound')">No empleados found</span>
    </div>
    <div class="table-responsive" v-if="empleados && empleados.length > 0">
      <table class="table table-striped" aria-describedby="empleados">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.rfc')">Rfc</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.nss')">Nss</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.finicio')">Finicio</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.puesto')">Puesto</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.salario')">Salario</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.diaPago')">Dia Pago</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.tipoPago')">Tipo Pago</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.persona')">Persona</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.empleado.sucursal')">Sucursal</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="empleado in empleados" :key="empleado.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: empleado.id } }">{{ empleado.id }}</router-link>
            </td>
            <td>{{ empleado.rfc }}</td>
            <td>{{ empleado.nss }}</td>
            <td>{{ empleado.finicio }}</td>
            <td>{{ empleado.puesto }}</td>
            <td>{{ empleado.salario }}</td>
            <td>{{ empleado.diaPago }}</td>
            <td>{{ empleado.tipoPago }}</td>
            <td>{{ empleado.createByUser }}</td>
            <td>{{ empleado.createdOn ? $d(Date.parse(empleado.createdOn), 'short') : '' }}</td>
            <td>{{ empleado.modifyByUser }}</td>
            <td>{{ empleado.modifiedOn ? $d(Date.parse(empleado.modifiedOn), 'short') : '' }}</td>
            <td>{{ empleado.auditStatus }}</td>
            <td>
              <div v-if="empleado.persona">
                <router-link :to="{ name: 'PersonaView', params: { personaId: empleado.persona.id } }">{{
                  empleado.persona.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="empleado.sucursal">
                <router-link :to="{ name: 'SucursalView', params: { sucursalId: empleado.sucursal.id } }">{{
                  empleado.sucursal.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: empleado.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EmpleadoEdit', params: { empleadoId: empleado.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(empleado)"
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
        ><span id="segmaflotApp.empleado.delete.question" data-cy="empleadoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-empleado-heading" v-text="$t('segmaflotApp.empleado.delete.question', { id: removeId })">
          Are you sure you want to delete this Empleado?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-empleado"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEmpleado()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./empleado.component.ts"></script>
