<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="operadorUnidad">
        <h2 class="jh-entity-heading" data-cy="operadorUnidadDetailsHeading">
          <span v-text="$t('segmaflotApp.operadorUnidad.detail.title')">OperadorUnidad</span> {{ operadorUnidad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="operadorUnidad.fecha">{{ $d(Date.parse(operadorUnidad.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.rol')">Rol</span>
          </dt>
          <dd>
            <span>{{ operadorUnidad.rol }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.status')">Status</span>
          </dt>
          <dd>
            <span>{{ operadorUnidad.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ operadorUnidad.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="operadorUnidad.createdOn">{{ $d(Date.parse(operadorUnidad.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ operadorUnidad.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="operadorUnidad.modifiedOn">{{ $d(Date.parse(operadorUnidad.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ operadorUnidad.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.unidasViaje')">Unidas Viaje</span>
          </dt>
          <dd>
            <div v-if="operadorUnidad.unidasViaje">
              <router-link :to="{ name: 'UnidadViajeView', params: { unidadViajeId: operadorUnidad.unidasViaje.id } }">{{
                operadorUnidad.unidasViaje.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.operadorUnidad.empleado')">Empleado</span>
          </dt>
          <dd>
            <div v-if="operadorUnidad.empleado">
              <router-link :to="{ name: 'EmpleadoView', params: { empleadoId: operadorUnidad.empleado.id } }">{{
                operadorUnidad.empleado.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="operadorUnidad.id"
          :to="{ name: 'OperadorUnidadEdit', params: { operadorUnidadId: operadorUnidad.id } }"
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

<script lang="ts" src="./operador-unidad-details.component.ts"></script>
