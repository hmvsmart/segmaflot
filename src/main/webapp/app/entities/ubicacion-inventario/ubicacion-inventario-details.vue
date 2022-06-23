<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ubicacionInventario">
        <h2 class="jh-entity-heading" data-cy="ubicacionInventarioDetailsHeading">
          <span v-text="$t('segmaflotApp.ubicacionInventario.detail.title')">UbicacionInventario</span> {{ ubicacionInventario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.status')">Status</span>
          </dt>
          <dd>
            <span>{{ ubicacionInventario.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ ubicacionInventario.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="ubicacionInventario.createdOn">{{ $d(Date.parse(ubicacionInventario.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ ubicacionInventario.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="ubicacionInventario.modifiedOn">{{ $d(Date.parse(ubicacionInventario.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ ubicacionInventario.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.seccion')">Seccion</span>
          </dt>
          <dd>
            <div v-if="ubicacionInventario.seccion">
              <router-link :to="{ name: 'SeccionView', params: { seccionId: ubicacionInventario.seccion.id } }">{{
                ubicacionInventario.seccion.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.ubicacionInventario.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="ubicacionInventario.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: ubicacionInventario.inventario.id } }">{{
                ubicacionInventario.inventario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="ubicacionInventario.id"
          :to="{ name: 'UbicacionInventarioEdit', params: { ubicacionInventarioId: ubicacionInventario.id } }"
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

<script lang="ts" src="./ubicacion-inventario-details.component.ts"></script>
