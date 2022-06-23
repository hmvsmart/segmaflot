<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.renglonVenta.home.createOrEditLabel"
          data-cy="RenglonVentaCreateUpdateHeading"
          v-text="$t('segmaflotApp.renglonVenta.home.createOrEditLabel')"
        >
          Create or edit a RenglonVenta
        </h2>
        <div>
          <div class="form-group" v-if="renglonVenta.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="renglonVenta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.cantidad')" for="renglon-venta-cantidad"
              >Cantidad</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="renglon-venta-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.renglonVenta.cantidad.$invalid, invalid: $v.renglonVenta.cantidad.$invalid }"
              v-model.number="$v.renglonVenta.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.createByUser')" for="renglon-venta-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="renglon-venta-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.renglonVenta.createByUser.$invalid, invalid: $v.renglonVenta.createByUser.$invalid }"
              v-model="$v.renglonVenta.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.createdOn')" for="renglon-venta-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="renglon-venta-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.renglonVenta.createdOn.$invalid, invalid: $v.renglonVenta.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.renglonVenta.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.modifyByUser')" for="renglon-venta-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="renglon-venta-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.renglonVenta.modifyByUser.$invalid, invalid: $v.renglonVenta.modifyByUser.$invalid }"
              v-model="$v.renglonVenta.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.modifiedOn')" for="renglon-venta-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="renglon-venta-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.renglonVenta.modifiedOn.$invalid, invalid: $v.renglonVenta.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.renglonVenta.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.auditStatus')" for="renglon-venta-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="renglon-venta-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.renglonVenta.auditStatus.$invalid, invalid: $v.renglonVenta.auditStatus.$invalid }"
              v-model="$v.renglonVenta.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.venta')" for="renglon-venta-venta">Venta</label>
            <select class="form-control" id="renglon-venta-venta" data-cy="venta" name="venta" v-model="renglonVenta.venta">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="renglonVenta.venta && ventaOption.id === renglonVenta.venta.id ? renglonVenta.venta : ventaOption"
                v-for="ventaOption in ventas"
                :key="ventaOption.id"
              >
                {{ ventaOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.renglonVenta.precioventa')" for="renglon-venta-precioventa"
              >Precioventa</label
            >
            <select
              class="form-control"
              id="renglon-venta-precioventa"
              data-cy="precioventa"
              name="precioventa"
              v-model="renglonVenta.precioventa"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  renglonVenta.precioventa && precioVentaOption.id === renglonVenta.precioventa.id
                    ? renglonVenta.precioventa
                    : precioVentaOption
                "
                v-for="precioVentaOption in precioVentas"
                :key="precioVentaOption.id"
              >
                {{ precioVentaOption.id }}
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
            :disabled="$v.renglonVenta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./renglon-venta-update.component.ts"></script>
