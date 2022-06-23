<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.precioVenta.home.createOrEditLabel"
          data-cy="PrecioVentaCreateUpdateHeading"
          v-text="$t('segmaflotApp.precioVenta.home.createOrEditLabel')"
        >
          Create or edit a PrecioVenta
        </h2>
        <div>
          <div class="form-group" v-if="precioVenta.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="precioVenta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.fecha')" for="precio-venta-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="precio-venta-fecha"
                  v-model="$v.precioVenta.fecha.$model"
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
                id="precio-venta-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.precioVenta.fecha.$invalid, invalid: $v.precioVenta.fecha.$invalid }"
                v-model="$v.precioVenta.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.ppu')" for="precio-venta-ppu">Ppu</label>
            <input
              type="number"
              class="form-control"
              name="ppu"
              id="precio-venta-ppu"
              data-cy="ppu"
              :class="{ valid: !$v.precioVenta.ppu.$invalid, invalid: $v.precioVenta.ppu.$invalid }"
              v-model.number="$v.precioVenta.ppu.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.createByUser')" for="precio-venta-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="precio-venta-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.precioVenta.createByUser.$invalid, invalid: $v.precioVenta.createByUser.$invalid }"
              v-model="$v.precioVenta.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.createdOn')" for="precio-venta-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="precio-venta-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.precioVenta.createdOn.$invalid, invalid: $v.precioVenta.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.precioVenta.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.modifyByUser')" for="precio-venta-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="precio-venta-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.precioVenta.modifyByUser.$invalid, invalid: $v.precioVenta.modifyByUser.$invalid }"
              v-model="$v.precioVenta.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.modifiedOn')" for="precio-venta-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="precio-venta-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.precioVenta.modifiedOn.$invalid, invalid: $v.precioVenta.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.precioVenta.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.auditStatus')" for="precio-venta-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="precio-venta-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.precioVenta.auditStatus.$invalid, invalid: $v.precioVenta.auditStatus.$invalid }"
              v-model="$v.precioVenta.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.precioVenta.inventario')" for="precio-venta-inventario"
              >Inventario</label
            >
            <select
              class="form-control"
              id="precio-venta-inventario"
              data-cy="inventario"
              name="inventario"
              v-model="precioVenta.inventario"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  precioVenta.inventario && inventarioOption.id === precioVenta.inventario.id ? precioVenta.inventario : inventarioOption
                "
                v-for="inventarioOption in inventarios"
                :key="inventarioOption.id"
              >
                {{ inventarioOption.id }}
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
            :disabled="$v.precioVenta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./precio-venta-update.component.ts"></script>
