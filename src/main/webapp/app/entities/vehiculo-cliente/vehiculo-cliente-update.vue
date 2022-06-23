<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.vehiculoCliente.home.createOrEditLabel"
          data-cy="VehiculoClienteCreateUpdateHeading"
          v-text="$t('segmaflotApp.vehiculoCliente.home.createOrEditLabel')"
        >
          Create or edit a VehiculoCliente
        </h2>
        <div>
          <div class="form-group" v-if="vehiculoCliente.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vehiculoCliente.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.fecha')" for="vehiculo-cliente-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="vehiculo-cliente-fecha"
                  v-model="$v.vehiculoCliente.fecha.$model"
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
                id="vehiculo-cliente-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.vehiculoCliente.fecha.$invalid, invalid: $v.vehiculoCliente.fecha.$invalid }"
                v-model="$v.vehiculoCliente.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.numeroSerie')" for="vehiculo-cliente-numeroSerie"
              >Numero Serie</label
            >
            <input
              type="text"
              class="form-control"
              name="numeroSerie"
              id="vehiculo-cliente-numeroSerie"
              data-cy="numeroSerie"
              :class="{ valid: !$v.vehiculoCliente.numeroSerie.$invalid, invalid: $v.vehiculoCliente.numeroSerie.$invalid }"
              v-model="$v.vehiculoCliente.numeroSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.color')" for="vehiculo-cliente-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="vehiculo-cliente-color"
              data-cy="color"
              :class="{ valid: !$v.vehiculoCliente.color.$invalid, invalid: $v.vehiculoCliente.color.$invalid }"
              v-model="$v.vehiculoCliente.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.status')" for="vehiculo-cliente-status"
              >Status</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="vehiculo-cliente-status"
              data-cy="status"
              :class="{ valid: !$v.vehiculoCliente.status.$invalid, invalid: $v.vehiculoCliente.status.$invalid }"
              v-model="$v.vehiculoCliente.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.createByUser')" for="vehiculo-cliente-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="vehiculo-cliente-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.vehiculoCliente.createByUser.$invalid, invalid: $v.vehiculoCliente.createByUser.$invalid }"
              v-model="$v.vehiculoCliente.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.createdOn')" for="vehiculo-cliente-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="vehiculo-cliente-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.vehiculoCliente.createdOn.$invalid, invalid: $v.vehiculoCliente.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.vehiculoCliente.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.modifyByUser')" for="vehiculo-cliente-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="vehiculo-cliente-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.vehiculoCliente.modifyByUser.$invalid, invalid: $v.vehiculoCliente.modifyByUser.$invalid }"
              v-model="$v.vehiculoCliente.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.modifiedOn')" for="vehiculo-cliente-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="vehiculo-cliente-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.vehiculoCliente.modifiedOn.$invalid, invalid: $v.vehiculoCliente.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.vehiculoCliente.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.auditStatus')" for="vehiculo-cliente-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="vehiculo-cliente-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.vehiculoCliente.auditStatus.$invalid, invalid: $v.vehiculoCliente.auditStatus.$invalid }"
              v-model="$v.vehiculoCliente.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.vehiculoCliente.cliente')" for="vehiculo-cliente-cliente"
              >Cliente</label
            >
            <select class="form-control" id="vehiculo-cliente-cliente" data-cy="cliente" name="cliente" v-model="vehiculoCliente.cliente">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  vehiculoCliente.cliente && clienteOption.id === vehiculoCliente.cliente.id ? vehiculoCliente.cliente : clienteOption
                "
                v-for="clienteOption in clientes"
                :key="clienteOption.id"
              >
                {{ clienteOption.id }}
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
            :disabled="$v.vehiculoCliente.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vehiculo-cliente-update.component.ts"></script>
