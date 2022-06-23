<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.tipoContacto.home.createOrEditLabel"
          data-cy="TipoContactoCreateUpdateHeading"
          v-text="$t('segmaflotApp.tipoContacto.home.createOrEditLabel')"
        >
          Create or edit a TipoContacto
        </h2>
        <div>
          <div class="form-group" v-if="tipoContacto.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tipoContacto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.nombre')" for="tipo-contacto-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="tipo-contacto-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.tipoContacto.nombre.$invalid, invalid: $v.tipoContacto.nombre.$invalid }"
              v-model="$v.tipoContacto.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.createByUser')" for="tipo-contacto-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="tipo-contacto-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.tipoContacto.createByUser.$invalid, invalid: $v.tipoContacto.createByUser.$invalid }"
              v-model="$v.tipoContacto.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.createdOn')" for="tipo-contacto-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="tipo-contacto-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.tipoContacto.createdOn.$invalid, invalid: $v.tipoContacto.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.tipoContacto.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.modifyByUser')" for="tipo-contacto-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="tipo-contacto-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.tipoContacto.modifyByUser.$invalid, invalid: $v.tipoContacto.modifyByUser.$invalid }"
              v-model="$v.tipoContacto.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.modifiedOn')" for="tipo-contacto-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="tipo-contacto-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.tipoContacto.modifiedOn.$invalid, invalid: $v.tipoContacto.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.tipoContacto.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.tipoContacto.auditStatus')" for="tipo-contacto-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="tipo-contacto-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.tipoContacto.auditStatus.$invalid, invalid: $v.tipoContacto.auditStatus.$invalid }"
              v-model="$v.tipoContacto.auditStatus.$model"
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
            :disabled="$v.tipoContacto.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tipo-contacto-update.component.ts"></script>
