<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.diaHorario.home.createOrEditLabel"
          data-cy="DiaHorarioCreateUpdateHeading"
          v-text="$t('segmaflotApp.diaHorario.home.createOrEditLabel')"
        >
          Create or edit a DiaHorario
        </h2>
        <div>
          <div class="form-group" v-if="diaHorario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="diaHorario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.dia')" for="dia-horario-dia">Dia</label>
            <input
              type="number"
              class="form-control"
              name="dia"
              id="dia-horario-dia"
              data-cy="dia"
              :class="{ valid: !$v.diaHorario.dia.$invalid, invalid: $v.diaHorario.dia.$invalid }"
              v-model.number="$v.diaHorario.dia.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.horaEntrada')" for="dia-horario-horaEntrada"
              >Hora Entrada</label
            >
            <div class="d-flex">
              <input
                id="dia-horario-horaEntrada"
                data-cy="horaEntrada"
                type="datetime-local"
                class="form-control"
                name="horaEntrada"
                :class="{ valid: !$v.diaHorario.horaEntrada.$invalid, invalid: $v.diaHorario.horaEntrada.$invalid }"
                :value="convertDateTimeFromServer($v.diaHorario.horaEntrada.$model)"
                @change="updateInstantField('horaEntrada', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.horaSalida')" for="dia-horario-horaSalida"
              >Hora Salida</label
            >
            <div class="d-flex">
              <input
                id="dia-horario-horaSalida"
                data-cy="horaSalida"
                type="datetime-local"
                class="form-control"
                name="horaSalida"
                :class="{ valid: !$v.diaHorario.horaSalida.$invalid, invalid: $v.diaHorario.horaSalida.$invalid }"
                :value="convertDateTimeFromServer($v.diaHorario.horaSalida.$model)"
                @change="updateInstantField('horaSalida', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.createByUser')" for="dia-horario-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="dia-horario-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.diaHorario.createByUser.$invalid, invalid: $v.diaHorario.createByUser.$invalid }"
              v-model="$v.diaHorario.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.createdOn')" for="dia-horario-createdOn"
              >Created On</label
            >
            <div class="d-flex">
              <input
                id="dia-horario-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.diaHorario.createdOn.$invalid, invalid: $v.diaHorario.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.diaHorario.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.modifyByUser')" for="dia-horario-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="dia-horario-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.diaHorario.modifyByUser.$invalid, invalid: $v.diaHorario.modifyByUser.$invalid }"
              v-model="$v.diaHorario.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.modifiedOn')" for="dia-horario-modifiedOn"
              >Modified On</label
            >
            <div class="d-flex">
              <input
                id="dia-horario-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.diaHorario.modifiedOn.$invalid, invalid: $v.diaHorario.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.diaHorario.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.auditStatus')" for="dia-horario-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="dia-horario-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.diaHorario.auditStatus.$invalid, invalid: $v.diaHorario.auditStatus.$invalid }"
              v-model="$v.diaHorario.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.diaHorario.horario')" for="dia-horario-horario">Horario</label>
            <select class="form-control" id="dia-horario-horario" data-cy="horario" name="horario" v-model="diaHorario.horario">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="diaHorario.horario && horarioOption.id === diaHorario.horario.id ? diaHorario.horario : horarioOption"
                v-for="horarioOption in horarios"
                :key="horarioOption.id"
              >
                {{ horarioOption.id }}
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
            :disabled="$v.diaHorario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dia-horario-update.component.ts"></script>
