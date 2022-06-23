<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.cita.home.createOrEditLabel"
          data-cy="CitaCreateUpdateHeading"
          v-text="$t('segmaflotApp.cita.home.createOrEditLabel')"
        >
          Create or edit a Cita
        </h2>
        <div>
          <div class="form-group" v-if="cita.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cita.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.fecha')" for="cita-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cita-fecha"
                  v-model="$v.cita.fecha.$model"
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
                id="cita-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.cita.fecha.$invalid, invalid: $v.cita.fecha.$invalid }"
                v-model="$v.cita.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.horaInicio')" for="cita-horaInicio">Hora Inicio</label>
            <div class="d-flex">
              <input
                id="cita-horaInicio"
                data-cy="horaInicio"
                type="datetime-local"
                class="form-control"
                name="horaInicio"
                :class="{ valid: !$v.cita.horaInicio.$invalid, invalid: $v.cita.horaInicio.$invalid }"
                :value="convertDateTimeFromServer($v.cita.horaInicio.$model)"
                @change="updateInstantField('horaInicio', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.horaFin')" for="cita-horaFin">Hora Fin</label>
            <div class="d-flex">
              <input
                id="cita-horaFin"
                data-cy="horaFin"
                type="datetime-local"
                class="form-control"
                name="horaFin"
                :class="{ valid: !$v.cita.horaFin.$invalid, invalid: $v.cita.horaFin.$invalid }"
                :value="convertDateTimeFromServer($v.cita.horaFin.$model)"
                @change="updateInstantField('horaFin', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.area')" for="cita-area">Area</label>
            <input
              type="text"
              class="form-control"
              name="area"
              id="cita-area"
              data-cy="area"
              :class="{ valid: !$v.cita.area.$invalid, invalid: $v.cita.area.$invalid }"
              v-model="$v.cita.area.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.identificador')" for="cita-identificador">Identificador</label>
            <input
              type="text"
              class="form-control"
              name="identificador"
              id="cita-identificador"
              data-cy="identificador"
              :class="{ valid: !$v.cita.identificador.$invalid, invalid: $v.cita.identificador.$invalid }"
              v-model="$v.cita.identificador.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.createByUser')" for="cita-createByUser">Create By User</label>
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="cita-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.cita.createByUser.$invalid, invalid: $v.cita.createByUser.$invalid }"
              v-model="$v.cita.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.createdOn')" for="cita-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="cita-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.cita.createdOn.$invalid, invalid: $v.cita.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.cita.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.modifyByUser')" for="cita-modifyByUser">Modify By User</label>
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="cita-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.cita.modifyByUser.$invalid, invalid: $v.cita.modifyByUser.$invalid }"
              v-model="$v.cita.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.modifiedOn')" for="cita-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="cita-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.cita.modifiedOn.$invalid, invalid: $v.cita.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.cita.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cita.auditStatus')" for="cita-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="cita-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.cita.auditStatus.$invalid, invalid: $v.cita.auditStatus.$invalid }"
              v-model="$v.cita.auditStatus.$model"
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
            :disabled="$v.cita.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cita-update.component.ts"></script>
