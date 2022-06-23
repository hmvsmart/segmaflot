<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.resurtir.home.createOrEditLabel"
          data-cy="ResurtirCreateUpdateHeading"
          v-text="$t('segmaflotApp.resurtir.home.createOrEditLabel')"
        >
          Create or edit a Resurtir
        </h2>
        <div>
          <div class="form-group" v-if="resurtir.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="resurtir.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.fecha')" for="resurtir-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="resurtir-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.resurtir.fecha.$invalid, invalid: $v.resurtir.fecha.$invalid }"
                :value="convertDateTimeFromServer($v.resurtir.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.total')" for="resurtir-total">Total</label>
            <input
              type="number"
              class="form-control"
              name="total"
              id="resurtir-total"
              data-cy="total"
              :class="{ valid: !$v.resurtir.total.$invalid, invalid: $v.resurtir.total.$invalid }"
              v-model.number="$v.resurtir.total.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.createByUser')" for="resurtir-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="resurtir-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.resurtir.createByUser.$invalid, invalid: $v.resurtir.createByUser.$invalid }"
              v-model="$v.resurtir.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.createdOn')" for="resurtir-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="resurtir-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.resurtir.createdOn.$invalid, invalid: $v.resurtir.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.resurtir.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.modifyByUser')" for="resurtir-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="resurtir-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.resurtir.modifyByUser.$invalid, invalid: $v.resurtir.modifyByUser.$invalid }"
              v-model="$v.resurtir.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.modifiedOn')" for="resurtir-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="resurtir-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.resurtir.modifiedOn.$invalid, invalid: $v.resurtir.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.resurtir.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.auditStatus')" for="resurtir-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="resurtir-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.resurtir.auditStatus.$invalid, invalid: $v.resurtir.auditStatus.$invalid }"
              v-model="$v.resurtir.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.resurtir.inventario')" for="resurtir-inventario">Inventario</label>
            <select class="form-control" id="resurtir-inventario" data-cy="inventario" name="inventario" v-model="resurtir.inventario">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  resurtir.inventario && inventarioOption.id === resurtir.inventario.id ? resurtir.inventario : inventarioOption
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
            :disabled="$v.resurtir.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./resurtir-update.component.ts"></script>
