<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.ventaPedido.home.createOrEditLabel"
          data-cy="VentaPedidoCreateUpdateHeading"
          v-text="$t('segmaflotApp.ventaPedido.home.createOrEditLabel')"
        >
          Create or edit a VentaPedido
        </h2>
        <div>
          <div class="form-group" v-if="ventaPedido.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ventaPedido.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ventaPedido.fecha')" for="venta-pedido-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="venta-pedido-fecha"
                  v-model="$v.ventaPedido.fecha.$model"
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
                id="venta-pedido-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.ventaPedido.fecha.$invalid, invalid: $v.ventaPedido.fecha.$invalid }"
                v-model="$v.ventaPedido.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ventaPedido.venta')" for="venta-pedido-venta">Venta</label>
            <select class="form-control" id="venta-pedido-venta" data-cy="venta" name="venta" v-model="ventaPedido.venta">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ventaPedido.venta && ventaOption.id === ventaPedido.venta.id ? ventaPedido.venta : ventaOption"
                v-for="ventaOption in ventas"
                :key="ventaOption.id"
              >
                {{ ventaOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.ventaPedido.pedido')" for="venta-pedido-pedido">Pedido</label>
            <select class="form-control" id="venta-pedido-pedido" data-cy="pedido" name="pedido" v-model="ventaPedido.pedido">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ventaPedido.pedido && pedidoOption.id === ventaPedido.pedido.id ? ventaPedido.pedido : pedidoOption"
                v-for="pedidoOption in pedidos"
                :key="pedidoOption.id"
              >
                {{ pedidoOption.id }}
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
            :disabled="$v.ventaPedido.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./venta-pedido-update.component.ts"></script>
