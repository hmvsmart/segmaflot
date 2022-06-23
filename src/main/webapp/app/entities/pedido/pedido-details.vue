<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="pedido">
        <h2 class="jh-entity-heading" data-cy="pedidoDetailsHeading">
          <span v-text="$t('segmaflotApp.pedido.detail.title')">Pedido</span> {{ pedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.pedido.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ pedido.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ pedido.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="pedido.createdOn">{{ $d(Date.parse(pedido.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ pedido.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="pedido.modifiedOn">{{ $d(Date.parse(pedido.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ pedido.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.pedido.empresa')">Empresa</span>
          </dt>
          <dd>
            <div v-if="pedido.empresa">
              <router-link :to="{ name: 'PersonaMoralView', params: { personaMoralId: pedido.empresa.id } }">{{
                pedido.empresa.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="pedido.id" :to="{ name: 'PedidoEdit', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./pedido-details.component.ts"></script>
