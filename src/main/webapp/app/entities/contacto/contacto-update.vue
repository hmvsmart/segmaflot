<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.contacto.home.createOrEditLabel"
          data-cy="ContactoCreateUpdateHeading"
          v-text="$t('segmaflotApp.contacto.home.createOrEditLabel')"
        >
          Create or edit a Contacto
        </h2>
        <div>
          <div class="form-group" v-if="contacto.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="contacto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.valor')" for="contacto-valor">Valor</label>
            <input
              type="text"
              class="form-control"
              name="valor"
              id="contacto-valor"
              data-cy="valor"
              :class="{ valid: !$v.contacto.valor.$invalid, invalid: $v.contacto.valor.$invalid }"
              v-model="$v.contacto.valor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.etiqueta')" for="contacto-etiqueta">Etiqueta</label>
            <input
              type="text"
              class="form-control"
              name="etiqueta"
              id="contacto-etiqueta"
              data-cy="etiqueta"
              :class="{ valid: !$v.contacto.etiqueta.$invalid, invalid: $v.contacto.etiqueta.$invalid }"
              v-model="$v.contacto.etiqueta.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.string')" for="contacto-string">String</label>
            <input
              type="checkbox"
              class="form-check"
              name="string"
              id="contacto-string"
              data-cy="string"
              :class="{ valid: !$v.contacto.string.$invalid, invalid: $v.contacto.string.$invalid }"
              v-model="$v.contacto.string.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.createByUser')" for="contacto-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="contacto-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.contacto.createByUser.$invalid, invalid: $v.contacto.createByUser.$invalid }"
              v-model="$v.contacto.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.createdOn')" for="contacto-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="contacto-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.contacto.createdOn.$invalid, invalid: $v.contacto.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.contacto.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.modifyByUser')" for="contacto-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="contacto-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.contacto.modifyByUser.$invalid, invalid: $v.contacto.modifyByUser.$invalid }"
              v-model="$v.contacto.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.modifiedOn')" for="contacto-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="contacto-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.contacto.modifiedOn.$invalid, invalid: $v.contacto.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.contacto.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.auditStatus')" for="contacto-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="contacto-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.contacto.auditStatus.$invalid, invalid: $v.contacto.auditStatus.$invalid }"
              v-model="$v.contacto.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.tipoContacto')" for="contacto-tipoContacto"
              >Tipo Contacto</label
            >
            <select
              class="form-control"
              id="contacto-tipoContacto"
              data-cy="tipoContacto"
              name="tipoContacto"
              v-model="contacto.tipoContacto"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  contacto.tipoContacto && tipoContactoOption.id === contacto.tipoContacto.id ? contacto.tipoContacto : tipoContactoOption
                "
                v-for="tipoContactoOption in tipoContactos"
                :key="tipoContactoOption.id"
              >
                {{ tipoContactoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.contacto.persona')" for="contacto-persona">Persona</label>
            <select class="form-control" id="contacto-persona" data-cy="persona" name="persona" v-model="contacto.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="contacto.persona && personaOption.id === contacto.persona.id ? contacto.persona : personaOption"
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
            :disabled="$v.contacto.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./contacto-update.component.ts"></script>
