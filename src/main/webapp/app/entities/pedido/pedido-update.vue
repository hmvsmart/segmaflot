<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.pedido.home.createOrEditLabel"
          data-cy="PedidoCreateUpdateHeading"
          v-text="$t('segmaflotApp.pedido.home.createOrEditLabel')"
        >
          Create or edit a Pedido
        </h2>
        <div>
          <div class="form-group" v-if="pedido.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="pedido.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.fecha')" for="pedido-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="pedido-fecha"
                  v-model="$v.pedido.fecha.$model"
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
                id="pedido-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.pedido.fecha.$invalid, invalid: $v.pedido.fecha.$invalid }"
                v-model="$v.pedido.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.createByUser')" for="pedido-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="pedido-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.pedido.createByUser.$invalid, invalid: $v.pedido.createByUser.$invalid }"
              v-model="$v.pedido.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.createdOn')" for="pedido-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="pedido-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.pedido.createdOn.$invalid, invalid: $v.pedido.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.pedido.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.modifyByUser')" for="pedido-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="pedido-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.pedido.modifyByUser.$invalid, invalid: $v.pedido.modifyByUser.$invalid }"
              v-model="$v.pedido.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.modifiedOn')" for="pedido-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="pedido-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.pedido.modifiedOn.$invalid, invalid: $v.pedido.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.pedido.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.auditStatus')" for="pedido-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="pedido-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.pedido.auditStatus.$invalid, invalid: $v.pedido.auditStatus.$invalid }"
              v-model="$v.pedido.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.pedido.empresa')" for="pedido-empresa">Empresa</label>
            <select class="form-control" id="pedido-empresa" data-cy="empresa" name="empresa" v-model="pedido.empresa">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="pedido.empresa && personaMoralOption.id === pedido.empresa.id ? pedido.empresa : personaMoralOption"
                v-for="personaMoralOption in personaMorals"
                :key="personaMoralOption.id"
              >
                {{ personaMoralOption.id }}
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
            :disabled="$v.pedido.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./pedido-update.component.ts"></script>
