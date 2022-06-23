<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.poliza.home.createOrEditLabel"
          data-cy="PolizaCreateUpdateHeading"
          v-text="$t('segmaflotApp.poliza.home.createOrEditLabel')"
        >
          Create or edit a Poliza
        </h2>
        <div>
          <div class="form-group" v-if="poliza.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="poliza.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.fecha')" for="poliza-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="poliza-fecha"
                  v-model="$v.poliza.fecha.$model"
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
                id="poliza-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.poliza.fecha.$invalid, invalid: $v.poliza.fecha.$invalid }"
                v-model="$v.poliza.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.fechaVencimiento')" for="poliza-fechaVencimiento"
              >Fecha Vencimiento</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="poliza-fechaVencimiento"
                  v-model="$v.poliza.fechaVencimiento.$model"
                  name="fechaVencimiento"
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
                id="poliza-fechaVencimiento"
                data-cy="fechaVencimiento"
                type="text"
                class="form-control"
                name="fechaVencimiento"
                :class="{ valid: !$v.poliza.fechaVencimiento.$invalid, invalid: $v.poliza.fechaVencimiento.$invalid }"
                v-model="$v.poliza.fechaVencimiento.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.numeroPoliza')" for="poliza-numeroPoliza"
              >Numero Poliza</label
            >
            <input
              type="text"
              class="form-control"
              name="numeroPoliza"
              id="poliza-numeroPoliza"
              data-cy="numeroPoliza"
              :class="{ valid: !$v.poliza.numeroPoliza.$invalid, invalid: $v.poliza.numeroPoliza.$invalid }"
              v-model="$v.poliza.numeroPoliza.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.createByUser')" for="poliza-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="poliza-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.poliza.createByUser.$invalid, invalid: $v.poliza.createByUser.$invalid }"
              v-model="$v.poliza.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.createdOn')" for="poliza-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="poliza-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.poliza.createdOn.$invalid, invalid: $v.poliza.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.poliza.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.modifyByUser')" for="poliza-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="poliza-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.poliza.modifyByUser.$invalid, invalid: $v.poliza.modifyByUser.$invalid }"
              v-model="$v.poliza.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.modifiedOn')" for="poliza-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="poliza-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.poliza.modifiedOn.$invalid, invalid: $v.poliza.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.poliza.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.auditStatus')" for="poliza-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="poliza-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.poliza.auditStatus.$invalid, invalid: $v.poliza.auditStatus.$invalid }"
              v-model="$v.poliza.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.poliza.unidad')" for="poliza-unidad">Unidad</label>
            <select class="form-control" id="poliza-unidad" data-cy="unidad" name="unidad" v-model="poliza.unidad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="poliza.unidad && unidadOption.id === poliza.unidad.id ? poliza.unidad : unidadOption"
                v-for="unidadOption in unidads"
                :key="unidadOption.id"
              >
                {{ unidadOption.id }}
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
            :disabled="$v.poliza.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./poliza-update.component.ts"></script>
