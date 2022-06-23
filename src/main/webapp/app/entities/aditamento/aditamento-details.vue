<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="aditamento">
        <h2 class="jh-entity-heading" data-cy="aditamentoDetailsHeading">
          <span v-text="$t('segmaflotApp.aditamento.detail.title')">Aditamento</span> {{ aditamento.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ aditamento.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.numeroSerie')">Numero Serie</span>
          </dt>
          <dd>
            <span>{{ aditamento.numeroSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <div v-if="aditamento.descripcion">
              <a v-on:click="openFile(aditamento.descripcionContentType, aditamento.descripcion)" v-text="$t('entity.action.open')">open</a>
              {{ aditamento.descripcionContentType }}, {{ byteSize(aditamento.descripcion) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ aditamento.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="aditamento.createdOn">{{ $d(Date.parse(aditamento.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ aditamento.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="aditamento.modifiedOn">{{ $d(Date.parse(aditamento.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamento.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ aditamento.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="aditamento.id"
          :to="{ name: 'AditamentoEdit', params: { aditamentoId: aditamento.id } }"
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

<script lang="ts" src="./aditamento-details.component.ts"></script>
