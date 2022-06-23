<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.inventario.home.createOrEditLabel"
          data-cy="InventarioCreateUpdateHeading"
          v-text="$t('segmaflotApp.inventario.home.createOrEditLabel')"
        >
          Create or edit a Inventario
        </h2>
        <div>
          <div class="form-group" v-if="inventario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="inventario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.cantidad')" for="inventario-cantidad">Cantidad</label>
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="inventario-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.inventario.cantidad.$invalid, invalid: $v.inventario.cantidad.$invalid }"
              v-model.number="$v.inventario.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.cantidadMinimaEst')" for="inventario-cantidadMinimaEst"
              >Cantidad Minima Est</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidadMinimaEst"
              id="inventario-cantidadMinimaEst"
              data-cy="cantidadMinimaEst"
              :class="{ valid: !$v.inventario.cantidadMinimaEst.$invalid, invalid: $v.inventario.cantidadMinimaEst.$invalid }"
              v-model.number="$v.inventario.cantidadMinimaEst.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.cantidadMaximaEst')" for="inventario-cantidadMaximaEst"
              >Cantidad Maxima Est</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidadMaximaEst"
              id="inventario-cantidadMaximaEst"
              data-cy="cantidadMaximaEst"
              :class="{ valid: !$v.inventario.cantidadMaximaEst.$invalid, invalid: $v.inventario.cantidadMaximaEst.$invalid }"
              v-model.number="$v.inventario.cantidadMaximaEst.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.status')" for="inventario-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="inventario-status"
              data-cy="status"
              :class="{ valid: !$v.inventario.status.$invalid, invalid: $v.inventario.status.$invalid }"
              v-model="$v.inventario.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.createByUser')" for="inventario-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="inventario-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.inventario.createByUser.$invalid, invalid: $v.inventario.createByUser.$invalid }"
              v-model="$v.inventario.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.createdOn')" for="inventario-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="inventario-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.inventario.createdOn.$invalid, invalid: $v.inventario.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.inventario.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.modifyByUser')" for="inventario-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="inventario-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.inventario.modifyByUser.$invalid, invalid: $v.inventario.modifyByUser.$invalid }"
              v-model="$v.inventario.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.modifiedOn')" for="inventario-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="inventario-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.inventario.modifiedOn.$invalid, invalid: $v.inventario.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.inventario.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.auditStatus')" for="inventario-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="inventario-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.inventario.auditStatus.$invalid, invalid: $v.inventario.auditStatus.$invalid }"
              v-model="$v.inventario.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.producto')" for="inventario-producto">Producto</label>
            <select class="form-control" id="inventario-producto" data-cy="producto" name="producto" v-model="inventario.producto">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="inventario.producto && productoOption.id === inventario.producto.id ? inventario.producto : productoOption"
                v-for="productoOption in productos"
                :key="productoOption.id"
              >
                {{ productoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.inventario.sucursal')" for="inventario-sucursal">Sucursal</label>
            <select class="form-control" id="inventario-sucursal" data-cy="sucursal" name="sucursal" v-model="inventario.sucursal">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="inventario.sucursal && sucursalOption.id === inventario.sucursal.id ? inventario.sucursal : sucursalOption"
                v-for="sucursalOption in sucursals"
                :key="sucursalOption.id"
              >
                {{ sucursalOption.id }}
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
            :disabled="$v.inventario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./inventario-update.component.ts"></script>
