<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vehiculoCliente">
        <h2 class="jh-entity-heading" data-cy="vehiculoClienteDetailsHeading">
          <span v-text="$t('segmaflotApp.vehiculoCliente.detail.title')">VehiculoCliente</span> {{ vehiculoCliente.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.numeroSerie')">Numero Serie</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.numeroSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.color')">Color</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.status')">Status</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="vehiculoCliente.createdOn">{{ $d(Date.parse(vehiculoCliente.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="vehiculoCliente.modifiedOn">{{ $d(Date.parse(vehiculoCliente.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ vehiculoCliente.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.vehiculoCliente.cliente')">Cliente</span>
          </dt>
          <dd>
            <div v-if="vehiculoCliente.cliente">
              <router-link :to="{ name: 'ClienteView', params: { clienteId: vehiculoCliente.cliente.id } }">{{
                vehiculoCliente.cliente.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="vehiculoCliente.id"
          :to="{ name: 'VehiculoClienteEdit', params: { vehiculoClienteId: vehiculoCliente.id } }"
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

<script lang="ts" src="./vehiculo-cliente-details.component.ts"></script>
