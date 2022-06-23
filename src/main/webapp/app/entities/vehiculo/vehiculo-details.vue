<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vehiculo">
        <h2 class="jh-entity-heading" data-cy="vehiculoDetailsHeading">
          <span v-text="$t('segmaflotApp.vehiculo.detail.title')">Vehiculo</span> {{ vehiculo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.marca')">Marca</span>
          </dt>
          <dd>
            <span>{{ vehiculo.marca }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.submarca')">Submarca</span>
          </dt>
          <dd>
            <span>{{ vehiculo.submarca }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.modelo')">Modelo</span>
          </dt>
          <dd>
            <span>{{ vehiculo.modelo }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.generacion')">Generacion</span>
          </dt>
          <dd>
            <span>{{ vehiculo.generacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.tipoVehiculo')">Tipo Vehiculo</span>
          </dt>
          <dd>
            <span>{{ vehiculo.tipoVehiculo }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ vehiculo.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="vehiculo.createdOn">{{ $d(Date.parse(vehiculo.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ vehiculo.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="vehiculo.modifiedOn">{{ $d(Date.parse(vehiculo.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculo.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ vehiculo.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="vehiculo.id" :to="{ name: 'VehiculoEdit', params: { vehiculoId: vehiculo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vehiculo-details.component.ts"></script>
