<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.empleado.home.createOrEditLabel"
          data-cy="EmpleadoCreateUpdateHeading"
          v-text="$t('segmaflotApp.empleado.home.createOrEditLabel')"
        >
          Create or edit a Empleado
        </h2>
        <div>
          <div class="form-group" v-if="empleado.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="empleado.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.rfc')" for="empleado-rfc">Rfc</label>
            <input
              type="text"
              class="form-control"
              name="rfc"
              id="empleado-rfc"
              data-cy="rfc"
              :class="{ valid: !$v.empleado.rfc.$invalid, invalid: $v.empleado.rfc.$invalid }"
              v-model="$v.empleado.rfc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.nss')" for="empleado-nss">Nss</label>
            <input
              type="text"
              class="form-control"
              name="nss"
              id="empleado-nss"
              data-cy="nss"
              :class="{ valid: !$v.empleado.nss.$invalid, invalid: $v.empleado.nss.$invalid }"
              v-model="$v.empleado.nss.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.finicio')" for="empleado-finicio">Finicio</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="empleado-finicio"
                  v-model="$v.empleado.finicio.$model"
                  name="finicio"
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
                id="empleado-finicio"
                data-cy="finicio"
                type="text"
                class="form-control"
                name="finicio"
                :class="{ valid: !$v.empleado.finicio.$invalid, invalid: $v.empleado.finicio.$invalid }"
                v-model="$v.empleado.finicio.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.puesto')" for="empleado-puesto">Puesto</label>
            <input
              type="text"
              class="form-control"
              name="puesto"
              id="empleado-puesto"
              data-cy="puesto"
              :class="{ valid: !$v.empleado.puesto.$invalid, invalid: $v.empleado.puesto.$invalid }"
              v-model="$v.empleado.puesto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.salario')" for="empleado-salario">Salario</label>
            <input
              type="number"
              class="form-control"
              name="salario"
              id="empleado-salario"
              data-cy="salario"
              :class="{ valid: !$v.empleado.salario.$invalid, invalid: $v.empleado.salario.$invalid }"
              v-model.number="$v.empleado.salario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.diaPago')" for="empleado-diaPago">Dia Pago</label>
            <input
              type="text"
              class="form-control"
              name="diaPago"
              id="empleado-diaPago"
              data-cy="diaPago"
              :class="{ valid: !$v.empleado.diaPago.$invalid, invalid: $v.empleado.diaPago.$invalid }"
              v-model="$v.empleado.diaPago.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.tipoPago')" for="empleado-tipoPago">Tipo Pago</label>
            <input
              type="text"
              class="form-control"
              name="tipoPago"
              id="empleado-tipoPago"
              data-cy="tipoPago"
              :class="{ valid: !$v.empleado.tipoPago.$invalid, invalid: $v.empleado.tipoPago.$invalid }"
              v-model="$v.empleado.tipoPago.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.createByUser')" for="empleado-createByUser"
              >Create By User</label
            >
            <input
              type="text"
              class="form-control"
              name="createByUser"
              id="empleado-createByUser"
              data-cy="createByUser"
              :class="{ valid: !$v.empleado.createByUser.$invalid, invalid: $v.empleado.createByUser.$invalid }"
              v-model="$v.empleado.createByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.createdOn')" for="empleado-createdOn">Created On</label>
            <div class="d-flex">
              <input
                id="empleado-createdOn"
                data-cy="createdOn"
                type="datetime-local"
                class="form-control"
                name="createdOn"
                :class="{ valid: !$v.empleado.createdOn.$invalid, invalid: $v.empleado.createdOn.$invalid }"
                :value="convertDateTimeFromServer($v.empleado.createdOn.$model)"
                @change="updateInstantField('createdOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.modifyByUser')" for="empleado-modifyByUser"
              >Modify By User</label
            >
            <input
              type="text"
              class="form-control"
              name="modifyByUser"
              id="empleado-modifyByUser"
              data-cy="modifyByUser"
              :class="{ valid: !$v.empleado.modifyByUser.$invalid, invalid: $v.empleado.modifyByUser.$invalid }"
              v-model="$v.empleado.modifyByUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.modifiedOn')" for="empleado-modifiedOn">Modified On</label>
            <div class="d-flex">
              <input
                id="empleado-modifiedOn"
                data-cy="modifiedOn"
                type="datetime-local"
                class="form-control"
                name="modifiedOn"
                :class="{ valid: !$v.empleado.modifiedOn.$invalid, invalid: $v.empleado.modifiedOn.$invalid }"
                :value="convertDateTimeFromServer($v.empleado.modifiedOn.$model)"
                @change="updateInstantField('modifiedOn', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.auditStatus')" for="empleado-auditStatus"
              >Audit Status</label
            >
            <input
              type="text"
              class="form-control"
              name="auditStatus"
              id="empleado-auditStatus"
              data-cy="auditStatus"
              :class="{ valid: !$v.empleado.auditStatus.$invalid, invalid: $v.empleado.auditStatus.$invalid }"
              v-model="$v.empleado.auditStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.persona')" for="empleado-persona">Persona</label>
            <select class="form-control" id="empleado-persona" data-cy="persona" name="persona" v-model="empleado.persona">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="empleado.persona && personaOption.id === empleado.persona.id ? empleado.persona : personaOption"
                v-for="personaOption in personas"
                :key="personaOption.id"
              >
                {{ personaOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.empleado.sucursal')" for="empleado-sucursal">Sucursal</label>
            <select class="form-control" id="empleado-sucursal" data-cy="sucursal" name="sucursal" v-model="empleado.sucursal">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="empleado.sucursal && sucursalOption.id === empleado.sucursal.id ? empleado.sucursal : sucursalOption"
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
            :disabled="$v.empleado.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./empleado-update.component.ts"></script>
