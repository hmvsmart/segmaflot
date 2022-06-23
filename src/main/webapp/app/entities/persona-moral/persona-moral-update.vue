<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.personaMoral.home.createOrEditLabel"
          data-cy="PersonaMoralCreateUpdateHeading"
          v-text="$t('segmaflotApp.personaMoral.home.createOrEditLabel')"
        >
          Create or edit a PersonaMoral
        </h2>
        <div>
          <div class="form-group" v-if="personaMoral.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="personaMoral.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.razonSocial')" for="persona-moral-razonSocial"
              >Razon Social</label
            >
            <input
              type="text"
              class="form-control"
              name="razonSocial"
              id="persona-moral-razonSocial"
              data-cy="razonSocial"
              :class="{ valid: !$v.personaMoral.razonSocial.$invalid, invalid: $v.personaMoral.razonSocial.$invalid }"
              v-model="$v.personaMoral.razonSocial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.rfc')" for="persona-moral-rfc">Rfc</label>
            <input
              type="text"
              class="form-control"
              name="rfc"
              id="persona-moral-rfc"
              data-cy="rfc"
              :class="{ valid: !$v.personaMoral.rfc.$invalid, invalid: $v.personaMoral.rfc.$invalid }"
              v-model="$v.personaMoral.rfc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.createByUser')" for="persona-moral-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="persona-moral-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.personaMoral.createByUser.$invalid, invalid: $v.personaMoral.createByUser.$invalid }"
              v-model="$v.personaMoral.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.createdOn')" for="persona-moral-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="persona-moral-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.personaMoral.createdOn.$invalid, invalid: $v.personaMoral.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.personaMoral.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.modifyByUser')" for="persona-moral-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="persona-moral-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.personaMoral.modifyByUser.$invalid, invalid: $v.personaMoral.modifyByUser.$invalid }"
              v-model="$v.personaMoral.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.modifiedOn')" for="persona-moral-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="persona-moral-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.personaMoral.modifiedOn.$invalid, invalid: $v.personaMoral.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.personaMoral.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.auditStatus')" for="persona-moral-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="persona-moral-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.personaMoral.auditStatus.$invalid, invalid: $v.personaMoral.auditStatus.$invalid }"
              v-model="$v.personaMoral.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.personaMoral.persona')" for="persona-moral-persona">Persona</label>
            <select class="form-control" id="persona-moral-persona" data-cy="persona" name="persona" v-model="personaMoral.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="personaMoral.persona && personaOption.id === personaMoral.persona.id ? personaMoral.persona : personaOption"
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
            :disabled="$v.personaMoral.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./persona-moral-update.component.ts"></script>
