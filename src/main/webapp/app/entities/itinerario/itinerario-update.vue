<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.itinerario.home.createOrEditLabel"
          data-cy="ItinerarioCreateUpdateHeading"
          v-text="$t('segmaflotApp.itinerario.home.createOrEditLabel')"
        >
          Create or edit a Itinerario
        </h2>
        <div>
          <div class="form-group" v-if="itinerario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="itinerario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.accion')" for="itinerario-accion">Accion</label>
            <input
              type="text"
              class="form-control"
              name="accion"
              id="itinerario-accion"
              data-cy="accion"
              :class="{ valid: !$v.itinerario.accion.$invalid, invalid: $v.itinerario.accion.$invalid }"
              v-model="$v.itinerario.accion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.fechaHoraEstimada')" for="itinerario-fechaHoraEstimada"
              >Fecha Hora Estimada</label
            >
            <div class="d-flex">
              <input
                id="itinerario-fechaHoraEstimada"
                data-cy="fechaHoraEstimada"
                type="datetime-local"
                class="form-control"
                name="fechaHoraEstimada"
                :class="{ valid: !$v.itinerario.fechaHoraEstimada.$invalid, invalid: $v.itinerario.fechaHoraEstimada.$invalid }"
                :value="convertDateTimeFromServer($v.itinerario.fechaHoraEstimada.$model)"
                @change="updateInstantField('fechaHoraEstimada', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.createByUser')" for="itinerario-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="itinerario-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.itinerario.createByUser.$invalid, invalid: $v.itinerario.createByUser.$invalid }"
              v-model="$v.itinerario.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.createdOn')" for="itinerario-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="itinerario-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.itinerario.createdOn.$invalid, invalid: $v.itinerario.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.itinerario.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.modifyByUser')" for="itinerario-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="itinerario-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.itinerario.modifyByUser.$invalid, invalid: $v.itinerario.modifyByUser.$invalid }"
              v-model="$v.itinerario.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.modifiedOn')" for="itinerario-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="itinerario-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.itinerario.modifiedOn.$invalid, invalid: $v.itinerario.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.itinerario.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.auditStatus')" for="itinerario-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="itinerario-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.itinerario.auditStatus.$invalid, invalid: $v.itinerario.auditStatus.$invalid }"
              v-model="$v.itinerario.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.itinerario.viaje')" for="itinerario-viaje">Viaje</label>
            <select class="form-control" id="itinerario-viaje" data-cy="viaje" name="viaje" v-model="itinerario.viaje">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="itinerario.viaje && viajeOption.id === itinerario.viaje.id ? itinerario.viaje : viajeOption"
                v-for="viajeOption in viajes"
                :key="viajeOption.id"
              >
                {{ viajeOption.id }}
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
            :disabled="$v.itinerario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./itinerario-update.component.ts"></script>
