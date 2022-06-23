<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="servicio">
        <h2 class="jh-entity-heading" data-cy="servicioDetailsHeading">
          <span v-text="$t('segmaflotApp.servicio.detail.title')">Servicio</span> {{ servicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('segmaflotApp.servicio.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ servicio.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <div v-if="servicio.descripcion">
              <a v-on:click="openFile(servicio.descripcionContentType, servicio.descripcion)" v-text="$t('entity.action.open')">open</a>
              {{ servicio.descripcionContentType }}, {{ byteSize(servicio.descripcion) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.duracionEstimada')">Duracion Estimada</span>
          </dt>
          <dd>
            <span v-if="servicio.duracionEstimada">{{ $d(Date.parse(servicio.duracionEstimada), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.createByUser')">Create By User</span>
          </dt>
          <dd>
            <span>{{ servicio.createByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.createdOn')">Created On</span>
          </dt>
          <dd>
            <span v-if="servicio.createdOn">{{ $d(Date.parse(servicio.createdOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.modifyByUser')">Modify By User</span>
          </dt>
          <dd>
            <span>{{ servicio.modifyByUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.modifiedOn')">Modified On</span>
          </dt>
          <dd>
            <span v-if="servicio.modifiedOn">{{ $d(Date.parse(servicio.modifiedOn), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('segmaflotApp.servicio.auditStatus')">Audit Status</span>
          </dt>
          <dd>
            <span>{{ servicio.auditStatus }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="servicio.id" :to="{ name: 'ServicioEdit', params: { servicioId: servicio.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./servicio-details.component.ts"></script>
