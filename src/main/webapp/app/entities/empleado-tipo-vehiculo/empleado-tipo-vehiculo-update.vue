<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.empleadoTipoVehiculo.home.createOrEditLabel"
          data-cy="EmpleadoTipoVehiculoCreateUpdateHeading"
          v-text="$t('segmaflotApp.empleadoTipoVehiculo.home.createOrEditLabel')"
        >
          Create or edit a EmpleadoTipoVehiculo
        </h2>
        <div>
          <div class="form-group" v-if="empleadoTipoVehiculo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="empleadoTipoVehiculo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleadoTipoVehiculo.fecha')" for="empleado-tipo-vehiculo-fecha"
              >Fecha</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="empleado-tipo-vehiculo-fecha"
                  v-model="$v.empleadoTipoVehiculo.fecha.$model"
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
                id="empleado-tipo-vehiculo-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.empleadoTipoVehiculo.fecha.$invalid, invalid: $v.empleadoTipoVehiculo.fecha.$invalid }"
                v-model="$v.empleadoTipoVehiculo.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleadoTipoVehiculo.status')" for="empleado-tipo-vehiculo-status"
              >Status</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="empleado-tipo-vehiculo-status"
              data-cy="status"
              :class="{ valid: !$v.empleadoTipoVehiculo.status.$invalid, invalid: $v.empleadoTipoVehiculo.status.$invalid }"
              v-model="$v.empleadoTipoVehiculo.status.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.createByUser')"
              for="empleado-tipo-vehiculo-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="empleado-tipo-vehiculo-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.empleadoTipoVehiculo.createByUser.$invalid, invalid: $v.empleadoTipoVehiculo.createByUser.$invalid }"
              v-model="$v.empleadoTipoVehiculo.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.createdOn')"
              for="empleado-tipo-vehiculo-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="empleado-tipo-vehiculo-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.empleadoTipoVehiculo.createdOn.$invalid, invalid: $v.empleadoTipoVehiculo.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.empleadoTipoVehiculo.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifyByUser')"
              for="empleado-tipo-vehiculo-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="empleado-tipo-vehiculo-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.empleadoTipoVehiculo.modifyByUser.$invalid, invalid: $v.empleadoTipoVehiculo.modifyByUser.$invalid }"
              v-model="$v.empleadoTipoVehiculo.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifiedOn')"
              for="empleado-tipo-vehiculo-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="empleado-tipo-vehiculo-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.empleadoTipoVehiculo.modifiedOn.$invalid, invalid: $v.empleadoTipoVehiculo.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.empleadoTipoVehiculo.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.auditStatus')"
              for="empleado-tipo-vehiculo-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="empleado-tipo-vehiculo-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.empleadoTipoVehiculo.auditStatus.$invalid, invalid: $v.empleadoTipoVehiculo.auditStatus.$invalid }"
              v-model="$v.empleadoTipoVehiculo.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.empleadoTipoVehiculo.empleado')"
              for="empleado-tipo-vehiculo-empleado"
              >Empleado</label
            >
            <select
              class="form-control"
              id="empleado-tipo-vehiculo-empleado"
              data-cy="empleado"
              name="empleado"
              v-model="empleadoTipoVehiculo.empleado"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  empleadoTipoVehiculo.empleado && empleadoOption.id === empleadoTipoVehiculo.empleado.id
                    ? empleadoTipoVehiculo.empleado
                    : empleadoOption
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
            :disabled="$v.empleadoTipoVehiculo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./empleado-tipo-vehiculo-update.component.ts"></script>
