<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cliente">
        <h2 class="jh-entity-heading" data-cy="clienteDetailsHeading">
          <span v-text="$t('segmaflotApp.cliente.detail.title')">Cliente</span> {{ cliente.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.cliente.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ cliente.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.observaciones')">Observaciones</span>
          </dt>
          <dd>
            <div v-if="cliente.observaciones">
              <a v-on:click="openFile(cliente.observacionesContentType, cliente.observaciones)" v-text="$t('entity.action.open')">open</a>
              {{ cliente.observacionesContentType }}, {{ byteSize(cliente.observaciones) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ cliente.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="cliente.createdOn">{{ $d(Date.parse(cliente.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ cliente.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="cliente.modifiedOn">{{ $d(Date.parse(cliente.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ cliente.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.cliente.persona')">Persona</span>
          </dt>
          <dd>
            <div v-if="cliente.persona">
              <router-link :to="{ name: 'PersonaView', params: { personaId: cliente.persona.id } }">{{ cliente.persona.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cliente.id" :to="{ name: 'ClienteEdit', params: { clienteId: cliente.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cliente-details.component.ts"></script>
