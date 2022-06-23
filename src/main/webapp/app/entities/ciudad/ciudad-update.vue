<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.ciudad.home.createOrEditLabel"
          data-cy="CiudadCreateUpdateHeading"
          v-text="$t('segmaflotApp.ciudad.home.createOrEditLabel')"
        >
          Create or edit a Ciudad
        </h2>
        <div>
          <div class="form-group" v-if="ciudad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ciudad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ciudad.nombre')" for="ciudad-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="ciudad-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.ciudad.nombre.$invalid, invalid: $v.ciudad.nombre.$invalid }"
              v-model="$v.ciudad.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ciudad.estado')" for="ciudad-estado">Estado</label>
            <select class="form-control" id="ciudad-estado" data-cy="estado" name="estado" v-model="ciudad.estado">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ciudad.estado && estadoOption.id === ciudad.estado.id ? ciudad.estado : estadoOption"
                v-for="estadoOption in estados"
                :key="estadoOption.id"
              >
                {{ estadoOption.id }}
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
            :disabled="$v.ciudad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ciudad-update.component.ts"></script>
