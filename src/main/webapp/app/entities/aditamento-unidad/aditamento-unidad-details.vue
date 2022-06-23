<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="aditamentoUnidad">
        <h2 class="jh-entity-heading" data-cy="aditamentoUnidadDetailsHeading">
          <span v-text="$t('segmaflotApp.aditamentoUnidad.detail.title')">AditamentoUnidad</span> {{ aditamentoUnidad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ aditamentoUnidad.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.numeroSerie')">Numero Serie</span>
          </dt>
          <dd>
            <span>{{ aditamentoUnidad.numeroSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ aditamentoUnidad.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="aditamentoUnidad.createdOn">{{ $d(Date.parse(aditamentoUnidad.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ aditamentoUnidad.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="aditamentoUnidad.modifiedOn">{{ $d(Date.parse(aditamentoUnidad.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ aditamentoUnidad.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.aditamento')">Aditamento</span>
          </dt>
          <dd>
            <div v-if="aditamentoUnidad.aditamento">
              <router-link :to="{ name: 'AditamentoView', params: { aditamentoId: aditamentoUnidad.aditamento.id } }">{{
                aditamentoUnidad.aditamento.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.aditamentoUnidad.unidad')">Unidad</span>
          </dt>
          <dd>
            <div v-if="aditamentoUnidad.unidad">
              <router-link :to="{ name: 'UnidadView', params: { unidadId: aditamentoUnidad.unidad.id } }">{{
                aditamentoUnidad.unidad.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="aditamentoUnidad.id"
          :to="{ name: 'AditamentoUnidadEdit', params: { aditamentoUnidadId: aditamentoUnidad.id } }"
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

<script lang="ts" src="./aditamento-unidad-details.component.ts"></script>
