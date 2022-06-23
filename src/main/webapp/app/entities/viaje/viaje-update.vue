<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.viaje.home.createOrEditLabel"
          data-cy="ViajeCreateUpdateHeading"
          v-text="$t('segmaflotApp.viaje.home.createOrEditLabel')"
        >
          Create or edit a Viaje
        </h2>
        <div>
          <div class="form-group" v-if="viaje.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="viaje.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.fecha')" for="viaje-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="viaje-fecha"
                  v-model="$v.viaje.fecha.$model"
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
                id="viaje-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.viaje.fecha.$invalid, invalid: $v.viaje.fecha.$invalid }"
                v-model="$v.viaje.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.fechaInicio')" for="viaje-fechaInicio">Fecha Inicio</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="viaje-fechaInicio"
                  v-model="$v.viaje.fechaInicio.$model"
                  name="fechaInicio"
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
                id="viaje-fechaInicio"
                data-cy="fechaInicio"
                type="text"
                class="form-control"
                name="fechaInicio"
                :class="{ valid: !$v.viaje.fechaInicio.$invalid, invalid: $v.viaje.fechaInicio.$invalid }"
                v-model="$v.viaje.fechaInicio.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.fechaFin')" for="viaje-fechaFin">Fecha Fin</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="viaje-fechaFin"
                  v-model="$v.viaje.fechaFin.$model"
                  name="fechaFin"
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
                id="viaje-fechaFin"
                data-cy="fechaFin"
                type="text"
                class="form-control"
                name="fechaFin"
                :class="{ valid: !$v.viaje.fechaFin.$invalid, invalid: $v.viaje.fechaFin.$invalid }"
                v-model="$v.viaje.fechaFin.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.horaEmbarque')" for="viaje-horaEmbarque">Hora Embarque</label>
            <div class="d-flex">
              <input
                id="viaje-horaEmbarque"
                data-cy="horaEmbarque"
                type="datetime-local"
                class="form-control"
                name="horaEmbarque"
                :class="{ valid: !$v.viaje.horaEmbarque.$invalid, invalid: $v.viaje.horaEmbarque.$invalid }"
                :value="convertDateTimeFromServer($v.viaje.horaEmbarque.$model)"
                @change="updateInstantField('horaEmbarque', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.tipoCarga')" for="viaje-tipoCarga">Tipo Carga</label>
            <input
              type="text"
              class="form-control"
              name="tipoCarga"
              id="viaje-tipoCarga"
              data-cy="tipoCarga"
              :class="{ valid: !$v.viaje.tipoCarga.$invalid, invalid: $v.viaje.tipoCarga.$invalid }"
              v-model="$v.viaje.tipoCarga.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.pesoNeto')" for="viaje-pesoNeto">Peso Neto</label>
            <input
              type="number"
              class="form-control"
              name="pesoNeto"
              id="viaje-pesoNeto"
              data-cy="pesoNeto"
              :class="{ valid: !$v.viaje.pesoNeto.$invalid, invalid: $v.viaje.pesoNeto.$invalid }"
              v-model.number="$v.viaje.pesoNeto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.comentarios')" for="viaje-comentarios">Comentarios</label>
            <div>
              <div v-if="viaje.comentarios" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(viaje.comentariosContentType, viaje.comentarios)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ viaje.comentariosContentType }}, {{ byteSize(viaje.comentarios) }}</span>
                <button
                  type="button"
                  v-on:click="
                    viaje.comentarios = null;
                    viaje.comentariosContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_comentarios"
                id="file_comentarios"
                data-cy="comentarios"
                v-on:change="setFileData($event, viaje, 'comentarios', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="comentarios"
              id="viaje-comentarios"
              data-cy="comentarios"
              :class="{ valid: !$v.viaje.comentarios.$invalid, invalid: $v.viaje.comentarios.$invalid }"
              v-model="$v.viaje.comentarios.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="comentariosContentType"
              id="viaje-comentariosContentType"
              v-model="viaje.comentariosContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.status')" for="viaje-status">Status</label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="viaje-status"
              data-cy="status"
              :class="{ valid: !$v.viaje.status.$invalid, invalid: $v.viaje.status.$invalid }"
              v-model="$v.viaje.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.createByUser')" for="viaje-createByUser">Create By User</label>
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="viaje-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.viaje.createByUser.$invalid, invalid: $v.viaje.createByUser.$invalid }"
              v-model="$v.viaje.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.createdOn')" for="viaje-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="viaje-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.viaje.createdOn.$invalid, invalid: $v.viaje.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.viaje.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.modifyByUser')" for="viaje-modifyByUser">Modify By User</label>
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="viaje-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.viaje.modifyByUser.$invalid, invalid: $v.viaje.modifyByUser.$invalid }"
              v-model="$v.viaje.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.modifiedOn')" for="viaje-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="viaje-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.viaje.modifiedOn.$invalid, invalid: $v.viaje.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.viaje.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.auditStatus')" for="viaje-auditStatus">Audit Status</label>
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="viaje-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.viaje.auditStatus.$invalid, invalid: $v.viaje.auditStatus.$invalid }"
              v-model="$v.viaje.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.viaje.origen')" for="viaje-origen">Origen</label>
            <select class="form-control" id="viaje-origen" data-cy="origen" name="origen" v-model="viaje.origen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="viaje.origen && direccionOption.id === viaje.origen.id ? viaje.origen : direccionOption"
                v-for="direccionOption in direccions"
                :key="direccionOption.id"
              >
                {{ direccionOption.id }}
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
            :disabled="$v.viaje.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./viaje-update.component.ts"></script>
