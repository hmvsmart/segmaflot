<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.aditamento.home.createOrEditLabel"
          data-cy="AditamentoCreateUpdateHeading"
          v-text="$t('segmaflotApp.aditamento.home.createOrEditLabel')"
        >
          Create or edit a Aditamento
        </h2>
        <div>
          <div class="form-group" v-if="aditamento.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="aditamento.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.nombre')" for="aditamento-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="aditamento-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.aditamento.nombre.$invalid, invalid: $v.aditamento.nombre.$invalid }"
              v-model="$v.aditamento.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.numeroSerie')" for="aditamento-numeroSerie"
              >Numero Serie</label
            >
            <input
              type="text"
              class="form-control"
              name="numeroSerie"
              id="aditamento-numeroSerie"
              data-cy="numeroSerie"
              :class="{ valid: !$v.aditamento.numeroSerie.$invalid, invalid: $v.aditamento.numeroSerie.$invalid }"
              v-model="$v.aditamento.numeroSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.descripcion')" for="aditamento-descripcion"
              >Descripcion</label
            >
            <div>
              <div v-if="aditamento.descripcion" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(aditamento.descripcionContentType, aditamento.descripcion)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ aditamento.descripcionContentType }}, {{ byteSize(aditamento.descripcion) }}</span>
                <button
                  type="button"
                  v-on:click="
                    aditamento.descripcion = null;
                    aditamento.descripcionContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_descripcion"
                id="file_descripcion"
                data-cy="descripcion"
                v-on:change="setFileData($event, aditamento, 'descripcion', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="descripcion"
              id="aditamento-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.aditamento.descripcion.$invalid, invalid: $v.aditamento.descripcion.$invalid }"
              v-model="$v.aditamento.descripcion.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="descripcionContentType"
              id="aditamento-descripcionContentType"
              v-model="aditamento.descripcionContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.createByUser')" for="aditamento-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="aditamento-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.aditamento.createByUser.$invalid, invalid: $v.aditamento.createByUser.$invalid }"
              v-model="$v.aditamento.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.createdOn')" for="aditamento-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="aditamento-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.aditamento.createdOn.$invalid, invalid: $v.aditamento.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.aditamento.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.modifyByUser')" for="aditamento-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="aditamento-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.aditamento.modifyByUser.$invalid, invalid: $v.aditamento.modifyByUser.$invalid }"
              v-model="$v.aditamento.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.modifiedOn')" for="aditamento-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="aditamento-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.aditamento.modifiedOn.$invalid, invalid: $v.aditamento.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.aditamento.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.aditamento.auditStatus')" for="aditamento-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="aditamento-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.aditamento.auditStatus.$invalid, invalid: $v.aditamento.auditStatus.$invalid }"
              v-model="$v.aditamento.auditStatus.$model"
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
            :disabled="$v.aditamento.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./aditamento-update.component.ts"></script>
