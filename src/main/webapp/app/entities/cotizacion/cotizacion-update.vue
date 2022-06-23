<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.cotizacion.home.createOrEditLabel"
          data-cy="CotizacionCreateUpdateHeading"
          v-text="$t('segmaflotApp.cotizacion.home.createOrEditLabel')"
        >
          Create or edit a Cotizacion
        </h2>
        <div>
          <div class="form-group" v-if="cotizacion.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cotizacion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.fecha')" for="cotizacion-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cotizacion-fecha"
                  v-model="$v.cotizacion.fecha.$model"
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
                id="cotizacion-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.cotizacion.fecha.$invalid, invalid: $v.cotizacion.fecha.$invalid }"
                v-model="$v.cotizacion.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.createByUser')" for="cotizacion-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="cotizacion-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.cotizacion.createByUser.$invalid, invalid: $v.cotizacion.createByUser.$invalid }"
              v-model="$v.cotizacion.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.createdOn')" for="cotizacion-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="cotizacion-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.cotizacion.createdOn.$invalid, invalid: $v.cotizacion.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.cotizacion.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.modifyByUser')" for="cotizacion-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="cotizacion-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.cotizacion.modifyByUser.$invalid, invalid: $v.cotizacion.modifyByUser.$invalid }"
              v-model="$v.cotizacion.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.modifiedOn')" for="cotizacion-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="cotizacion-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.cotizacion.modifiedOn.$invalid, invalid: $v.cotizacion.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.cotizacion.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cotizacion.auditStatus')" for="cotizacion-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="cotizacion-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.cotizacion.auditStatus.$invalid, invalid: $v.cotizacion.auditStatus.$invalid }"
              v-model="$v.cotizacion.auditStatus.$model"
            />
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
            :disabled="$v.cotizacion.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cotizacion-update.component.ts"></script>
