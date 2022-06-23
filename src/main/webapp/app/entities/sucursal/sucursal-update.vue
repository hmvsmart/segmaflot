<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.sucursal.home.createOrEditLabel"
          data-cy="SucursalCreateUpdateHeading"
          v-text="$t('segmaflotApp.sucursal.home.createOrEditLabel')"
        >
          Create or edit a Sucursal
        </h2>
        <div>
          <div class="form-group" v-if="sucursal.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="sucursal.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.nombre')" for="sucursal-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="sucursal-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.sucursal.nombre.$invalid, invalid: $v.sucursal.nombre.$invalid }"
              v-model="$v.sucursal.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.tipoSucursal')" for="sucursal-tipoSucursal"
              >Tipo Sucursal</label
            >
            <input
              type="text"
              class="form-control"
              name="tipoSucursal"
              id="sucursal-tipoSucursal"
              data-cy="tipoSucursal"
              :class="{ valid: !$v.sucursal.tipoSucursal.$invalid, invalid: $v.sucursal.tipoSucursal.$invalid }"
              v-model="$v.sucursal.tipoSucursal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.telefono')" for="sucursal-telefono">Telefono</label>
            <input
              type="text"
              class="form-control"
              name="telefono"
              id="sucursal-telefono"
              data-cy="telefono"
              :class="{ valid: !$v.sucursal.telefono.$invalid, invalid: $v.sucursal.telefono.$invalid }"
              v-model="$v.sucursal.telefono.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.status')" for="sucursal-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="sucursal-status"
              data-cy="status"
              :class="{ valid: !$v.sucursal.status.$invalid, invalid: $v.sucursal.status.$invalid }"
              v-model="$v.sucursal.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.createByUser')" for="sucursal-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="sucursal-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.sucursal.createByUser.$invalid, invalid: $v.sucursal.createByUser.$invalid }"
              v-model="$v.sucursal.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.createdOn')" for="sucursal-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="sucursal-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.sucursal.createdOn.$invalid, invalid: $v.sucursal.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.sucursal.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.modifyByUser')" for="sucursal-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="sucursal-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.sucursal.modifyByUser.$invalid, invalid: $v.sucursal.modifyByUser.$invalid }"
              v-model="$v.sucursal.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.modifiedOn')" for="sucursal-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="sucursal-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.sucursal.modifiedOn.$invalid, invalid: $v.sucursal.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.sucursal.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.auditStatus')" for="sucursal-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="sucursal-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.sucursal.auditStatus.$invalid, invalid: $v.sucursal.auditStatus.$invalid }"
              v-model="$v.sucursal.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.sucursal.empresa')" for="sucursal-empresa">Empresa</label>
            <select class="form-control" id="sucursal-empresa" data-cy="empresa" name="empresa" v-model="sucursal.empresa">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="sucursal.empresa && personaMoralOption.id === sucursal.empresa.id ? sucursal.empresa : personaMoralOption"
                v-for="personaMoralOption in personaMorals"
                :key="personaMoralOption.id"
              >
                {{ personaMoralOption.id }}
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
            :disabled="$v.sucursal.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./sucursal-update.component.ts"></script>
