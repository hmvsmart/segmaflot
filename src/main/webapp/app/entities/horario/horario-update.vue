<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.horario.home.createOrEditLabel"
          data-cy="HorarioCreateUpdateHeading"
          v-text="$t('segmaflotApp.horario.home.createOrEditLabel')"
        >
          Create or edit a Horario
        </h2>
        <div>
          <div class="form-group" v-if="horario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="horario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.fecha')" for="horario-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="horario-fecha"
                  v-model="$v.horario.fecha.$model"
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
                id="horario-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.horario.fecha.$invalid, invalid: $v.horario.fecha.$invalid }"
                v-model="$v.horario.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.status')" for="horario-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="horario-status"
              data-cy="status"
              :class="{ valid: !$v.horario.status.$invalid, invalid: $v.horario.status.$invalid }"
              v-model="$v.horario.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.createByUser')" for="horario-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="horario-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.horario.createByUser.$invalid, invalid: $v.horario.createByUser.$invalid }"
              v-model="$v.horario.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.createdOn')" for="horario-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="horario-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.horario.createdOn.$invalid, invalid: $v.horario.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.horario.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.modifyByUser')" for="horario-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="horario-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.horario.modifyByUser.$invalid, invalid: $v.horario.modifyByUser.$invalid }"
              v-model="$v.horario.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.modifiedOn')" for="horario-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="horario-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.horario.modifiedOn.$invalid, invalid: $v.horario.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.horario.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.auditStatus')" for="horario-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="horario-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.horario.auditStatus.$invalid, invalid: $v.horario.auditStatus.$invalid }"
              v-model="$v.horario.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.horario.empleado')" for="horario-empleado">Empleado</label>
            <select class="form-control" id="horario-empleado" data-cy="empleado" name="empleado" v-model="horario.empleado">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="horario.empleado && empleadoOption.id === horario.empleado.id ? horario.empleado : empleadoOption"
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
            :disabled="$v.horario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./horario-update.component.ts"></script>
