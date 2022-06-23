<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.pago.home.createOrEditLabel"
          data-cy="PagoCreateUpdateHeading"
          v-text="$t('segmaflotApp.pago.home.createOrEditLabel')"
        >
          Create or edit a Pago
        </h2>
        <div>
          <div class="form-group" v-if="pago.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="pago.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.fecha')" for="pago-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="pago-fecha"
                  v-model="$v.pago.fecha.$model"
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
                id="pago-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.pago.fecha.$invalid, invalid: $v.pago.fecha.$invalid }"
                v-model="$v.pago.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.tipoPago')" for="pago-tipoPago">Tipo Pago</label>
            <input
              type="text"
              class="form-control"
              name="tipoPago"
              id="pago-tipoPago"
              data-cy="tipoPago"
              :class="{ valid: !$v.pago.tipoPago.$invalid, invalid: $v.pago.tipoPago.$invalid }"
              v-model="$v.pago.tipoPago.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.monto')" for="pago-monto">Monto</label>
            <input
              type="number"
              class="form-control"
              name="monto"
              id="pago-monto"
              data-cy="monto"
              :class="{ valid: !$v.pago.monto.$invalid, invalid: $v.pago.monto.$invalid }"
              v-model.number="$v.pago.monto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.area')" for="pago-area">Area</label>
            <input
              type="text"
              class="form-control"
              name="area"
              id="pago-area"
              data-cy="area"
              :class="{ valid: !$v.pago.area.$invalid, invalid: $v.pago.area.$invalid }"
              v-model="$v.pago.area.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.identificador')" for="pago-identificador">Identificador</label>
            <input
              type="text"
              class="form-control"
              name="identificador"
              id="pago-identificador"
              data-cy="identificador"
              :class="{ valid: !$v.pago.identificador.$invalid, invalid: $v.pago.identificador.$invalid }"
              v-model="$v.pago.identificador.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.createByUser')" for="pago-createByUser">Create By User</label>
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="pago-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.pago.createByUser.$invalid, invalid: $v.pago.createByUser.$invalid }"
              v-model="$v.pago.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.createdOn')" for="pago-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="pago-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.pago.createdOn.$invalid, invalid: $v.pago.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.pago.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.modifyByUser')" for="pago-modifyByUser">Modify By User</label>
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="pago-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.pago.modifyByUser.$invalid, invalid: $v.pago.modifyByUser.$invalid }"
              v-model="$v.pago.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.modifiedOn')" for="pago-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="pago-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.pago.modifiedOn.$invalid, invalid: $v.pago.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.pago.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pago.auditStatus')" for="pago-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="pago-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.pago.auditStatus.$invalid, invalid: $v.pago.auditStatus.$invalid }"
              v-model="$v.pago.auditStatus.$model"
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
            :disabled="$v.pago.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./pago-update.component.ts"></script>
