<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.unidad.home.createOrEditLabel"
          data-cy="UnidadCreateUpdateHeading"
          v-text="$t('segmaflotApp.unidad.home.createOrEditLabel')"
        >
          Create or edit a Unidad
        </h2>
        <div>
          <div class="form-group" v-if="unidad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="unidad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.fecha')" for="unidad-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="unidad-fecha"
                  v-model="$v.unidad.fecha.$model"
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
                id="unidad-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.unidad.fecha.$invalid, invalid: $v.unidad.fecha.$invalid }"
                v-model="$v.unidad.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.numeroSerie')" for="unidad-numeroSerie">Numero Serie</label>
            <input
              type="text"
              class="form-control"
              name="numeroSerie"
              id="unidad-numeroSerie"
              data-cy="numeroSerie"
              :class="{ valid: !$v.unidad.numeroSerie.$invalid, invalid: $v.unidad.numeroSerie.$invalid }"
              v-model="$v.unidad.numeroSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.kmActual')" for="unidad-kmActual">Km Actual</label>
            <input
              type="number"
              class="form-control"
              name="kmActual"
              id="unidad-kmActual"
              data-cy="kmActual"
              :class="{ valid: !$v.unidad.kmActual.$invalid, invalid: $v.unidad.kmActual.$invalid }"
              v-model.number="$v.unidad.kmActual.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.tipoAdquisicion')" for="unidad-tipoAdquisicion"
              >Tipo Adquisicion</label
            >
            <input
              type="text"
              class="form-control"
              name="tipoAdquisicion"
              id="unidad-tipoAdquisicion"
              data-cy="tipoAdquisicion"
              :class="{ valid: !$v.unidad.tipoAdquisicion.$invalid, invalid: $v.unidad.tipoAdquisicion.$invalid }"
              v-model="$v.unidad.tipoAdquisicion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.imagen')" for="unidad-imagen">Imagen</label>
            <div>
              <div v-if="unidad.imagen" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(unidad.imagenContentType, unidad.imagen)" v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ unidad.imagenContentType }}, {{ byteSize(unidad.imagen) }}</span>
                <button
                  type="button"
                  v-on:click="
                    unidad.imagen = null;
                    unidad.imagenContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_imagen"
                id="file_imagen"
                data-cy="imagen"
                v-on:change="setFileData($event, unidad, 'imagen', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="imagen"
              id="unidad-imagen"
              data-cy="imagen"
              :class="{ valid: !$v.unidad.imagen.$invalid, invalid: $v.unidad.imagen.$invalid }"
              v-model="$v.unidad.imagen.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="imagenContentType"
              id="unidad-imagenContentType"
              v-model="unidad.imagenContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.color')" for="unidad-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="unidad-color"
              data-cy="color"
              :class="{ valid: !$v.unidad.color.$invalid, invalid: $v.unidad.color.$invalid }"
              v-model="$v.unidad.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.descripcion')" for="unidad-descripcion">Descripcion</label>
            <div>
              <div v-if="unidad.descripcion" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(unidad.descripcionContentType, unidad.descripcion)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ unidad.descripcionContentType }}, {{ byteSize(unidad.descripcion) }}</span>
                <button
                  type="button"
                  v-on:click="
                    unidad.descripcion = null;
                    unidad.descripcionContentType = null;
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
                v-on:change="setFileData($event, unidad, 'descripcion', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="descripcion"
              id="unidad-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.unidad.descripcion.$invalid, invalid: $v.unidad.descripcion.$invalid }"
              v-model="$v.unidad.descripcion.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="descripcionContentType"
              id="unidad-descripcionContentType"
              v-model="unidad.descripcionContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.status')" for="unidad-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="unidad-status"
              data-cy="status"
              :class="{ valid: !$v.unidad.status.$invalid, invalid: $v.unidad.status.$invalid }"
              v-model="$v.unidad.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.createByUser')" for="unidad-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="unidad-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.unidad.createByUser.$invalid, invalid: $v.unidad.createByUser.$invalid }"
              v-model="$v.unidad.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.createdOn')" for="unidad-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="unidad-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.unidad.createdOn.$invalid, invalid: $v.unidad.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidad.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.modifyByUser')" for="unidad-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="unidad-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.unidad.modifyByUser.$invalid, invalid: $v.unidad.modifyByUser.$invalid }"
              v-model="$v.unidad.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.modifiedOn')" for="unidad-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="unidad-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.unidad.modifiedOn.$invalid, invalid: $v.unidad.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidad.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.auditStatus')" for="unidad-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="unidad-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.unidad.auditStatus.$invalid, invalid: $v.unidad.auditStatus.$invalid }"
              v-model="$v.unidad.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidad.vehiculo')" for="unidad-vehiculo">Vehiculo</label>
            <select class="form-control" id="unidad-vehiculo" data-cy="vehiculo" name="vehiculo" v-model="unidad.vehiculo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="unidad.vehiculo && vehiculoOption.id === unidad.vehiculo.id ? unidad.vehiculo : vehiculoOption"
                v-for="vehiculoOption in vehiculos"
                :key="vehiculoOption.id"
              >
                {{ vehiculoOption.id }}
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
            :disabled="$v.unidad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./unidad-update.component.ts"></script>
