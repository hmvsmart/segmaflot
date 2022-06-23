<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.seccion.home.createOrEditLabel"
          data-cy="SeccionCreateUpdateHeading"
          v-text="$t('segmaflotApp.seccion.home.createOrEditLabel')"
        >
          Create or edit a Seccion
        </h2>
        <div>
          <div class="form-group" v-if="seccion.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="seccion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.nombre')" for="seccion-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="seccion-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.seccion.nombre.$invalid, invalid: $v.seccion.nombre.$invalid }"
              v-model="$v.seccion.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.createByUser')" for="seccion-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="seccion-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.seccion.createByUser.$invalid, invalid: $v.seccion.createByUser.$invalid }"
              v-model="$v.seccion.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.createdOn')" for="seccion-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="seccion-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.seccion.createdOn.$invalid, invalid: $v.seccion.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.seccion.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.modifyByUser')" for="seccion-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="seccion-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.seccion.modifyByUser.$invalid, invalid: $v.seccion.modifyByUser.$invalid }"
              v-model="$v.seccion.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.modifiedOn')" for="seccion-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="seccion-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.seccion.modifiedOn.$invalid, invalid: $v.seccion.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.seccion.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.auditStatus')" for="seccion-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="seccion-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.seccion.auditStatus.$invalid, invalid: $v.seccion.auditStatus.$invalid }"
              v-model="$v.seccion.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.seccion.nivel')" for="seccion-nivel">Nivel</label>
            <select class="form-control" id="seccion-nivel" data-cy="nivel" name="nivel" v-model="seccion.nivel">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="seccion.nivel && nivelOption.id === seccion.nivel.id ? seccion.nivel : nivelOption"
                v-for="nivelOption in nivels"
                :key="nivelOption.id"
              >
                {{ nivelOption.id }}
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
            :disabled="$v.seccion.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./seccion-update.component.ts"></script>
