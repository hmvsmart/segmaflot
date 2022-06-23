<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.direccionPersona.home.createOrEditLabel"
          data-cy="DireccionPersonaCreateUpdateHeading"
          v-text="$t('segmaflotApp.direccionPersona.home.createOrEditLabel')"
        >
          Create or edit a DireccionPersona
        </h2>
        <div>
          <div class="form-group" v-if="direccionPersona.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="direccionPersona.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.fecha')" for="direccion-persona-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="direccion-persona-fecha"
                  v-model="$v.direccionPersona.fecha.$model"
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
                id="direccion-persona-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.direccionPersona.fecha.$invalid, invalid: $v.direccionPersona.fecha.$invalid }"
                v-model="$v.direccionPersona.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.status')" for="direccion-persona-status"
              >Status</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="direccion-persona-status"
              data-cy="status"
              :class="{ valid: !$v.direccionPersona.status.$invalid, invalid: $v.direccionPersona.status.$invalid }"
              v-model="$v.direccionPersona.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.createByUser')" for="direccion-persona-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="direccion-persona-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.direccionPersona.createByUser.$invalid, invalid: $v.direccionPersona.createByUser.$invalid }"
              v-model="$v.direccionPersona.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.createdOn')" for="direccion-persona-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="direccion-persona-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.direccionPersona.createdOn.$invalid, invalid: $v.direccionPersona.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.direccionPersona.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.modifyByUser')" for="direccion-persona-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="direccion-persona-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.direccionPersona.modifyByUser.$invalid, invalid: $v.direccionPersona.modifyByUser.$invalid }"
              v-model="$v.direccionPersona.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.modifiedOn')" for="direccion-persona-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="direccion-persona-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.direccionPersona.modifiedOn.$invalid, invalid: $v.direccionPersona.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.direccionPersona.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.auditStatus')" for="direccion-persona-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="direccion-persona-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.direccionPersona.auditStatus.$invalid, invalid: $v.direccionPersona.auditStatus.$invalid }"
              v-model="$v.direccionPersona.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.direccion')" for="direccion-persona-direccion"
              >Direccion</label
            >
            <select
              class="form-control"
              id="direccion-persona-direccion"
              data-cy="direccion"
              name="direccion"
              v-model="direccionPersona.direccion"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  direccionPersona.direccion && direccionOption.id === direccionPersona.direccion.id
                    ? direccionPersona.direccion
                    : direccionOption
                "
                v-for="direccionOption in direccions"
                :key="direccionOption.id"
              >
                {{ direccionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccionPersona.persona')" for="direccion-persona-persona"
              >Persona</label
            >
            <select class="form-control" id="direccion-persona-persona" data-cy="persona" name="persona" v-model="direccionPersona.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  direccionPersona.persona && personaOption.id === direccionPersona.persona.id ? direccionPersona.persona : personaOption
                "
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
            :disabled="$v.direccionPersona.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./direccion-persona-update.component.ts"></script>
