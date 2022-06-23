<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.operadorUnidad.home.createOrEditLabel"
          data-cy="OperadorUnidadCreateUpdateHeading"
          v-text="$t('segmaflotApp.operadorUnidad.home.createOrEditLabel')"
        >
          Create or edit a OperadorUnidad
        </h2>
        <div>
          <div class="form-group" v-if="operadorUnidad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="operadorUnidad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.fecha')" for="operador-unidad-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="operador-unidad-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.operadorUnidad.fecha.$invalid, invalid: $v.operadorUnidad.fecha.$invalid }"
                :value="convertDateTimeFromServer($v.operadorUnidad.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.rol')" for="operador-unidad-rol">Rol</label>
            <input
              type="text"
              class="form-control"
              name="rol"
              id="operador-unidad-rol"
              data-cy="rol"
              :class="{ valid: !$v.operadorUnidad.rol.$invalid, invalid: $v.operadorUnidad.rol.$invalid }"
              v-model="$v.operadorUnidad.rol.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.status')" for="operador-unidad-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="operador-unidad-status"
              data-cy="status"
              :class="{ valid: !$v.operadorUnidad.status.$invalid, invalid: $v.operadorUnidad.status.$invalid }"
              v-model="$v.operadorUnidad.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.createByUser')" for="operador-unidad-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="operador-unidad-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.operadorUnidad.createByUser.$invalid, invalid: $v.operadorUnidad.createByUser.$invalid }"
              v-model="$v.operadorUnidad.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.createdOn')" for="operador-unidad-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="operador-unidad-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.operadorUnidad.createdOn.$invalid, invalid: $v.operadorUnidad.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.operadorUnidad.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.modifyByUser')" for="operador-unidad-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="operador-unidad-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.operadorUnidad.modifyByUser.$invalid, invalid: $v.operadorUnidad.modifyByUser.$invalid }"
              v-model="$v.operadorUnidad.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.modifiedOn')" for="operador-unidad-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="operador-unidad-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.operadorUnidad.modifiedOn.$invalid, invalid: $v.operadorUnidad.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.operadorUnidad.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.auditStatus')" for="operador-unidad-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="operador-unidad-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.operadorUnidad.auditStatus.$invalid, invalid: $v.operadorUnidad.auditStatus.$invalid }"
              v-model="$v.operadorUnidad.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.unidasViaje')" for="operador-unidad-unidasViaje"
              >Unidas Viaje</label
            >
            <select
              class="form-control"
              id="operador-unidad-unidasViaje"
              data-cy="unidasViaje"
              name="unidasViaje"
              v-model="operadorUnidad.unidasViaje"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operadorUnidad.unidasViaje && unidadViajeOption.id === operadorUnidad.unidasViaje.id
                    ? operadorUnidad.unidasViaje
                    : unidadViajeOption
                "
                v-for="unidadViajeOption in unidadViajes"
                :key="unidadViajeOption.id"
              >
                {{ unidadViajeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.operadorUnidad.empleado')" for="operador-unidad-empleado"
              >Empleado</label
            >
            <select class="form-control" id="operador-unidad-empleado" data-cy="empleado" name="empleado" v-model="operadorUnidad.empleado">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operadorUnidad.empleado && empleadoOption.id === operadorUnidad.empleado.id ? operadorUnidad.empleado : empleadoOption
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
            :disabled="$v.operadorUnidad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./operador-unidad-update.component.ts"></script>
