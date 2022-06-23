<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.precioServicio.home.createOrEditLabel"
          data-cy="PrecioServicioCreateUpdateHeading"
          v-text="$t('segmaflotApp.precioServicio.home.createOrEditLabel')"
        >
          Create or edit a PrecioServicio
        </h2>
        <div>
          <div class="form-group" v-if="precioServicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="precioServicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.fecha')" for="precio-servicio-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="precio-servicio-fecha"
                  v-model="$v.precioServicio.fecha.$model"
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
                id="precio-servicio-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.precioServicio.fecha.$invalid, invalid: $v.precioServicio.fecha.$invalid }"
                v-model="$v.precioServicio.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.costo')" for="precio-servicio-costo">Costo</label>
            <input
              type="number"
              class="form-control"
              name="costo"
              id="precio-servicio-costo"
              data-cy="costo"
              :class="{ valid: !$v.precioServicio.costo.$invalid, invalid: $v.precioServicio.costo.$invalid }"
              v-model.number="$v.precioServicio.costo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.createByUser')" for="precio-servicio-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="precio-servicio-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.precioServicio.createByUser.$invalid, invalid: $v.precioServicio.createByUser.$invalid }"
              v-model="$v.precioServicio.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.createdOn')" for="precio-servicio-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="precio-servicio-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.precioServicio.createdOn.$invalid, invalid: $v.precioServicio.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.precioServicio.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.modifyByUser')" for="precio-servicio-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="precio-servicio-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.precioServicio.modifyByUser.$invalid, invalid: $v.precioServicio.modifyByUser.$invalid }"
              v-model="$v.precioServicio.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.modifiedOn')" for="precio-servicio-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="precio-servicio-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.precioServicio.modifiedOn.$invalid, invalid: $v.precioServicio.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.precioServicio.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.auditStatus')" for="precio-servicio-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="precio-servicio-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.precioServicio.auditStatus.$invalid, invalid: $v.precioServicio.auditStatus.$invalid }"
              v-model="$v.precioServicio.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioServicio.servicio')" for="precio-servicio-servicio"
              >Servicio</label
            >
            <select class="form-control" id="precio-servicio-servicio" data-cy="servicio" name="servicio" v-model="precioServicio.servicio">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  precioServicio.servicio && servicioOption.id === precioServicio.servicio.id ? precioServicio.servicio : servicioOption
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
            :disabled="$v.precioServicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./precio-servicio-update.component.ts"></script>
