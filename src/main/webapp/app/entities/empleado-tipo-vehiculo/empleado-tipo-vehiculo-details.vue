<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="empleadoTipoVehiculo">
        <h2 class="jh-entity-heading" data-cy="empleadoTipoVehiculoDetailsHeading">
          <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.detail.title')">EmpleadoTipoVehiculo</span> {{ empleadoTipoVehiculo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ empleadoTipoVehiculo.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.status')">Status</span>
          </dt>
          <dd>
            <span>{{ empleadoTipoVehiculo.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ empleadoTipoVehiculo.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="empleadoTipoVehiculo.createdOn">{{ $d(Date.parse(empleadoTipoVehiculo.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ empleadoTipoVehiculo.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="empleadoTipoVehiculo.modifiedOn">{{ $d(Date.parse(empleadoTipoVehiculo.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ empleadoTipoVehiculo.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleadoTipoVehiculo.empleado')">Empleado</span>
          </dt>
          <dd>
            <div v-if="empleadoTipoVehiculo.empleado">
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: empleadoTipoVehiculo.empleado.id } }">{{
                empleadoTipoVehiculo.empleado.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="empleadoTipoVehiculo.id"
          :to="{ name: 'EmpleadoTipoVehiculoEdit', params: { empleadoTipoVehiculoId: empleadoTipoVehiculo.id } }"
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

<script lang="ts" src="./empleado-tipo-vehiculo-details.component.ts"></script>
