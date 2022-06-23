<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.experienciaHabilidad.home.createOrEditLabel"
          data-cy="ExperienciaHabilidadCreateUpdateHeading"
          v-text="$t('segmaflotApp.experienciaHabilidad.home.createOrEditLabel')"
        >
          Create or edit a ExperienciaHabilidad
        </h2>
        <div>
          <div class="form-group" v-if="experienciaHabilidad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="experienciaHabilidad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.experienciaHabilidad.fecha')" for="experiencia-habilidad-fecha"
              >Fecha</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="experiencia-habilidad-fecha"
                  v-model="$v.experienciaHabilidad.fecha.$model"
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
                id="experiencia-habilidad-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.experienciaHabilidad.fecha.$invalid, invalid: $v.experienciaHabilidad.fecha.$invalid }"
                v-model="$v.experienciaHabilidad.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.descripcion')"
              for="experiencia-habilidad-descripcion"
              >Descripcion</label
            >
            <div>
              <div v-if="experienciaHabilidad.descripcion" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(experienciaHabilidad.descripcionContentType, experienciaHabilidad.descripcion)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left"
                  >{{ experienciaHabilidad.descripcionContentType }}, {{ byteSize(experienciaHabilidad.descripcion) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    experienciaHabilidad.descripcion = null;
                    experienciaHabilidad.descripcionContentType = null;
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
                v-on:change="setFileData($event, experienciaHabilidad, 'descripcion', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="descripcion"
              id="experiencia-habilidad-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.experienciaHabilidad.descripcion.$invalid, invalid: $v.experienciaHabilidad.descripcion.$invalid }"
              v-model="$v.experienciaHabilidad.descripcion.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="descripcionContentType"
              id="experiencia-habilidad-descripcionContentType"
              v-model="experienciaHabilidad.descripcionContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.createByUser')"
              for="experiencia-habilidad-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="experiencia-habilidad-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.experienciaHabilidad.createByUser.$invalid, invalid: $v.experienciaHabilidad.createByUser.$invalid }"
              v-model="$v.experienciaHabilidad.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.createdOn')"
              for="experiencia-habilidad-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="experiencia-habilidad-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.experienciaHabilidad.createdOn.$invalid, invalid: $v.experienciaHabilidad.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.experienciaHabilidad.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.modifyByUser')"
              for="experiencia-habilidad-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="experiencia-habilidad-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.experienciaHabilidad.modifyByUser.$invalid, invalid: $v.experienciaHabilidad.modifyByUser.$invalid }"
              v-model="$v.experienciaHabilidad.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.modifiedOn')"
              for="experiencia-habilidad-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="experiencia-habilidad-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.experienciaHabilidad.modifiedOn.$invalid, invalid: $v.experienciaHabilidad.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.experienciaHabilidad.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.experienciaHabilidad.auditStatus')"
              for="experiencia-habilidad-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="experiencia-habilidad-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.experienciaHabilidad.auditStatus.$invalid, invalid: $v.experienciaHabilidad.auditStatus.$invalid }"
              v-model="$v.experienciaHabilidad.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.experienciaHabilidad.empleado')" for="experiencia-habilidad-empleado"
              >Empleado</label
            >
            <select
              class="form-control"
              id="experiencia-habilidad-empleado"
              data-cy="empleado"
              name="empleado"
              v-model="experienciaHabilidad.empleado"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  experienciaHabilidad.empleado && empleadoOption.id === experienciaHabilidad.empleado.id
                    ? experienciaHabilidad.empleado
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
            :disabled="$v.experienciaHabilidad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./experiencia-habilidad-update.component.ts"></script>
