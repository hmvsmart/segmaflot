<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.statusCita.home.createOrEditLabel"
          data-cy="StatusCitaCreateUpdateHeading"
          v-text="$t('segmaflotApp.statusCita.home.createOrEditLabel')"
        >
          Create or edit a StatusCita
        </h2>
        <div>
          <div class="form-group" v-if="statusCita.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="statusCita.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.fecha')" for="status-cita-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="status-cita-fecha"
                  v-model="$v.statusCita.fecha.$model"
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
                id="status-cita-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.statusCita.fecha.$invalid, invalid: $v.statusCita.fecha.$invalid }"
                v-model="$v.statusCita.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.status')" for="status-cita-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.statusCita.status.$invalid, invalid: $v.statusCita.status.$invalid }"
              v-model="$v.statusCita.status.$model"
              id="status-cita-status"
              data-cy="status"
            >
              <option
                v-for="tipoStatusCita in tipoStatusCitaValues"
                :key="tipoStatusCita"
                v-bind:value="tipoStatusCita"
                v-bind:label="$t('segmaflotApp.TipoStatusCita.' + tipoStatusCita)"
              >
                {{ tipoStatusCita }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.createByUser')" for="status-cita-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="status-cita-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.statusCita.createByUser.$invalid, invalid: $v.statusCita.createByUser.$invalid }"
              v-model="$v.statusCita.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.createdOn')" for="status-cita-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="status-cita-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.statusCita.createdOn.$invalid, invalid: $v.statusCita.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.statusCita.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.modifyByUser')" for="status-cita-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="status-cita-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.statusCita.modifyByUser.$invalid, invalid: $v.statusCita.modifyByUser.$invalid }"
              v-model="$v.statusCita.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.modifiedOn')" for="status-cita-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="status-cita-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.statusCita.modifiedOn.$invalid, invalid: $v.statusCita.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.statusCita.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.auditStatus')" for="status-cita-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="status-cita-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.statusCita.auditStatus.$invalid, invalid: $v.statusCita.auditStatus.$invalid }"
              v-model="$v.statusCita.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.statusCita.cita')" for="status-cita-cita">Cita</label>
            <select class="form-control" id="status-cita-cita" data-cy="cita" name="cita" v-model="statusCita.cita">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="statusCita.cita && citaOption.id === statusCita.cita.id ? statusCita.cita : citaOption"
                v-for="citaOption in citas"
                :key="citaOption.id"
              >
                {{ citaOption.id }}
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
            :disabled="$v.statusCita.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./status-cita-update.component.ts"></script>
