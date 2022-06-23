<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.nivel.home.createOrEditLabel"
          data-cy="NivelCreateUpdateHeading"
          v-text="$t('segmaflotApp.nivel.home.createOrEditLabel')"
        >
          Create or edit a Nivel
        </h2>
        <div>
          <div class="form-group" v-if="nivel.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="nivel.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.nombre')" for="nivel-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="nivel-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.nivel.nombre.$invalid, invalid: $v.nivel.nombre.$invalid }"
              v-model="$v.nivel.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.createByUser')" for="nivel-createByUser">Create By User</label>
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="nivel-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.nivel.createByUser.$invalid, invalid: $v.nivel.createByUser.$invalid }"
              v-model="$v.nivel.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.createdOn')" for="nivel-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="nivel-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.nivel.createdOn.$invalid, invalid: $v.nivel.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.nivel.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.modifyByUser')" for="nivel-modifyByUser">Modify By User</label>
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="nivel-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.nivel.modifyByUser.$invalid, invalid: $v.nivel.modifyByUser.$invalid }"
              v-model="$v.nivel.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.modifiedOn')" for="nivel-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="nivel-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.nivel.modifiedOn.$invalid, invalid: $v.nivel.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.nivel.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.auditStatus')" for="nivel-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="nivel-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.nivel.auditStatus.$invalid, invalid: $v.nivel.auditStatus.$invalid }"
              v-model="$v.nivel.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.nivel.estante')" for="nivel-estante">Estante</label>
            <select class="form-control" id="nivel-estante" data-cy="estante" name="estante" v-model="nivel.estante">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="nivel.estante && estanteOption.id === nivel.estante.id ? nivel.estante : estanteOption"
                v-for="estanteOption in estantes"
                :key="estanteOption.id"
              >
                {{ estanteOption.id }}
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
            :disabled="$v.nivel.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./nivel-update.component.ts"></script>
