<template>
  <div>
    <h2 id="page-heading" data-cy="ExperienciaHabilidadHeading">
      <span v-text="$t('segmaflotApp.experienciaHabilidad.home.title')" id="experiencia-habilidad-heading">Experiencia Habilidads</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('segmaflotApp.experienciaHabilidad.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ExperienciaHabilidadCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-experiencia-habilidad"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('segmaflotApp.experienciaHabilidad.home.createLabel')"> Create a new Experiencia Habilidad </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && experienciaHabilidads && experienciaHabilidads.length === 0">
      <span v-text="$t('segmaflotApp.experienciaHabilidad.home.notFound')">No experienciaHabilidads found</span>
    </div>
    <div class="table-responsive" v-if="experienciaHabilidads && experienciaHabilidads.length > 0">
      <table class="table table-striped" aria-describedby="experienciaHabilidads">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.descripcion')">Descripcion</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.createByUser')">Create By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.createdOn')">Created On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.modifyByUser')">Modify By User</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.modifiedOn')">Modified On</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.auditStatus')">Audit Status</span></th>
            <th scope="row"><span v-text="$t('segmaflotApp.experienciaHabilidad.empleado')">Empleado</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="experienciaHabilidad in experienciaHabilidads" :key="experienciaHabilidad.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ExperienciaHabilidadView', params: { experienciaHabilidadId: experienciaHabilidad.id } }">{{
                experienciaHabilidad.id
              }}</router-link>
            </td>
            <td>{{ experienciaHabilidad.fecha }}</td>
            <td>
              <a
                v-if="experienciaHabilidad.descripcion"
                v-on:click="openFile(experienciaHabilidad.descripcionContentType, experienciaHabilidad.descripcion)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="experienciaHabilidad.descripcion"
                >{{ experienciaHabilidad.descripcionContentType }}, {{ byteSize(experienciaHabilidad.descripcion) }}</span
              >
            </td>
            <td>{{ experienciaHabilidad.createByUser }}</td>
            <td>{{ experienciaHabilidad.createdOn ? $d(Date.parse(experienciaHabilidad.createdOn), 'short') : '' }}</td>
            <td>{{ experienciaHabilidad.modifyByUser }}</td>
            <td>{{ experienciaHabilidad.modifiedOn ? $d(Date.parse(experienciaHabilidad.modifiedOn), 'short') : '' }}</td>
            <td>{{ experienciaHabilidad.auditStatus }}</td>
            <td>
              <div v-if="experienciaHabilidad.empleado">
                <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: experienciaHabilidad.empleado.id } }">{{
                  experienciaHabilidad.empleado.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ExperienciaHabilidadView', params: { experienciaHabilidadId: experienciaHabilidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ExperienciaHabilidadEdit', params: { experienciaHabilidadId: experienciaHabilidad.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(experienciaHabilidad)"
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
          id="segmaflotApp.experienciaHabilidad.delete.question"
          data-cy="experienciaHabilidadDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-experienciaHabilidad-heading" v-text="$t('segmaflotApp.experienciaHabilidad.delete.question', { id: removeId })">
          Are you sure you want to delete this Experiencia Habilidad?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-experienciaHabilidad"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeExperienciaHabilidad()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./experiencia-habilidad.component.ts"></script>
