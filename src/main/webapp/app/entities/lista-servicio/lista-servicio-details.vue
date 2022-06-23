<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="listaServicio">
        <h2 class="jh-entity-heading" data-cy="listaServicioDetailsHeading">
          <span v-text="$t('segmaflotApp.listaServicio.detail.title')">ListaServicio</span> {{ listaServicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ listaServicio.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ listaServicio.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="listaServicio.createdOn">{{ $d(Date.parse(listaServicio.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ listaServicio.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="listaServicio.modifiedOn">{{ $d(Date.parse(listaServicio.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ listaServicio.auditStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.servicio')">Servicio</span>
          </dt>
          <dd>
            <div v-if="listaServicio.servicio">
              <router-link :to="{ name: 'ServicioView', params: { servicioId: listaServicio.servicio.id } }">{{
                listaServicio.servicio.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.listaServicio.unidadServicio')">Unidad Servicio</span>
          </dt>
          <dd>
            <div v-if="listaServicio.unidadServicio">
              <router-link :to="{ name: 'UnidadServicioView', params: { unidadServicioId: listaServicio.unidadServicio.id } }">{{
                listaServicio.unidadServicio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="listaServicio.id"
          :to="{ name: 'ListaServicioEdit', params: { listaServicioId: listaServicio.id } }"
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

<script lang="ts" src="./lista-servicio-details.component.ts"></script>
