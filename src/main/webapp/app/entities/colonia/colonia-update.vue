<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.colonia.home.createOrEditLabel"
          data-cy="ColoniaCreateUpdateHeading"
          v-text="$t('segmaflotApp.colonia.home.createOrEditLabel')"
        >
          Create or edit a Colonia
        </h2>
        <div>
          <div class="form-group" v-if="colonia.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="colonia.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.colonia.nombre')" for="colonia-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="colonia-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.colonia.nombre.$invalid, invalid: $v.colonia.nombre.$invalid }"
              v-model="$v.colonia.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.colonia.cp')" for="colonia-cp">Cp</label>
            <select class="form-control" id="colonia-cp" data-cy="cp" name="cp" v-model="colonia.cp">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="colonia.cp && cPOption.id === colonia.cp.id ? colonia.cp : cPOption"
                v-for="cPOption in cPS"
                :key="cPOption.id"
              >
                {{ cPOption.id }}
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
            :disabled="$v.colonia.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./colonia-update.component.ts"></script>
