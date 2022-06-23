<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="segmaflotApp.listaPedido.home.createOrEditLabel"
          data-cy="ListaPedidoCreateUpdateHeading"
          v-text="$t('segmaflotApp.listaPedido.home.createOrEditLabel')"
        >
          Create or edit a ListaPedido
        </h2>
        <div>
          <div class="form-group" v-if="listaPedido.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="listaPedido.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaPedido.cantidad')" for="lista-pedido-cantidad">Cantidad</label>
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="lista-pedido-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.listaPedido.cantidad.$invalid, invalid: $v.listaPedido.cantidad.$invalid }"
              v-model.number="$v.listaPedido.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaPedido.pedido')" for="lista-pedido-pedido">Pedido</label>
            <select class="form-control" id="lista-pedido-pedido" data-cy="pedido" name="pedido" v-model="listaPedido.pedido">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="listaPedido.pedido && pedidoOption.id === listaPedido.pedido.id ? listaPedido.pedido : pedidoOption"
                v-for="pedidoOption in pedidos"
                :key="pedidoOption.id"
              >
                {{ pedidoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('segmaflotApp.listaPedido.inventario')" for="lista-pedido-inventario"
              >Inventario</label
            >
            <select
              class="form-control"
              id="lista-pedido-inventario"
              data-cy="inventario"
              name="inventario"
              v-model="listaPedido.inventario"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  listaPedido.inventario && inventarioOption.id === listaPedido.inventario.id ? listaPedido.inventario : inventarioOption
                "
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
            :disabled="$v.listaPedido.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./lista-pedido-update.component.ts"></script>
