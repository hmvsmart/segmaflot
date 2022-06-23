<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ventaPedido">
        <h2 class="jh-entity-heading" data-cy="ventaPedidoDetailsHeading">
          <span v-text="$t('segmaflotApp.ventaPedido.detail.title')">VentaPedido</span> {{ ventaPedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.ventaPedido.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ ventaPedido.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ventaPedido.venta')">Venta</span>
          </dt>
          <dd>
            <div v-if="ventaPedido.venta">
              <router-link :to="{ name: 'VentaView', params: { ventaId: ventaPedido.venta.id } }">{{ ventaPedido.venta.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ventaPedido.pedido')">Pedido</span>
          </dt>
          <dd>
            <div v-if="ventaPedido.pedido">
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: ventaPedido.pedido.id } }">{{
                ventaPedido.pedido.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="ventaPedido.id"
          :to="{ name: 'VentaPedidoEdit', params: { ventaPedidoId: ventaPedido.id } }"
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

<script lang="ts" src="./venta-pedido-details.component.ts"></script>
