<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.unidadServicio.home.createOrEditLabel"
          data-cy="UnidadServicioCreateUpdateHeading"
          v-text="$t('segmaflotApp.unidadServicio.home.createOrEditLabel')"
        >
          Create or edit a UnidadServicio
        </h2>
        <div>
          <div class="form-group" v-if="unidadServicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="unidadServicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.fecha')" for="unidad-servicio-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="unidad-servicio-fecha"
                  v-model="$v.unidadServicio.fecha.$model"
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
                id="unidad-servicio-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.unidadServicio.fecha.$invalid, invalid: $v.unidadServicio.fecha.$invalid }"
                v-model="$v.unidadServicio.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.totalEstimado')" for="unidad-servicio-totalEstimado"
              >Total Estimado</label
            >
            <input
              type="number"
              class="form-control"
              name="totalEstimado"
              id="unidad-servicio-totalEstimado"
              data-cy="totalEstimado"
              :class="{ valid: !$v.unidadServicio.totalEstimado.$invalid, invalid: $v.unidadServicio.totalEstimado.$invalid }"
              v-model.number="$v.unidadServicio.totalEstimado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.totalReal')" for="unidad-servicio-totalReal"
              >Total Real</label
            >
            <input
              type="number"
              class="form-control"
              name="totalReal"
              id="unidad-servicio-totalReal"
              data-cy="totalReal"
              :class="{ valid: !$v.unidadServicio.totalReal.$invalid, invalid: $v.unidadServicio.totalReal.$invalid }"
              v-model.number="$v.unidadServicio.totalReal.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.unidadServicio.observacionesGenerales')"
              for="unidad-servicio-observacionesGenerales"
              >Observaciones Generales</label
            >
            <div>
              <div v-if="unidadServicio.observacionesGenerales" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(unidadServicio.observacionesGeneralesContentType, unidadServicio.observacionesGenerales)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left"
                  >{{ unidadServicio.observacionesGeneralesContentType }}, {{ byteSize(unidadServicio.observacionesGenerales) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    unidadServicio.observacionesGenerales = null;
                    unidadServicio.observacionesGeneralesContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_observacionesGenerales"
                id="file_observacionesGenerales"
                data-cy="observacionesGenerales"
                v-on:change="setFileData($event, unidadServicio, 'observacionesGenerales', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="observacionesGenerales"
              id="unidad-servicio-observacionesGenerales"
              data-cy="observacionesGenerales"
              :class="{
                valid: !$v.unidadServicio.observacionesGenerales.$invalid,
                invalid: $v.unidadServicio.observacionesGenerales.$invalid,
              }"
              v-model="$v.unidadServicio.observacionesGenerales.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="observacionesGeneralesContentType"
              id="unidad-servicio-observacionesGeneralesContentType"
              v-model="unidadServicio.observacionesGeneralesContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.createByUser')" for="unidad-servicio-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="unidad-servicio-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.unidadServicio.createByUser.$invalid, invalid: $v.unidadServicio.createByUser.$invalid }"
              v-model="$v.unidadServicio.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.createdOn')" for="unidad-servicio-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="unidad-servicio-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.unidadServicio.createdOn.$invalid, invalid: $v.unidadServicio.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidadServicio.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.modifyByUser')" for="unidad-servicio-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="unidad-servicio-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.unidadServicio.modifyByUser.$invalid, invalid: $v.unidadServicio.modifyByUser.$invalid }"
              v-model="$v.unidadServicio.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.modifiedOn')" for="unidad-servicio-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="unidad-servicio-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.unidadServicio.modifiedOn.$invalid, invalid: $v.unidadServicio.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.unidadServicio.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.auditStatus')" for="unidad-servicio-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="unidad-servicio-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.unidadServicio.auditStatus.$invalid, invalid: $v.unidadServicio.auditStatus.$invalid }"
              v-model="$v.unidadServicio.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.unidadServicio.cliente')" for="unidad-servicio-cliente"
              >Cliente</label
            >
            <select class="form-control" id="unidad-servicio-cliente" data-cy="cliente" name="cliente" v-model="unidadServicio.cliente">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  unidadServicio.cliente && clienteOption.id === unidadServicio.cliente.id ? unidadServicio.cliente : clienteOption
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
            :disabled="$v.unidadServicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./unidad-servicio-update.component.ts"></script>
