<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estado">
        <h2 class="jh-entity-heading" data-cy="estadoDetailsHeading">
          <span v-text="$t('segmaflotApp.estado.detail.title')">Estado</span> {{ estado.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.estado.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ estado.nombre }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="estado.id" :to="{ name: 'EstadoEdit', params: { estadoId: estado.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estado-details.component.ts"></script>
