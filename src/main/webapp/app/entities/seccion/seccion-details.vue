<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="seccion">
        <h2 class="jh-entity-heading" data-cy="seccionDetailsHeading">
          <span v-text="$t('segmaflotApp.seccion.detail.title')">Seccion</span> {{ seccion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.seccion.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ seccion.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ seccion.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="seccion.createdOn">{{ $d(Date.parse(seccion.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ seccion.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="seccion.modifiedOn">{{ $d(Date.parse(seccion.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ seccion.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.seccion.nivel')">Nivel</span>
          </dt>
          <dd>
            <div v-if="seccion.nivel">
              <router-link :to="{ name: 'NivelView', params: { nivelId: seccion.nivel.id } }">{{ seccion.nivel.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="seccion.id" :to="{ name: 'SeccionEdit', params: { seccionId: seccion.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./seccion-details.component.ts"></script>
