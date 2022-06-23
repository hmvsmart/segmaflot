<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.estante.home.createOrEditLabel"
          data-cy="EstanteCreateUpdateHeading"
          v-text="$t('segmaflotApp.estante.home.createOrEditLabel')"
        >
          Create or edit a Estante
        </h2>
        <div>
          <div class="form-group" v-if="estante.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estante.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.nombre')" for="estante-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="estante-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.estante.nombre.$invalid, invalid: $v.estante.nombre.$invalid }"
              v-model="$v.estante.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.material')" for="estante-material">Material</label>
            <input
              type="text"
              class="form-control"
              name="material"
              id="estante-material"
              data-cy="material"
              :class="{ valid: !$v.estante.material.$invalid, invalid: $v.estante.material.$invalid }"
              v-model="$v.estante.material.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.color')" for="estante-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="estante-color"
              data-cy="color"
              :class="{ valid: !$v.estante.color.$invalid, invalid: $v.estante.color.$invalid }"
              v-model="$v.estante.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.descripcion')" for="estante-descripcion">Descripcion</label>
            <input
              type="text"
              class="form-control"
              name="descripcion"
              id="estante-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.estante.descripcion.$invalid, invalid: $v.estante.descripcion.$invalid }"
              v-model="$v.estante.descripcion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.createByUser')" for="estante-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="estante-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.estante.createByUser.$invalid, invalid: $v.estante.createByUser.$invalid }"
              v-model="$v.estante.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.createdOn')" for="estante-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="estante-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.estante.createdOn.$invalid, invalid: $v.estante.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.estante.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.modifyByUser')" for="estante-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="estante-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.estante.modifyByUser.$invalid, invalid: $v.estante.modifyByUser.$invalid }"
              v-model="$v.estante.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.modifiedOn')" for="estante-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="estante-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.estante.modifiedOn.$invalid, invalid: $v.estante.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.estante.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.estante.auditStatus')" for="estante-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="estante-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.estante.auditStatus.$invalid, invalid: $v.estante.auditStatus.$invalid }"
              v-model="$v.estante.auditStatus.$model"
            />
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
            :disabled="$v.estante.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estante-update.component.ts"></script>
