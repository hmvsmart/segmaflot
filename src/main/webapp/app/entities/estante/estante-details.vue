<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estante">
        <h2 class="jh-entity-heading" data-cy="estanteDetailsHeading">
          <span v-text="$t('segmaflotApp.estante.detail.title')">Estante</span> {{ estante.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.estante.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ estante.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.material')">Material</span>
          </dt>
          <dd>
            <span>{{ estante.material }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.color')">Color</span>
          </dt>
          <dd>
            <span>{{ estante.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <span>{{ estante.descripcion }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ estante.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="estante.createdOn">{{ $d(Date.parse(estante.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ estante.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="estante.modifiedOn">{{ $d(Date.parse(estante.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.estante.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ estante.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="estante.id" :to="{ name: 'EstanteEdit', params: { estanteId: estante.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estante-details.component.ts"></script>
