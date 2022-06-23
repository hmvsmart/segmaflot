<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.licenciaManejo.home.createOrEditLabel"
          data-cy="LicenciaManejoCreateUpdateHeading"
          v-text="$t('segmaflotApp.licenciaManejo.home.createOrEditLabel')"
        >
          Create or edit a LicenciaManejo
        </h2>
        <div>
          <div class="form-group" v-if="licenciaManejo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="licenciaManejo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.fecha')" for="licencia-manejo-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="licencia-manejo-fecha"
                  v-model="$v.licenciaManejo.fecha.$model"
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
                id="licencia-manejo-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.licenciaManejo.fecha.$invalid, invalid: $v.licenciaManejo.fecha.$invalid }"
                v-model="$v.licenciaManejo.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.tipo')" for="licencia-manejo-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="licencia-manejo-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.licenciaManejo.tipo.$invalid, invalid: $v.licenciaManejo.tipo.$invalid }"
              v-model="$v.licenciaManejo.tipo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.status')" for="licencia-manejo-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="licencia-manejo-status"
              data-cy="status"
              :class="{ valid: !$v.licenciaManejo.status.$invalid, invalid: $v.licenciaManejo.status.$invalid }"
              v-model="$v.licenciaManejo.status.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.licenciaManejo.fechaExpiracion')"
              for="licencia-manejo-fechaExpiracion"
              >Fecha Expiracion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="licencia-manejo-fechaExpiracion"
                  v-model="$v.licenciaManejo.fechaExpiracion.$model"
                  name="fechaExpiracion"
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
                id="licencia-manejo-fechaExpiracion"
                data-cy="fechaExpiracion"
                type="text"
                class="form-control"
                name="fechaExpiracion"
                :class="{ valid: !$v.licenciaManejo.fechaExpiracion.$invalid, invalid: $v.licenciaManejo.fechaExpiracion.$invalid }"
                v-model="$v.licenciaManejo.fechaExpiracion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.createByUser')" for="licencia-manejo-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="licencia-manejo-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.licenciaManejo.createByUser.$invalid, invalid: $v.licenciaManejo.createByUser.$invalid }"
              v-model="$v.licenciaManejo.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.createdOn')" for="licencia-manejo-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="licencia-manejo-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.licenciaManejo.createdOn.$invalid, invalid: $v.licenciaManejo.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.licenciaManejo.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.modifyByUser')" for="licencia-manejo-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="licencia-manejo-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.licenciaManejo.modifyByUser.$invalid, invalid: $v.licenciaManejo.modifyByUser.$invalid }"
              v-model="$v.licenciaManejo.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.modifiedOn')" for="licencia-manejo-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="licencia-manejo-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.licenciaManejo.modifiedOn.$invalid, invalid: $v.licenciaManejo.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.licenciaManejo.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.auditStatus')" for="licencia-manejo-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="licencia-manejo-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.licenciaManejo.auditStatus.$invalid, invalid: $v.licenciaManejo.auditStatus.$invalid }"
              v-model="$v.licenciaManejo.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.licenciaManejo.empleado')" for="licencia-manejo-empleado"
              >Empleado</label
            >
            <select class="form-control" id="licencia-manejo-empleado" data-cy="empleado" name="empleado" v-model="licenciaManejo.empleado">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  licenciaManejo.empleado && empleadoOption.id === licenciaManejo.empleado.id ? licenciaManejo.empleado : empleadoOption
                "
                v-for="empleadoOption in empleados"
                :key="empleadoOption.id"
              >
                {{ empleadoOption.id }}
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
            :disabled="$v.licenciaManejo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./licencia-manejo-update.component.ts"></script>
