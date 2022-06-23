<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="precioVenta">
        <h2 class="jh-entity-heading" data-cy="precioVentaDetailsHeading">
          <span v-text="$t('segmaflotApp.precioVenta.detail.title')">PrecioVenta</span> {{ precioVenta.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ precioVenta.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.ppu')">Ppu</span>
          </dt>
          <dd>
            <span>{{ precioVenta.ppu }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ precioVenta.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="precioVenta.createdOn">{{ $d(Date.parse(precioVenta.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ precioVenta.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="precioVenta.modifiedOn">{{ $d(Date.parse(precioVenta.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ precioVenta.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.precioVenta.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="precioVenta.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: precioVenta.inventario.id } }">{{
                precioVenta.inventario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="precioVenta.id"
          :to="{ name: 'PrecioVentaEdit', params: { precioVentaId: precioVenta.id } }"
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

<script lang="ts" src="./precio-venta-details.component.ts"></script>
