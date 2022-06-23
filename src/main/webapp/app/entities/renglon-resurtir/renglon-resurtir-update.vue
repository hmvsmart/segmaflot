<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.renglonResurtir.home.createOrEditLabel"
          data-cy="RenglonResurtirCreateUpdateHeading"
          v-text="$t('segmaflotApp.renglonResurtir.home.createOrEditLabel')"
        >
          Create or edit a RenglonResurtir
        </h2>
        <div>
          <div class="form-group" v-if="renglonResurtir.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="renglonResurtir.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.renglonResurtir.fechaCaducidad')"
              for="renglon-resurtir-fechaCaducidad"
              >Fecha Caducidad</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="renglon-resurtir-fechaCaducidad"
                  v-model="$v.renglonResurtir.fechaCaducidad.$model"
                  name="fechaCaducidad"
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
                id="renglon-resurtir-fechaCaducidad"
                data-cy="fechaCaducidad"
                type="text"
                class="form-control"
                name="fechaCaducidad"
                :class="{ valid: !$v.renglonResurtir.fechaCaducidad.$invalid, invalid: $v.renglonResurtir.fechaCaducidad.$invalid }"
                v-model="$v.renglonResurtir.fechaCaducidad.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.cantidad')" for="renglon-resurtir-cantidad"
              >Cantidad</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="renglon-resurtir-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.renglonResurtir.cantidad.$invalid, invalid: $v.renglonResurtir.cantidad.$invalid }"
              v-model.number="$v.renglonResurtir.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.precioCompra')" for="renglon-resurtir-precioCompra"
              >Precio Compra</label
            >
            <input
              type="number"
              class="form-control"
              name="precioCompra"
              id="renglon-resurtir-precioCompra"
              data-cy="precioCompra"
              :class="{ valid: !$v.renglonResurtir.precioCompra.$invalid, invalid: $v.renglonResurtir.precioCompra.$invalid }"
              v-model.number="$v.renglonResurtir.precioCompra.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.createByUser')" for="renglon-resurtir-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="renglon-resurtir-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.renglonResurtir.createByUser.$invalid, invalid: $v.renglonResurtir.createByUser.$invalid }"
              v-model="$v.renglonResurtir.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.createdOn')" for="renglon-resurtir-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="renglon-resurtir-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.renglonResurtir.createdOn.$invalid, invalid: $v.renglonResurtir.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.renglonResurtir.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.modifyByUser')" for="renglon-resurtir-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="renglon-resurtir-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.renglonResurtir.modifyByUser.$invalid, invalid: $v.renglonResurtir.modifyByUser.$invalid }"
              v-model="$v.renglonResurtir.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.modifiedOn')" for="renglon-resurtir-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="renglon-resurtir-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.renglonResurtir.modifiedOn.$invalid, invalid: $v.renglonResurtir.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.renglonResurtir.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.auditStatus')" for="renglon-resurtir-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="renglon-resurtir-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.renglonResurtir.auditStatus.$invalid, invalid: $v.renglonResurtir.auditStatus.$invalid }"
              v-model="$v.renglonResurtir.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonResurtir.resurtir')" for="renglon-resurtir-resurtir"
              >Resurtir</label
            >
            <select
              class="form-control"
              id="renglon-resurtir-resurtir"
              data-cy="resurtir"
              name="resurtir"
              v-model="renglonResurtir.resurtir"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  renglonResurtir.resurtir && resurtirOption.id === renglonResurtir.resurtir.id ? renglonResurtir.resurtir : resurtirOption
                "
                v-for="resurtirOption in resurtirs"
                :key="resurtirOption.id"
              >
                {{ resurtirOption.id }}
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
            :disabled="$v.renglonResurtir.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./renglon-resurtir-update.component.ts"></script>
