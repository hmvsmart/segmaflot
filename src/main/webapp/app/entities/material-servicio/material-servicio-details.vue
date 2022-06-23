<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="materialServicio">
        <h2 class="jh-entity-heading" data-cy="materialServicioDetailsHeading">
          <span v-text="$t('segmaflotApp.materialServicio.detail.title')">MaterialServicio</span> {{ materialServicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ materialServicio.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ materialServicio.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ materialServicio.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="materialServicio.createdOn">{{ $d(Date.parse(materialServicio.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ materialServicio.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="materialServicio.modifiedOn">{{ $d(Date.parse(materialServicio.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ materialServicio.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="materialServicio.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: materialServicio.inventario.id } }">{{
                materialServicio.inventario.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.materialServicio.servicio')">Servicio</span>
          </dt>
          <dd>
            <div v-if="materialServicio.servicio">
              <router-link :to="{ name: 'ServicioView', params: { servicioId: materialServicio.servicio.id } }">{{
                materialServicio.servicio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="materialServicio.id"
          :to="{ name: 'MaterialServicioEdit', params: { materialServicioId: materialServicio.id } }"
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

<script lang="ts" src="./material-servicio-details.component.ts"></script>
