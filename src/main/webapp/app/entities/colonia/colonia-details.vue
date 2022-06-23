<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="colonia">
        <h2 class="jh-entity-heading" data-cy="coloniaDetailsHeading">
          <span v-text="$t('segmaflotApp.colonia.detail.title')">Colonia</span> {{ colonia.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.colonia.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ colonia.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.colonia.cp')">Cp</span>
          </dt>
          <dd>
            <div v-if="colonia.cp">
              <router-link :to="{ name: 'CPView', params: { cPId: colonia.cp.id } }">{{ colonia.cp.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="colonia.id" :to="{ name: 'ColoniaEdit', params: { coloniaId: colonia.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./colonia-details.component.ts"></script>
