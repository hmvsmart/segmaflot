<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.vehiculo.home.createOrEditLabel"
          data-cy="VehiculoCreateUpdateHeading"
          v-text="$t('segmaflotApp.vehiculo.home.createOrEditLabel')"
        >
          Create or edit a Vehiculo
        </h2>
        <div>
          <div class="form-group" v-if="vehiculo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vehiculo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.marca')" for="vehiculo-marca">Marca</label>
            <input
              type="text"
              class="form-control"
              name="marca"
              id="vehiculo-marca"
              data-cy="marca"
              :class="{ valid: !$v.vehiculo.marca.$invalid, invalid: $v.vehiculo.marca.$invalid }"
              v-model="$v.vehiculo.marca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.submarca')" for="vehiculo-submarca">Submarca</label>
            <input
              type="text"
              class="form-control"
              name="submarca"
              id="vehiculo-submarca"
              data-cy="submarca"
              :class="{ valid: !$v.vehiculo.submarca.$invalid, invalid: $v.vehiculo.submarca.$invalid }"
              v-model="$v.vehiculo.submarca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.modelo')" for="vehiculo-modelo">Modelo</label>
            <input
              type="text"
              class="form-control"
              name="modelo"
              id="vehiculo-modelo"
              data-cy="modelo"
              :class="{ valid: !$v.vehiculo.modelo.$invalid, invalid: $v.vehiculo.modelo.$invalid }"
              v-model="$v.vehiculo.modelo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.generacion')" for="vehiculo-generacion">Generacion</label>
            <input
              type="text"
              class="form-control"
              name="generacion"
              id="vehiculo-generacion"
              data-cy="generacion"
              :class="{ valid: !$v.vehiculo.generacion.$invalid, invalid: $v.vehiculo.generacion.$invalid }"
              v-model="$v.vehiculo.generacion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.tipoVehiculo')" for="vehiculo-tipoVehiculo"
              >Tipo Vehiculo</label
            >
            <input
              type="text"
              class="form-control"
              name="tipoVehiculo"
              id="vehiculo-tipoVehiculo"
              data-cy="tipoVehiculo"
              :class="{ valid: !$v.vehiculo.tipoVehiculo.$invalid, invalid: $v.vehiculo.tipoVehiculo.$invalid }"
              v-model="$v.vehiculo.tipoVehiculo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.createByUser')" for="vehiculo-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="vehiculo-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.vehiculo.createByUser.$invalid, invalid: $v.vehiculo.createByUser.$invalid }"
              v-model="$v.vehiculo.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.createdOn')" for="vehiculo-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="vehiculo-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.vehiculo.createdOn.$invalid, invalid: $v.vehiculo.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.vehiculo.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.modifyByUser')" for="vehiculo-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="vehiculo-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.vehiculo.modifyByUser.$invalid, invalid: $v.vehiculo.modifyByUser.$invalid }"
              v-model="$v.vehiculo.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.modifiedOn')" for="vehiculo-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="vehiculo-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.vehiculo.modifiedOn.$invalid, invalid: $v.vehiculo.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.vehiculo.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculo.auditStatus')" for="vehiculo-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="vehiculo-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.vehiculo.auditStatus.$invalid, invalid: $v.vehiculo.auditStatus.$invalid }"
              v-model="$v.vehiculo.auditStatus.$model"
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
            :disabled="$v.vehiculo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vehiculo-update.component.ts"></script>
