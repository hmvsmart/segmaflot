<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="renglonVenta">
        <h2 class="jh-entity-heading" data-cy="renglonVentaDetailsHeading">
          <span v-text="$t('segmaflotApp.renglonVenta.detail.title')">RenglonVenta</span> {{ renglonVenta.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ renglonVenta.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ renglonVenta.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="renglonVenta.createdOn">{{ $d(Date.parse(renglonVenta.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ renglonVenta.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="renglonVenta.modifiedOn">{{ $d(Date.parse(renglonVenta.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ renglonVenta.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.venta')">Venta</span>
          </dt>
          <dd>
            <div v-if="renglonVenta.venta">
              <router-link :to="{ name: 'VentaView', params: { ventaId: renglonVenta.venta.id } }">{{ renglonVenta.venta.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.renglonVenta.precioventa')">Precioventa</span>
          </dt>
          <dd>
            <div v-if="renglonVenta.precioventa">
              <router-link :to="{ name: 'PrecioVentaView', params: { precioVentaId: renglonVenta.precioventa.id } }">{{
                renglonVenta.precioventa.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="renglonVenta.id"
          :to="{ name: 'RenglonVentaEdit', params: { renglonVentaId: renglonVenta.id } }"
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

<script lang="ts" src="./renglon-venta-details.component.ts"></script>
