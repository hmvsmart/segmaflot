<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.ubicacionInventario.home.createOrEditLabel"
          data-cy="UbicacionInventarioCreateUpdateHeading"
          v-text="$t('segmaflotApp.ubicacionInventario.home.createOrEditLabel')"
        >
          Create or edit a UbicacionInventario
        </h2>
        <div>
          <div class="form-group" v-if="ubicacionInventario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ubicacionInventario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ubicacionInventario.status')" for="ubicacion-inventario-status"
              >Status</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="ubicacion-inventario-status"
              data-cy="status"
              :class="{ valid: !$v.ubicacionInventario.status.$invalid, invalid: $v.ubicacionInventario.status.$invalid }"
              v-model="$v.ubicacionInventario.status.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.ubicacionInventario.createByUser')"
              for="ubicacion-inventario-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="ubicacion-inventario-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.ubicacionInventario.createByUser.$invalid, invalid: $v.ubicacionInventario.createByUser.$invalid }"
              v-model="$v.ubicacionInventario.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ubicacionInventario.createdOn')" for="ubicacion-inventario-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="ubicacion-inventario-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.ubicacionInventario.createdOn.$invalid, invalid: $v.ubicacionInventario.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.ubicacionInventario.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.ubicacionInventario.modifyByUser')"
              for="ubicacion-inventario-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="ubicacion-inventario-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.ubicacionInventario.modifyByUser.$invalid, invalid: $v.ubicacionInventario.modifyByUser.$invalid }"
              v-model="$v.ubicacionInventario.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.ubicacionInventario.modifiedOn')"
              for="ubicacion-inventario-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="ubicacion-inventario-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.ubicacionInventario.modifiedOn.$invalid, invalid: $v.ubicacionInventario.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.ubicacionInventario.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.ubicacionInventario.auditStatus')"
              for="ubicacion-inventario-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="ubicacion-inventario-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.ubicacionInventario.auditStatus.$invalid, invalid: $v.ubicacionInventario.auditStatus.$invalid }"
              v-model="$v.ubicacionInventario.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ubicacionInventario.seccion')" for="ubicacion-inventario-seccion"
              >Seccion</label
            >
            <select
              class="form-control"
              id="ubicacion-inventario-seccion"
              data-cy="seccion"
              name="seccion"
              v-model="ubicacionInventario.seccion"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  ubicacionInventario.seccion && seccionOption.id === ubicacionInventario.seccion.id
                    ? ubicacionInventario.seccion
                    : seccionOption
                "
                v-for="seccionOption in seccions"
                :key="seccionOption.id"
              >
                {{ seccionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('segmaflotApp.ubicacionInventario.inventario')"
              for="ubicacion-inventario-inventario"
              >Inventario</label
            >
            <select
              class="form-control"
              id="ubicacion-inventario-inventario"
              data-cy="inventario"
              name="inventario"
              v-model="ubicacionInventario.inventario"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  ubicacionInventario.inventario && inventarioOption.id === ubicacionInventario.inventario.id
                    ? ubicacionInventario.inventario
                    : inventarioOption
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
            :disabled="$v.ubicacionInventario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ubicacion-inventario-update.component.ts"></script>
