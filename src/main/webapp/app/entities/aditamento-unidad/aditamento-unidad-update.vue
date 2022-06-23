<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.aditamentoUnidad.home.createOrEditLabel"
          data-cy="AditamentoUnidadCreateUpdateHeading"
          v-text="$t('segmaflotApp.aditamentoUnidad.home.createOrEditLabel')"
        >
          Create or edit a AditamentoUnidad
        </h2>
        <div>
          <div class="form-group" v-if="aditamentoUnidad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="aditamentoUnidad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.fecha')" for="aditamento-unidad-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="aditamento-unidad-fecha"
                  v-model="$v.aditamentoUnidad.fecha.$model"
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
                id="aditamento-unidad-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.aditamentoUnidad.fecha.$invalid, invalid: $v.aditamentoUnidad.fecha.$invalid }"
                v-model="$v.aditamentoUnidad.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.numeroSerie')" for="aditamento-unidad-numeroSerie"
              >Numero Serie</label
            >
            <input
              type="text"
              class="form-control"
              name="numeroSerie"
              id="aditamento-unidad-numeroSerie"
              data-cy="numeroSerie"
              :class="{ valid: !$v.aditamentoUnidad.numeroSerie.$invalid, invalid: $v.aditamentoUnidad.numeroSerie.$invalid }"
              v-model="$v.aditamentoUnidad.numeroSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.createByUser')" for="aditamento-unidad-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="aditamento-unidad-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.aditamentoUnidad.createByUser.$invalid, invalid: $v.aditamentoUnidad.createByUser.$invalid }"
              v-model="$v.aditamentoUnidad.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.createdOn')" for="aditamento-unidad-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="aditamento-unidad-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.aditamentoUnidad.createdOn.$invalid, invalid: $v.aditamentoUnidad.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.aditamentoUnidad.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.modifyByUser')" for="aditamento-unidad-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="aditamento-unidad-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.aditamentoUnidad.modifyByUser.$invalid, invalid: $v.aditamentoUnidad.modifyByUser.$invalid }"
              v-model="$v.aditamentoUnidad.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.modifiedOn')" for="aditamento-unidad-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="aditamento-unidad-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.aditamentoUnidad.modifiedOn.$invalid, invalid: $v.aditamentoUnidad.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.aditamentoUnidad.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.auditStatus')" for="aditamento-unidad-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="aditamento-unidad-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.aditamentoUnidad.auditStatus.$invalid, invalid: $v.aditamentoUnidad.auditStatus.$invalid }"
              v-model="$v.aditamentoUnidad.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.aditamento')" for="aditamento-unidad-aditamento"
              >Aditamento</label
            >
            <select
              class="form-control"
              id="aditamento-unidad-aditamento"
              data-cy="aditamento"
              name="aditamento"
              v-model="aditamentoUnidad.aditamento"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  aditamentoUnidad.aditamento && aditamentoOption.id === aditamentoUnidad.aditamento.id
                    ? aditamentoUnidad.aditamento
                    : aditamentoOption
                "
                v-for="aditamentoOption in aditamentos"
                :key="aditamentoOption.id"
              >
                {{ aditamentoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamentoUnidad.unidad')" for="aditamento-unidad-unidad"
              >Unidad</label
            >
            <select class="form-control" id="aditamento-unidad-unidad" data-cy="unidad" name="unidad" v-model="aditamentoUnidad.unidad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  aditamentoUnidad.unidad && unidadOption.id === aditamentoUnidad.unidad.id ? aditamentoUnidad.unidad : unidadOption
                "
                v-for="unidadOption in unidads"
                :key="unidadOption.id"
              >
                {{ unidadOption.id }}
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
            :disabled="$v.aditamentoUnidad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./aditamento-unidad-update.component.ts"></script>
