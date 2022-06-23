<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="inventario">
        <h2 class="jh-entity-heading" data-cy="inventarioDetailsHeading">
          <span v-text="$t('segmaflotApp.inventario.detail.title')">Inventario</span> {{ inventario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.inventario.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ inventario.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.cantidadMinimaEst')">Cantidad Minima Est</span>
          </dt>
          <dd>
            <span>{{ inventario.cantidadMinimaEst }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.cantidadMaximaEst')">Cantidad Maxima Est</span>
          </dt>
          <dd>
            <span>{{ inventario.cantidadMaximaEst }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.status')">Status</span>
          </dt>
          <dd>
            <span>{{ inventario.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ inventario.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="inventario.createdOn">{{ $d(Date.parse(inventario.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ inventario.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="inventario.modifiedOn">{{ $d(Date.parse(inventario.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ inventario.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.producto')">Producto</span>
          </dt>
          <dd>
            <div v-if="inventario.producto">
              <router-link :to="{ name: 'ProductoView', params: { productoId: inventario.producto.id } }">{{
                inventario.producto.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.inventario.sucursal')">Sucursal</span>
          </dt>
          <dd>
            <div v-if="inventario.sucursal">
              <router-link :to="{ name: 'SucursalView', params: { sucursalId: inventario.sucursal.id } }">{{
                inventario.sucursal.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="inventario.id"
          :to="{ name: 'InventarioEdit', params: { inventarioId: inventario.id } }"
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

<script lang="ts" src="./inventario-details.component.ts"></script>
