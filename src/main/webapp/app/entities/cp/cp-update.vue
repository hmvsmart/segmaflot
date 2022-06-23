<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.cP.home.createOrEditLabel"
          data-cy="CPCreateUpdateHeading"
          v-text="$t('segmaflotApp.cP.home.createOrEditLabel')"
        >
          Create or edit a CP
        </h2>
        <div>
          <div class="form-group" v-if="cP.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cP.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cP.cp')" for="cp-cp">Cp</label>
            <input
              type="number"
              class="form-control"
              name="cp"
              id="cp-cp"
              data-cy="cp"
              :class="{ valid: !$v.cP.cp.$invalid, invalid: $v.cP.cp.$invalid }"
              v-model.number="$v.cP.cp.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.cP.ciudad')" for="cp-ciudad">Ciudad</label>
            <select class="form-control" id="cp-ciudad" data-cy="ciudad" name="ciudad" v-model="cP.ciudad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cP.ciudad && ciudadOption.id === cP.ciudad.id ? cP.ciudad : ciudadOption"
                v-for="ciudadOption in ciudads"
                :key="ciudadOption.id"
              >
                {{ ciudadOption.id }}
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
            :disabled="$v.cP.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cp-update.component.ts"></script>
