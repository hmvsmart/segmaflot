<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cotizacion">
        <h2 class="jh-entity-heading" data-cy="cotizacionDetailsHeading">
          <span v-text="$t('segmaflotApp.cotizacion.detail.title')">Cotizacion</span> {{ cotizacion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ cotizacion.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ cotizacion.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="cotizacion.createdOn">{{ $d(Date.parse(cotizacion.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ cotizacion.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="cotizacion.modifiedOn">{{ $d(Date.parse(cotizacion.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cotizacion.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ cotizacion.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="cotizacion.id"
          :to="{ name: 'CotizacionEdit', params: { cotizacionId: cotizacion.id } }"
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

<script lang="ts" src="./cotizacion-details.component.ts"></script>
