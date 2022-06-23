<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.servicio.home.createOrEditLabel"
          data-cy="ServicioCreateUpdateHeading"
          v-text="$t('segmaflotApp.servicio.home.createOrEditLabel')"
        >
          Create or edit a Servicio
        </h2>
        <div>
          <div class="form-group" v-if="servicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="servicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.nombre')" for="servicio-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="servicio-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.servicio.nombre.$invalid, invalid: $v.servicio.nombre.$invalid }"
              v-model="$v.servicio.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.descripcion')" for="servicio-descripcion"
              >Descripcion</label
            >
            <div>
              <div v-if="servicio.descripcion" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(servicio.descripcionContentType, servicio.descripcion)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ servicio.descripcionContentType }}, {{ byteSize(servicio.descripcion) }}</span>
                <button
                  type="button"
                  v-on:click="
                    servicio.descripcion = null;
                    servicio.descripcionContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_descripcion"
                id="file_descripcion"
                data-cy="descripcion"
                v-on:change="setFileData($event, servicio, 'descripcion', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="descripcion"
              id="servicio-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.servicio.descripcion.$invalid, invalid: $v.servicio.descripcion.$invalid }"
              v-model="$v.servicio.descripcion.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="descripcionContentType"
              id="servicio-descripcionContentType"
              v-model="servicio.descripcionContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.duracionEstimada')" for="servicio-duracionEstimada"
              >Duracion Estimada</label
            >
            <div class="d-flex">
              <input
                id="servicio-duracionEstimada"
                data-cy="duracionEstimada"
                type="datetime-local"
                class="form-control"
                name="duracionEstimada"
                :class="{ valid: !$v.servicio.duracionEstimada.$invalid, invalid: $v.servicio.duracionEstimada.$invalid }"
                :value="convertDateTimeFromServer($v.servicio.duracionEstimada.$model)"
                @change="updateInstantField('duracionEstimada', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.createByUser')" for="servicio-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="servicio-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.servicio.createByUser.$invalid, invalid: $v.servicio.createByUser.$invalid }"
              v-model="$v.servicio.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.createdOn')" for="servicio-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="servicio-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.servicio.createdOn.$invalid, invalid: $v.servicio.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.servicio.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.modifyByUser')" for="servicio-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="servicio-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.servicio.modifyByUser.$invalid, invalid: $v.servicio.modifyByUser.$invalid }"
              v-model="$v.servicio.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.modifiedOn')" for="servicio-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="servicio-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.servicio.modifiedOn.$invalid, invalid: $v.servicio.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.servicio.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.servicio.auditStatus')" for="servicio-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="servicio-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.servicio.auditStatus.$invalid, invalid: $v.servicio.auditStatus.$invalid }"
              v-model="$v.servicio.auditStatus.$model"
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
            :disabled="$v.servicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./servicio-update.component.ts"></script>
