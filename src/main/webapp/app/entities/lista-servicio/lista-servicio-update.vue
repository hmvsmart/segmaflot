<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.listaServicio.home.createOrEditLabel"
          data-cy="ListaServicioCreateUpdateHeading"
          v-text="$t('segmaflotApp.listaServicio.home.createOrEditLabel')"
        >
          Create or edit a ListaServicio
        </h2>
        <div>
          <div class="form-group" v-if="listaServicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="listaServicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.cantidad')" for="lista-servicio-cantidad"
              >Cantidad</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="lista-servicio-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.listaServicio.cantidad.$invalid, invalid: $v.listaServicio.cantidad.$invalid }"
              v-model.number="$v.listaServicio.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.createByUser')" for="lista-servicio-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="lista-servicio-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.listaServicio.createByUser.$invalid, invalid: $v.listaServicio.createByUser.$invalid }"
              v-model="$v.listaServicio.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.createdOn')" for="lista-servicio-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="lista-servicio-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.listaServicio.createdOn.$invalid, invalid: $v.listaServicio.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.listaServicio.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.modifyByUser')" for="lista-servicio-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="lista-servicio-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.listaServicio.modifyByUser.$invalid, invalid: $v.listaServicio.modifyByUser.$invalid }"
              v-model="$v.listaServicio.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.modifiedOn')" for="lista-servicio-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="lista-servicio-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.listaServicio.modifiedOn.$invalid, invalid: $v.listaServicio.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.listaServicio.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.auditStatus')" for="lista-servicio-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="lista-servicio-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.listaServicio.auditStatus.$invalid, invalid: $v.listaServicio.auditStatus.$invalid }"
              v-model="$v.listaServicio.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.servicio')" for="lista-servicio-servicio"
              >Servicio</label
            >
            <select class="form-control" id="lista-servicio-servicio" data-cy="servicio" name="servicio" v-model="listaServicio.servicio">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  listaServicio.servicio && servicioOption.id === listaServicio.servicio.id ? listaServicio.servicio : servicioOption
                "
                v-for="servicioOption in servicios"
                :key="servicioOption.id"
              >
                {{ servicioOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaServicio.unidadServicio')" for="lista-servicio-unidadServicio"
              >Unidad Servicio</label
            >
            <select
              class="form-control"
              id="lista-servicio-unidadServicio"
              data-cy="unidadServicio"
              name="unidadServicio"
              v-model="listaServicio.unidadServicio"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  listaServicio.unidadServicio && unidadServicioOption.id === listaServicio.unidadServicio.id
                    ? listaServicio.unidadServicio
                    : unidadServicioOption
                "
                v-for="unidadServicioOption in unidadServicios"
                :key="unidadServicioOption.id"
              >
                {{ unidadServicioOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.listaServicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./lista-servicio-update.component.ts"></script>
