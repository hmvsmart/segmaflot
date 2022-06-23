<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.venta.home.createOrEditLabel"
          data-cy="VentaCreateUpdateHeading"
          v-text="$t('segmaflotApp.venta.home.createOrEditLabel')"
        >
          Create or edit a Venta
        </h2>
        <div>
          <div class="form-group" v-if="venta.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="venta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.fecha')" for="venta-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="venta-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.venta.fecha.$invalid, invalid: $v.venta.fecha.$invalid }"
                :value="convertDateTimeFromServer($v.venta.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.total')" for="venta-total">Total</label>
            <input
              type="number"
              class="form-control"
              name="total"
              id="venta-total"
              data-cy="total"
              :class="{ valid: !$v.venta.total.$invalid, invalid: $v.venta.total.$invalid }"
              v-model.number="$v.venta.total.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.createByUser')" for="venta-createByUser">Create By User</label>
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="venta-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.venta.createByUser.$invalid, invalid: $v.venta.createByUser.$invalid }"
              v-model="$v.venta.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.createdOn')" for="venta-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="venta-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.venta.createdOn.$invalid, invalid: $v.venta.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.venta.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.modifyByUser')" for="venta-modifyByUser">Modify By User</label>
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="venta-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.venta.modifyByUser.$invalid, invalid: $v.venta.modifyByUser.$invalid }"
              v-model="$v.venta.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.modifiedOn')" for="venta-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="venta-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.venta.modifiedOn.$invalid, invalid: $v.venta.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.venta.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.venta.auditStatus')" for="venta-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="venta-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.venta.auditStatus.$invalid, invalid: $v.venta.auditStatus.$invalid }"
              v-model="$v.venta.auditStatus.$model"
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
            :disabled="$v.venta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./venta-update.component.ts"></script>
