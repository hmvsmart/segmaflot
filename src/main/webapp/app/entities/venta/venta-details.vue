<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="venta">
        <h2 class="jh-entity-heading" data-cy="ventaDetailsHeading">
          <span v-text="$t('segmaflotApp.venta.detail.title')">Venta</span> {{ venta.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.venta.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="venta.fecha">{{ $d(Date.parse(venta.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.total')">Total</span>
          </dt>
          <dd>
            <span>{{ venta.total }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ venta.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="venta.createdOn">{{ $d(Date.parse(venta.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ venta.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="venta.modifiedOn">{{ $d(Date.parse(venta.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.venta.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ venta.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="venta.id" :to="{ name: 'VentaEdit', params: { ventaId: venta.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./venta-details.component.ts"></script>
