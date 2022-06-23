<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.perdida.home.createOrEditLabel"
          data-cy="PerdidaCreateUpdateHeading"
          v-text="$t('segmaflotApp.perdida.home.createOrEditLabel')"
        >
          Create or edit a Perdida
        </h2>
        <div>
          <div class="form-group" v-if="perdida.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="perdida.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.perdida.fecha')" for="perdida-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="perdida-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.perdida.fecha.$invalid, invalid: $v.perdida.fecha.$invalid }"
                :value="convertDateTimeFromServer($v.perdida.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.perdida.cantidad')" for="perdida-cantidad">Cantidad</label>
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="perdida-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.perdida.cantidad.$invalid, invalid: $v.perdida.cantidad.$invalid }"
              v-model.number="$v.perdida.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.perdida.observaciones')" for="perdida-observaciones"
              >Observaciones</label
            >
            <div>
              <div v-if="perdida.observaciones" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(perdida.observacionesContentType, perdida.observaciones)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ perdida.observacionesContentType }}, {{ byteSize(perdida.observaciones) }}</span>
                <button
                  type="button"
                  v-on:click="
                    perdida.observaciones = null;
                    perdida.observacionesContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_observaciones"
                id="file_observaciones"
                data-cy="observaciones"
                v-on:change="setFileData($event, perdida, 'observaciones', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="observaciones"
              id="perdida-observaciones"
              data-cy="observaciones"
              :class="{ valid: !$v.perdida.observaciones.$invalid, invalid: $v.perdida.observaciones.$invalid }"
              v-model="$v.perdida.observaciones.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="observacionesContentType"
              id="perdida-observacionesContentType"
              v-model="perdida.observacionesContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.perdida.inventario')" for="perdida-inventario">Inventario</label>
            <select class="form-control" id="perdida-inventario" data-cy="inventario" name="inventario" v-model="perdida.inventario">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="perdida.inventario && inventarioOption.id === perdida.inventario.id ? perdida.inventario : inventarioOption"
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
            :disabled="$v.perdida.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./perdida-update.component.ts"></script>
