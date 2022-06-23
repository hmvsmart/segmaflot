<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.cliente.home.createOrEditLabel"
          data-cy="ClienteCreateUpdateHeading"
          v-text="$t('segmaflotApp.cliente.home.createOrEditLabel')"
        >
          Create or edit a Cliente
        </h2>
        <div>
          <div class="form-group" v-if="cliente.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cliente.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.fecha')" for="cliente-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cliente-fecha"
                  v-model="$v.cliente.fecha.$model"
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
                id="cliente-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.cliente.fecha.$invalid, invalid: $v.cliente.fecha.$invalid }"
                v-model="$v.cliente.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.observaciones')" for="cliente-observaciones"
              >Observaciones</label
            >
            <div>
              <div v-if="cliente.observaciones" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(cliente.observacionesContentType, cliente.observaciones)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ cliente.observacionesContentType }}, {{ byteSize(cliente.observaciones) }}</span>
                <button
                  type="button"
                  v-on:click="
                    cliente.observaciones = null;
                    cliente.observacionesContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_observaciones"
                id="file_observaciones"
                data-cy="observaciones"
                v-on:change="setFileData($event, cliente, 'observaciones', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="observaciones"
              id="cliente-observaciones"
              data-cy="observaciones"
              :class="{ valid: !$v.cliente.observaciones.$invalid, invalid: $v.cliente.observaciones.$invalid }"
              v-model="$v.cliente.observaciones.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="observacionesContentType"
              id="cliente-observacionesContentType"
              v-model="cliente.observacionesContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.createByUser')" for="cliente-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="cliente-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.cliente.createByUser.$invalid, invalid: $v.cliente.createByUser.$invalid }"
              v-model="$v.cliente.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.createdOn')" for="cliente-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="cliente-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.cliente.createdOn.$invalid, invalid: $v.cliente.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.cliente.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.modifyByUser')" for="cliente-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="cliente-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.cliente.modifyByUser.$invalid, invalid: $v.cliente.modifyByUser.$invalid }"
              v-model="$v.cliente.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.modifiedOn')" for="cliente-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="cliente-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.cliente.modifiedOn.$invalid, invalid: $v.cliente.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.cliente.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.auditStatus')" for="cliente-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="cliente-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.cliente.auditStatus.$invalid, invalid: $v.cliente.auditStatus.$invalid }"
              v-model="$v.cliente.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cliente.persona')" for="cliente-persona">Persona</label>
            <select class="form-control" id="cliente-persona" data-cy="persona" name="persona" v-model="cliente.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cliente.persona && personaOption.id === cliente.persona.id ? cliente.persona : personaOption"
                v-for="personaOption in personas"
                :key="personaOption.id"
              >
                {{ personaOption.id }}
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
            :disabled="$v.cliente.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cliente-update.component.ts"></script>
