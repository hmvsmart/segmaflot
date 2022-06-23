<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.direccion.home.createOrEditLabel"
          data-cy="DireccionCreateUpdateHeading"
          v-text="$t('segmaflotApp.direccion.home.createOrEditLabel')"
        >
          Create or edit a Direccion
        </h2>
        <div>
          <div class="form-group" v-if="direccion.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="direccion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.calle')" for="direccion-calle">Calle</label>
            <input
              type="text"
              class="form-control"
              name="calle"
              id="direccion-calle"
              data-cy="calle"
              :class="{ valid: !$v.direccion.calle.$invalid, invalid: $v.direccion.calle.$invalid }"
              v-model="$v.direccion.calle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.numeroExterior')" for="direccion-numeroExterior"
              >Numero Exterior</label
            >
            <input
              type="number"
              class="form-control"
              name="numeroExterior"
              id="direccion-numeroExterior"
              data-cy="numeroExterior"
              :class="{ valid: !$v.direccion.numeroExterior.$invalid, invalid: $v.direccion.numeroExterior.$invalid }"
              v-model.number="$v.direccion.numeroExterior.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.numeroInterior')" for="direccion-numeroInterior"
              >Numero Interior</label
            >
            <input
              type="text"
              class="form-control"
              name="numeroInterior"
              id="direccion-numeroInterior"
              data-cy="numeroInterior"
              :class="{ valid: !$v.direccion.numeroInterior.$invalid, invalid: $v.direccion.numeroInterior.$invalid }"
              v-model="$v.direccion.numeroInterior.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.createByUser')" for="direccion-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="direccion-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.direccion.createByUser.$invalid, invalid: $v.direccion.createByUser.$invalid }"
              v-model="$v.direccion.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.createdOn')" for="direccion-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="direccion-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.direccion.createdOn.$invalid, invalid: $v.direccion.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.direccion.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.modifyByUser')" for="direccion-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="direccion-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.direccion.modifyByUser.$invalid, invalid: $v.direccion.modifyByUser.$invalid }"
              v-model="$v.direccion.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.modifiedOn')" for="direccion-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="direccion-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.direccion.modifiedOn.$invalid, invalid: $v.direccion.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.direccion.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.auditStatus')" for="direccion-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="direccion-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.direccion.auditStatus.$invalid, invalid: $v.direccion.auditStatus.$invalid }"
              v-model="$v.direccion.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.direccion.colonia')" for="direccion-colonia">Colonia</label>
            <select class="form-control" id="direccion-colonia" data-cy="colonia" name="colonia" v-model="direccion.colonia">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="direccion.colonia && coloniaOption.id === direccion.colonia.id ? direccion.colonia : coloniaOption"
                v-for="coloniaOption in colonias"
                :key="coloniaOption.id"
              >
                {{ coloniaOption.id }}
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
            :disabled="$v.direccion.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./direccion-update.component.ts"></script>
