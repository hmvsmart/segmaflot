<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.reporte.home.createOrEditLabel"
          data-cy="ReporteCreateUpdateHeading"
          v-text="$t('segmaflotApp.reporte.home.createOrEditLabel')"
        >
          Create or edit a Reporte
        </h2>
        <div>
          <div class="form-group" v-if="reporte.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="reporte.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.fecha')" for="reporte-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="reporte-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.reporte.fecha.$invalid, invalid: $v.reporte.fecha.$invalid }"
                :value="convertDateTimeFromServer($v.reporte.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.nombre')" for="reporte-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="reporte-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.reporte.nombre.$invalid, invalid: $v.reporte.nombre.$invalid }"
              v-model="$v.reporte.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.documento')" for="reporte-documento">Documento</label>
            <div>
              <div v-if="reporte.documento" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(reporte.documentoContentType, reporte.documento)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ reporte.documentoContentType }}, {{ byteSize(reporte.documento) }}</span>
                <button
                  type="button"
                  v-on:click="
                    reporte.documento = null;
                    reporte.documentoContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_documento"
                id="file_documento"
                data-cy="documento"
                v-on:change="setFileData($event, reporte, 'documento', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="documento"
              id="reporte-documento"
              data-cy="documento"
              :class="{ valid: !$v.reporte.documento.$invalid, invalid: $v.reporte.documento.$invalid }"
              v-model="$v.reporte.documento.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="documentoContentType"
              id="reporte-documentoContentType"
              v-model="reporte.documentoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.createByUser')" for="reporte-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="reporte-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.reporte.createByUser.$invalid, invalid: $v.reporte.createByUser.$invalid }"
              v-model="$v.reporte.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.createdOn')" for="reporte-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="reporte-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.reporte.createdOn.$invalid, invalid: $v.reporte.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.reporte.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.modifyByUser')" for="reporte-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="reporte-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.reporte.modifyByUser.$invalid, invalid: $v.reporte.modifyByUser.$invalid }"
              v-model="$v.reporte.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.modifiedOn')" for="reporte-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="reporte-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.reporte.modifiedOn.$invalid, invalid: $v.reporte.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.reporte.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.auditStatus')" for="reporte-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="reporte-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.reporte.auditStatus.$invalid, invalid: $v.reporte.auditStatus.$invalid }"
              v-model="$v.reporte.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.reporte.empresa')" for="reporte-empresa">Empresa</label>
            <select class="form-control" id="reporte-empresa" data-cy="empresa" name="empresa" v-model="reporte.empresa">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="reporte.empresa && personaMoralOption.id === reporte.empresa.id ? reporte.empresa : personaMoralOption"
                v-for="personaMoralOption in personaMorals"
                :key="personaMoralOption.id"
              >
                {{ personaMoralOption.id }}
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
            :disabled="$v.reporte.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./reporte-update.component.ts"></script>
