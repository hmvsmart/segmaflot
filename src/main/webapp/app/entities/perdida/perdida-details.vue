<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="perdida">
        <h2 class="jh-entity-heading" data-cy="perdidaDetailsHeading">
          <span v-text="$t('segmaflotApp.perdida.detail.title')">Perdida</span> {{ perdida.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.perdida.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="perdida.fecha">{{ $d(Date.parse(perdida.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.perdida.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ perdida.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.perdida.observaciones')">Observaciones</span>
          </dt>
          <dd>
            <div v-if="perdida.observaciones">
              <a v-on:click="openFile(perdida.observacionesContentType, perdida.observaciones)" v-text="$t('entity.action.open')">open</a>
              {{ perdida.observacionesContentType }}, {{ byteSize(perdida.observaciones) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.perdida.inventario')">Inventario</span>
          </dt>
          <dd>
            <div v-if="perdida.inventario">
              <router-link :to="{ name: 'InventarioView', params: { inventarioId: perdida.inventario.id } }">{{
                perdida.inventario.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="perdida.id" :to="{ name: 'PerdidaEdit', params: { perdidaId: perdida.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./perdida-details.component.ts"></script>
