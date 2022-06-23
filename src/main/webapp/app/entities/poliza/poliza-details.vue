<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="poliza">
        <h2 class="jh-entity-heading" data-cy="polizaDetailsHeading">
          <span v-text="$t('segmaflotApp.poliza.detail.title')">Poliza</span> {{ poliza.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.poliza.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ poliza.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.fechaVencimiento')">Fecha Vencimiento</span>
          </dt>
          <dd>
            <span>{{ poliza.fechaVencimiento }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.numeroPoliza')">Numero Poliza</span>
          </dt>
          <dd>
            <span>{{ poliza.numeroPoliza }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ poliza.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="poliza.createdOn">{{ $d(Date.parse(poliza.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ poliza.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="poliza.modifiedOn">{{ $d(Date.parse(poliza.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ poliza.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.poliza.unidad')">Unidad</span>
          </dt>
          <dd>
            <div v-if="poliza.unidad">
              <router-link :to="{ name: 'UnidadView', params: { unidadId: poliza.unidad.id } }">{{ poliza.unidad.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="poliza.id" :to="{ name: 'PolizaEdit', params: { polizaId: poliza.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./poliza-details.component.ts"></script>
