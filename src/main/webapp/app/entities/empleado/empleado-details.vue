<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="empleado">
        <h2 class="jh-entity-heading" data-cy="empleadoDetailsHeading">
          <span v-text="$t('segmaflotApp.empleado.detail.title')">Empleado</span> {{ empleado.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.empleado.rfc')">Rfc</span>
          </dt>
          <dd>
            <span>{{ empleado.rfc }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.nss')">Nss</span>
          </dt>
          <dd>
            <span>{{ empleado.nss }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.finicio')">Finicio</span>
          </dt>
          <dd>
            <span>{{ empleado.finicio }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.puesto')">Puesto</span>
          </dt>
          <dd>
            <span>{{ empleado.puesto }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.salario')">Salario</span>
          </dt>
          <dd>
            <span>{{ empleado.salario }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.diaPago')">Dia Pago</span>
          </dt>
          <dd>
            <span>{{ empleado.diaPago }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.tipoPago')">Tipo Pago</span>
          </dt>
          <dd>
            <span>{{ empleado.tipoPago }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ empleado.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="empleado.createdOn">{{ $d(Date.parse(empleado.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ empleado.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="empleado.modifiedOn">{{ $d(Date.parse(empleado.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ empleado.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="empleado.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: empleado.persona.id } }">{{ empleado.persona.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.empleado.sucursal')">Sucursal</span>
          </dt>
          <dd>
            <div v-if="empleado.sucursal">
              <router-link :to="{ name: 'SucursalView', params: { sucursalId: empleado.sucursal.id } }">{{
                empleado.sucursal.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="empleado.id" :to="{ name: 'EmpleadoEdit', params: { empleadoId: empleado.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./empleado-details.component.ts"></script>
