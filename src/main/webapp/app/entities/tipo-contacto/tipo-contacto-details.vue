<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoContacto">
        <h2 class="jh-entity-heading" data-cy="tipoContactoDetailsHeading">
          <span v-text="$t('segmaflotApp.tipoContacto.detail.title')">TipoContacto</span> {{ tipoContacto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ tipoContacto.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ tipoContacto.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="tipoContacto.createdOn">{{ $d(Date.parse(tipoContacto.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ tipoContacto.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="tipoContacto.modifiedOn">{{ $d(Date.parse(tipoContacto.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.tipoContacto.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ tipoContacto.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="tipoContacto.id"
          :to="{ name: 'TipoContactoEdit', params: { tipoContactoId: tipoContacto.id } }"
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

<script lang="ts" src="./tipo-contacto-details.component.ts"></script>
