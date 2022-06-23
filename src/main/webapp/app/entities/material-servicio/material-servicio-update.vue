<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.materialServicio.home.createOrEditLabel"
          data-cy="MaterialServicioCreateUpdateHeading"
          v-text="$t('segmaflotApp.materialServicio.home.createOrEditLabel')"
        >
          Create or edit a MaterialServicio
        </h2>
        <div>
          <div class="form-group" v-if="materialServicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="materialServicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.fecha')" for="material-servicio-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="material-servicio-fecha"
                  v-model="$v.materialServicio.fecha.$model"
                  name="fecha"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="material-servicio-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.materialServicio.fecha.$invalid, invalid: $v.materialServicio.fecha.$invalid }"
                v-model="$v.materialServicio.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.cantidad')" for="material-servicio-cantidad"
              >Cantidad</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="material-servicio-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.materialServicio.cantidad.$invalid, invalid: $v.materialServicio.cantidad.$invalid }"
              v-model.number="$v.materialServicio.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.createByUser')" for="material-servicio-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="material-servicio-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.materialServicio.createByUser.$invalid, invalid: $v.materialServicio.createByUser.$invalid }"
              v-model="$v.materialServicio.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.createdOn')" for="material-servicio-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="material-servicio-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.materialServicio.createdOn.$invalid, invalid: $v.materialServicio.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.materialServicio.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.modifyByUser')" for="material-servicio-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="material-servicio-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.materialServicio.modifyByUser.$invalid, invalid: $v.materialServicio.modifyByUser.$invalid }"
              v-model="$v.materialServicio.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.modifiedOn')" for="material-servicio-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="material-servicio-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.materialServicio.modifiedOn.$invalid, invalid: $v.materialServicio.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.materialServicio.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.auditStatus')" for="material-servicio-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="material-servicio-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.materialServicio.auditStatus.$invalid, invalid: $v.materialServicio.auditStatus.$invalid }"
              v-model="$v.materialServicio.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.inventario')" for="material-servicio-inventario"
              >Inventario</label
            >
            <select
              class="form-control"
              id="material-servicio-inventario"
              data-cy="inventario"
              name="inventario"
              v-model="materialServicio.inventario"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  materialServicio.inventario && inventarioOption.id === materialServicio.inventario.id
                    ? materialServicio.inventario
                    : inventarioOption
                "
                v-for="inventarioOption in inventarios"
                :key="inventarioOption.id"
              >
                {{ inventarioOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.materialServicio.servicio')" for="material-servicio-servicio"
              >Servicio</label
            >
            <select
              class="form-control"
              id="material-servicio-servicio"
              data-cy="servicio"
              name="servicio"
              v-model="materialServicio.servicio"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  materialServicio.servicio && servicioOption.id === materialServicio.servicio.id
                    ? materialServicio.servicio
                    : servicioOption
                "
                v-for="servicioOption in servicios"
                :key="servicioOption.id"
              >
                {{ servicioOption.id }}
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
            :disabled="$v.materialServicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./material-servicio-update.component.ts"></script>
