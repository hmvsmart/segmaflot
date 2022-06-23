<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.personaFisica.home.createOrEditLabel"
          data-cy="PersonaFisicaCreateUpdateHeading"
          v-text="$t('segmaflotApp.personaFisica.home.createOrEditLabel')"
        >
          Create or edit a PersonaFisica
        </h2>
        <div>
          <div class="form-group" v-if="personaFisica.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="personaFisica.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.nombre')" for="persona-fisica-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="persona-fisica-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.personaFisica.nombre.$invalid, invalid: $v.personaFisica.nombre.$invalid }"
              v-model="$v.personaFisica.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.apaterno')" for="persona-fisica-apaterno"
              >Apaterno</label
            >
            <input
              type="text"
              class="form-control"
              name="apaterno"
              id="persona-fisica-apaterno"
              data-cy="apaterno"
              :class="{ valid: !$v.personaFisica.apaterno.$invalid, invalid: $v.personaFisica.apaterno.$invalid }"
              v-model="$v.personaFisica.apaterno.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.amaterno')" for="persona-fisica-amaterno"
              >Amaterno</label
            >
            <input
              type="text"
              class="form-control"
              name="amaterno"
              id="persona-fisica-amaterno"
              data-cy="amaterno"
              :class="{ valid: !$v.personaFisica.amaterno.$invalid, invalid: $v.personaFisica.amaterno.$invalid }"
              v-model="$v.personaFisica.amaterno.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.fechaNacimiento')" for="persona-fisica-fechaNacimiento"
              >Fecha Nacimiento</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="persona-fisica-fechaNacimiento"
                  v-model="$v.personaFisica.fechaNacimiento.$model"
                  name="fechaNacimiento"
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
                id="persona-fisica-fechaNacimiento"
                data-cy="fechaNacimiento"
                type="text"
                class="form-control"
                name="fechaNacimiento"
                :class="{ valid: !$v.personaFisica.fechaNacimiento.$invalid, invalid: $v.personaFisica.fechaNacimiento.$invalid }"
                v-model="$v.personaFisica.fechaNacimiento.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.curp')" for="persona-fisica-curp">Curp</label>
            <input
              type="text"
              class="form-control"
              name="curp"
              id="persona-fisica-curp"
              data-cy="curp"
              :class="{ valid: !$v.personaFisica.curp.$invalid, invalid: $v.personaFisica.curp.$invalid }"
              v-model="$v.personaFisica.curp.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.createByUser')" for="persona-fisica-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="persona-fisica-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.personaFisica.createByUser.$invalid, invalid: $v.personaFisica.createByUser.$invalid }"
              v-model="$v.personaFisica.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.createdOn')" for="persona-fisica-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="persona-fisica-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.personaFisica.createdOn.$invalid, invalid: $v.personaFisica.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.personaFisica.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.modifyByUser')" for="persona-fisica-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="persona-fisica-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.personaFisica.modifyByUser.$invalid, invalid: $v.personaFisica.modifyByUser.$invalid }"
              v-model="$v.personaFisica.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.modifiedOn')" for="persona-fisica-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="persona-fisica-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.personaFisica.modifiedOn.$invalid, invalid: $v.personaFisica.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.personaFisica.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.auditStatus')" for="persona-fisica-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="persona-fisica-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.personaFisica.auditStatus.$invalid, invalid: $v.personaFisica.auditStatus.$invalid }"
              v-model="$v.personaFisica.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaFisica.persona')" for="persona-fisica-persona">Persona</label>
            <select class="form-control" id="persona-fisica-persona" data-cy="persona" name="persona" v-model="personaFisica.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  personaFisica.persona && personaOption.id === personaFisica.persona.id ? personaFisica.persona : personaOption
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
            :disabled="$v.personaFisica.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./persona-fisica-update.component.ts"></script>
