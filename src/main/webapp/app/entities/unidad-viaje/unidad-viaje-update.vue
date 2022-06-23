<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.unidadViaje.home.createOrEditLabel"
          data-cy="UnidadViajeCreateUpdateHeading"
          v-text="$t('segmaflotApp.unidadViaje.home.createOrEditLabel')"
        >
          Create or edit a UnidadViaje
        </h2>
        <div>
          <div class="form-group" v-if="unidadViaje.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="unidadViaje.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.fecha')" for="unidad-viaje-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="unidad-viaje-fecha"
                  v-model="$v.unidadViaje.fecha.$model"
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
                id="unidad-viaje-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.unidadViaje.fecha.$invalid, invalid: $v.unidadViaje.fecha.$invalid }"
                v-model="$v.unidadViaje.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.status')" for="unidad-viaje-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="unidad-viaje-status"
              data-cy="status"
              :class="{ valid: !$v.unidadViaje.status.$invalid, invalid: $v.unidadViaje.status.$invalid }"
              v-model="$v.unidadViaje.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.createByUser')" for="unidad-viaje-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="unidad-viaje-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.unidadViaje.createByUser.$invalid, invalid: $v.unidadViaje.createByUser.$invalid }"
              v-model="$v.unidadViaje.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.createdOn')" for="unidad-viaje-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="unidad-viaje-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.unidadViaje.createdOn.$invalid, invalid: $v.unidadViaje.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidadViaje.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.modifyByUser')" for="unidad-viaje-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="unidad-viaje-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.unidadViaje.modifyByUser.$invalid, invalid: $v.unidadViaje.modifyByUser.$invalid }"
              v-model="$v.unidadViaje.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.modifiedOn')" for="unidad-viaje-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="unidad-viaje-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.unidadViaje.modifiedOn.$invalid, invalid: $v.unidadViaje.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidadViaje.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.auditStatus')" for="unidad-viaje-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="unidad-viaje-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.unidadViaje.auditStatus.$invalid, invalid: $v.unidadViaje.auditStatus.$invalid }"
              v-model="$v.unidadViaje.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.viaje')" for="unidad-viaje-viaje">Viaje</label>
            <select class="form-control" id="unidad-viaje-viaje" data-cy="viaje" name="viaje" v-model="unidadViaje.viaje">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="unidadViaje.viaje && viajeOption.id === unidadViaje.viaje.id ? unidadViaje.viaje : viajeOption"
                v-for="viajeOption in viajes"
                :key="viajeOption.id"
              >
                {{ viajeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadViaje.unidad')" for="unidad-viaje-unidad">Unidad</label>
            <select class="form-control" id="unidad-viaje-unidad" data-cy="unidad" name="unidad" v-model="unidadViaje.unidad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="unidadViaje.unidad && unidadOption.id === unidadViaje.unidad.id ? unidadViaje.unidad : unidadOption"
                v-for="unidadOption in unidads"
                :key="unidadOption.id"
              >
                {{ unidadOption.id }}
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
            :disabled="$v.unidadViaje.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./unidad-viaje-update.component.ts"></script>
