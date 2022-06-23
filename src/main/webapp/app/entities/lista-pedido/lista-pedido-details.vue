<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="listaPedido">
        <h2 class="jh-entity-heading" data-cy="listaPedidoDetailsHeading">
          <span v-text="$t('segmaflotApp.listaPedido.detail.title')">ListaPedido</span> {{ listaPedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.listaPedido.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ listaPedido.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaPedido.pedido')">Pedido</span>
          </dt>
          <dd>
            <div v-if="listaPedido.pedido">
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: listaPedido.pedido.id } }">{{
                listaPedido.pedido.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaPedido.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="listaPedido.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: listaPedido.inventario.id } }">{{
                listaPedido.inventario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="listaPedido.id"
          :to="{ name: 'ListaPedidoEdit', params: { listaPedidoId: listaPedido.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./lista-pedido-details.component.ts"></script>
